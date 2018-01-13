package customerservice.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import customerservice.domain.Address;
import customerservice.domain.Customer;
import customerservice.domain.enums.CustomerType;
import customerservice.domain.enums.Gender;
import customerservice.domain.enums.MaritalStatus;
import customerservice.domain.enums.PhoneType;

public class CustomerTest {

	@Test
	public void shouldBuildACustomerOfTypePerson() {

		// Given
		final Customer customer;
		final Address address = Address.ofCountry("Shadaloo")
				.withStreetNumber(110)
				.withStreetName("Bison street")
				.withCity("Shadaloo City")
				.withZipcode("123456").build();

		// When
		customer = Customer.ofType(CustomerType.PERSON)
				.withFirstName("Ken")
				.withLastName("Masters")
				.withGender(Gender.MALE)
				.withBirthDate(LocalDate.of(1990, Month.MARCH, 16))
				.withMaritalStatus(MaritalStatus.SINGLE)
				.withAddress(address)
				.withPhone(PhoneType.HOME, "111111111")
				.withPhone(PhoneType.CELLULAR, "222222222")
				.withPhone(PhoneType.OFFICE, "333333333 Ext123")
				.withPhone(PhoneType.FAX, "444444444")
				.withEmail("kmasters@streetf.com")
				.build();

		// Then
		assertThat(customer).isNotNull();
		assertThat(customer.getCustomerType()).isEqualTo(CustomerType.PERSON);
		assertThat(customer.getFirstName()).isEqualTo("Ken");
		assertThat(customer.getLastName()).isEqualTo("Masters");
		assertThat(customer.getGender()).isEqualTo(Gender.MALE);
		assertThat(customer.getBirthDate()).isEqualTo(LocalDate.of(1990, Month.MARCH, 16));
		assertThat(customer.getMaritalStatus()).isEqualTo(MaritalStatus.SINGLE);
		assertThat(customer.getAddress().getZipcode()).isEqualTo("123456");
		assertThat(customer.getPhones()).contains(
				entry(PhoneType.HOME, "111111111"),
				entry(PhoneType.CELLULAR, "222222222"), 
				entry(PhoneType.OFFICE, "333333333 Ext123"),
				entry(PhoneType.FAX, "444444444"));
		assertThat(customer.getEmail()).isEqualTo("kmasters@streetf.com");
	}

	@Test
	public void shouldBuildACustomerOfTypeCompany() {

		// Given
		final Customer customer;
		final Address address = Address.ofCountry("Shadaloo")
				.withStreetNumber(110)
				.withStreetName("Bison street")
				.withCity("Shadaloo City")
				.withZipcode("123456")
				.build();

		// When
		customer = Customer.ofType(CustomerType.COMPANY)
				.withLastName("Acme Corp.")
				.withAddress(address)
				.withPhone(PhoneType.HOME, "111111111")
				.withPhone(PhoneType.CELLULAR, "222222222")
				.withPhone(PhoneType.OFFICE, "333333333 Ext123")
				.withPhone(PhoneType.FAX, "444444444")
				.withEmail("kmasters@streetf.com")
				.build();

		// Then
		assertThat(customer).isNotNull();
		assertThat(customer.getCustomerType()).isEqualTo(CustomerType.COMPANY);
		assertThat(customer.getLastName()).isEqualTo("Acme Corp.");
		assertThat(customer.getGender()).isNull();
		assertThat(customer.getBirthDate()).isNull();
		assertThat(customer.getMaritalStatus()).isNull();
		assertThat(customer.getAddress().getZipcode()).isEqualTo("123456");
		assertThat(customer.getPhones()).contains(
				entry(PhoneType.HOME, "111111111"),
				entry(PhoneType.CELLULAR, "222222222"), 
				entry(PhoneType.OFFICE, "333333333 Ext123"),
				entry(PhoneType.FAX, "444444444"));
		assertThat(customer.getEmail()).isEqualTo("kmasters@streetf.com");
	}

	@Test
	public void shouldUpdateACustomer() {

		// Given
		final Address address = Address.ofCountry("Shadaloo")
				.withStreetNumber(110)
				.withStreetName("Bison street")
				.withCity("Shadaloo City")
				.withZipcode("123456")
				.build();

		Customer customer = Customer.ofType(CustomerType.COMPANY)
				.withLastName("Acme Corp.")
				.withAddress(address)
				.withPhone(PhoneType.HOME, "111111111")
				.withPhone(PhoneType.CELLULAR, "222222222")
				.withPhone(PhoneType.OFFICE, "333333333 Ext123")
				.withPhone(PhoneType.FAX, "444444444")
				.withEmail("kmasters@streetf.com")
				.build();

		// When
		customer = Customer.from(customer).withLastName("Acme Inc.").build();

		// Then
		assertThat(customer).isNotNull();
		assertThat(customer.getCustomerType()).isEqualTo(CustomerType.COMPANY);
		assertThat(customer.getLastName()).isEqualTo("Acme Inc.");
		assertThat(customer.getGender()).isNull();
		assertThat(customer.getBirthDate()).isNull();
		assertThat(customer.getMaritalStatus()).isNull();
		assertThat(customer.getAddress().getZipcode()).isEqualTo("123456");
		assertThat(customer.getPhones()).contains(
				entry(PhoneType.HOME, "111111111"),
				entry(PhoneType.CELLULAR, "222222222"), 
				entry(PhoneType.OFFICE, "333333333 Ext123"),
				entry(PhoneType.FAX, "444444444"));
		assertThat(customer.getEmail()).isEqualTo("kmasters@streetf.com");
	}

	@Test
	public void shouldFailIfCustomerTypeIsNull() {
		assertThatThrownBy(() -> Customer.ofType(null).build()).hasMessage("Customer type can not be null.");
	}
}
