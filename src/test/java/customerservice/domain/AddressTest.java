package customerservice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import customerservice.domain.Address;

public class AddressTest {

	@Test
	public void shouldBuildAnAddress() {

		// Given
		final Address address;

		// When
		address = Address.ofCountry("Shadaloo")
				.withStreetNumber(110)
				.withStreetName("Bison street")
				.withCity("Shadaloo City")
				.withZipcode("123456")
				.build();

		// Then
		assertThat(address).isNotNull();
		assertThat(address.getCountry()).isEqualTo("Shadaloo");
		assertThat(address.getStreetNumber()).isEqualTo(110);
		assertThat(address.getStreetName()).isEqualTo("Bison street");
		assertThat(address.getCity()).isEqualTo("Shadaloo City");
		assertThat(address.getStateOrProvince()).isNull();
		assertThat(address.getZipcode()).isEqualTo("123456");
	}

	@Test
	public void shouldUpdateAnAddress() {

		// Given
		Address address = Address.ofCountry("Shadaloo")
				.withStreetNumber(110)
				.withStreetName("Bison street")
				.withCity("Shadaloo City")
				.withZipcode("123456")
				.build();

		// When
		address = Address.from(address).withStreetNumber(2000).build();

		// Then
		assertThat(address).isNotNull();
		assertThat(address.getCountry()).isEqualTo("Shadaloo");
		assertThat(address.getStreetNumber()).isEqualTo(2000);
		assertThat(address.getStreetName()).isEqualTo("Bison street");
		assertThat(address.getCity()).isEqualTo("Shadaloo City");
		assertThat(address.getStateOrProvince()).isNull();
		assertThat(address.getZipcode()).isEqualTo("123456");
	}

	@Test
	public void shouldFailIfCountryIsNull() {

		assertThatThrownBy(() -> Address.ofCountry(null)).hasMessage("Country can not be null.");
	}
}
