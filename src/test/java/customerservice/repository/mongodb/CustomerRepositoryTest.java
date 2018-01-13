package customerservice.repository.mongodb;

import static customerservice.domain.enums.CustomerType.COMPANY;
import static customerservice.domain.enums.CustomerType.PERSON;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import customerservice.CustomerService;
import customerservice.domain.Address;
import customerservice.domain.Customer;
import customerservice.repository.mongodb.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@Import(CustomerService.class)
@TestPropertySource(locations = "classpath:application.properties")
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository repo;

	/**
	 * Class level @DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
	 * annotation can be used instead of this method to reset the context for
	 * each test but execution will be much slower.
	 */
	@Before
	public void cleanDB() {
		repo.deleteAll().block();
	}

	@Test
	public void shouldCreateAPerson() {

		// Given
		final Address address = Address.ofCountry("Shadaloo")
				.withStreetNumber(110)
				.withStreetName("Bison street")
				.withCity("Shadaloo City")
				.withZipcode("123456")
				.build();

		// When
		final Customer saved = repo.save(
				Customer.ofType(PERSON)
					.withFirstName("Ken")
					.withLastName("Masters")
					.withAddress(address).build())
				.block();

		// Then
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getFirstName()).isEqualTo("Ken");
		assertThat(saved.getAddress().getCountry()).isEqualTo("Shadaloo");
	}

	@Test
	public void shouldFindAPersonWithItsId() {

		// Given
		final Address address = Address.ofCountry("Shadaloo")
				.withStreetNumber(110)
				.withStreetName("Bison street")
				.withCity("Shadaloo City")
				.withZipcode("123456")
				.build();
		final Customer saved = repo.save(
				Customer.ofType(PERSON)
					.withFirstName("Ken")
					.withLastName("Masters")
					.withAddress(address)
					.build())
				.block();

		// When
		final Customer retrieved = repo.findById(saved.getId()).block();

		// Then
		assertThat(retrieved).isNotNull();
		assertThat(retrieved.getId()).isEqualTo(saved.getId());
		assertThat(retrieved.getFirstName()).isEqualTo(saved.getFirstName());
		assertThat(retrieved.getAddress().getCountry()).isEqualTo("Shadaloo");
	}

	@Test
	public void shouldUpdateAPerson() {

		// Given
		Address address = Address.ofCountry("Shadaloo")
				.withStreetNumber(110)
				.withStreetName("Bison street")
				.withCity("Shadaloo City")
				.withZipcode("123456")
				.build();
		final Customer saved = repo.save(
				Customer.ofType(PERSON)
					.withFirstName("Ken")
					.withLastName("Masters")
					.withAddress(address)
					.build())
				.block();
		Customer retrieved = repo.findById(saved.getId()).block();
		address = Address.from(address).withZipcode("654321").build();
		retrieved = Customer.from(retrieved)
				.withEmail("kenm@email.com")
				.withAddress(address)
				.build();

		// When
		final Customer updated = repo.save(retrieved).block();

		// Then
		assertThat(updated.getId()).isEqualTo(retrieved.getId());
		assertThat(updated.getEmail()).isEqualTo("kenm@email.com");
		assertThat(updated.getAddress().getZipcode()).isEqualTo("654321");
	}

	@Test
	public void shouldDeleteAPerson() {

		// Given
		final Customer saved = repo.save(
				Customer.ofType(PERSON)
					.withFirstName("Ken")
					.withLastName("Masters")
					.build())
				.block();

		// When
		repo.delete(saved).block();
		boolean exists = repo.existsById(saved.getId()).block();

		// Then
		assertThat(exists).isFalse();
	}

	@Test
	public void shouldReturnAllCustomers() {

		// Given
		final Address address = Address.ofCountry("Shadaloo")
				.withStreetNumber(110)
				.withStreetName("Bison street")
				.withCity("Shadaloo City")
				.withZipcode("123456")
				.build();
		
		repo.save(
				Customer.ofType(PERSON)
					.withFirstName("Ken")
					.withLastName("Masters")
					.withAddress(address)
					.build())
				.block();
		
		repo.save(
				Customer.ofType(COMPANY)
					.withFirstName("Ken")
					.withLastName("Masters")
					.withAddress(address)
					.build())
				.block();

		// When
		final Iterable<Customer> customers = repo.findAll().toIterable();

		// Then
		assertThat(customers).isNotNull();
		assertThat(customers.iterator()).hasSize(2);
	}

	@Test
	public void shouldReturnNoCustomers() {

		// Given

		// When
		final Iterable<Customer> customers = repo.findAll().toIterable();

		// Then
		assertThat(customers).isNotNull();
		assertThat(customers.iterator()).hasSize(0);
	}
}
