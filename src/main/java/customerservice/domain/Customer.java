package customerservice.domain;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import customerservice.CustomerServiceException;
import customerservice.domain.enums.CustomerType;
import customerservice.domain.enums.Gender;
import customerservice.domain.enums.MaritalStatus;
import customerservice.domain.enums.PhoneType;

/**
 * {@code Customer} is a immutable object.
 * <p>
 * Use {@code Customer.ofType(CustomerType)} builder to create a new customer.
 * <p>
 * Use {@code Customer.from(myCustomer)} builder to initiate a builder with a copy of myCustomer then
 * update its fields with one of the {@code with*()} methods before calling {@code build()} method.<br>
 * <p>
 * Example:<br>
 * {@code Customer myCustomer = Customer.ofType(CustomerType.PERSON).withFirstName("Ken").build();}<br>
 * {@code Customer myCustomer = Customer.from(myCustomer).withFirstName("Bison").build(); // First name changed}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public final class Customer {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	private String firstName;
	private String lastName;
	private Gender gender;
	@JsonFormat(shape = Shape.STRING)
	private LocalDate birthDate;
	private MaritalStatus maritalStatus;
	private Address address;
	private Map<PhoneType, String> phones;
	private String email;
	@NotNull
	private CustomerType customerType;

	private Customer() {
	}

	private Customer(ObjectId id, String firstName, String lastName, Gender gender, LocalDate birthDate,
			MaritalStatus maritalStatus, Address address, Map<PhoneType, String> phones, String email,
			CustomerType customerType) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.maritalStatus = maritalStatus;
		this.address = address;
		this.phones = phones;
		this.email = email;
		this.customerType = customerType;
	}

	public ObjectId getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public Address getAddress() {
		return address;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	/**
	 * Return a copy of the map so the reference does not escape.
	 * 
	 * @return A copy of the map
	 */
	public Map<PhoneType, String> getPhones() {
		
		return phones == null ? phones : new HashMap<>(phones);
	}

	public String getEmail() {
		return email;
	}

	/**
	 * Builds a customer object of the provided type.
	 * <p>
	 * If the type is {@link customerservice.domain.enums.CustomerType#COMPANY} then
	 * the field lastName is used to store the company's name.
	 * <p>
	 * If the type is {@link customerservice.domain.enums.CustomerType#COMPANY} then
	 * the following fields are not relevant:
	 * <p>
	 * <ul>
	 * <li>firstName</li>
	 * <li>gender</li>
	 * <li>birthDate</li>
	 * <li>maritalStatus</li>
	 * </ul>
	 * 
	 * @param customerType
	 *            The type of the customer, listed in the enumeration
	 *            {@link customerservice.domain.enums.CustomerType}
	 * @return A builder object
	 */
	static public Builder ofType(CustomerType customerType) {
		return new Builder(customerType);
	}

	static public Builder from(Customer customer) {

		final Builder builder = new Builder(customer.customerType);
		builder.id = customer.id;
		builder.firstName = customer.firstName;
		builder.lastName = customer.lastName;
		builder.gender = customer.gender;
		builder.maritalStatus = customer.maritalStatus;
		builder.address = customer.address;
		builder.birthDate = customer.birthDate;
		builder.email = customer.email;
		builder.phones = customer.getPhones(); // we need a copy of the map hence
												// the getter
		return builder;
	}

	static public final class Builder {

		private ObjectId id;
		private String firstName;
		private String lastName;
		private Gender gender;
		private LocalDate birthDate;
		private MaritalStatus maritalStatus;
		private Address address;
		private Map<PhoneType, String> phones;
		private String email;
		private CustomerType customerType;

		public Builder(CustomerType customerType) {
			if (customerType == null) {
				throw new CustomerServiceException(BAD_REQUEST, "Customer type can not be null.");
			}
			this.customerType = customerType;
		}

		public Builder withId(ObjectId id) {
			this.id = id;
			return this;
		}

		public Builder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder withGender(Gender gender) {
			this.gender = gender;
			return this;
		}

		public Builder withBirthDate(LocalDate birthDate) {
			this.birthDate = birthDate;
			return this;
		}

		public Builder withMaritalStatus(MaritalStatus maritalStatus) {
			this.maritalStatus = maritalStatus;
			return this;
		}

		public Builder withAddress(Address address) {
			this.address = address;
			return this;
		}

		/**
		 * Add a phone to the customer's list of phones.<br>
		 * A phone consists of a a pair of (phone type, phone number).
		 * <p>
		 * The list is backed by a map where the phone type is the key so only
		 * one number per type can exist.
		 * <p>
		 * The available types are listed in the enumeration {@link PhoneType}
		 * 
		 * @param type
		 *            The type of the phone
		 * @param number
		 *            The phone number
		 */
		public Builder withPhone(PhoneType phonetype, String number) {
			
			if (phones == null) {
				phones = new HashMap<>();
			}
			
			this.phones.put(phonetype, number);
			return this;
		}

		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}

		public Customer build() {
			return new Customer(id, firstName, lastName, gender, birthDate, maritalStatus, address, phones, email,
					customerType);
		}
	}
}
