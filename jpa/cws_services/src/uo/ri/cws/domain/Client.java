package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Client extends BaseEntity {

	private String dni;

	private String name;
	private String surname;
	private String email;
	private String phone;
	private Address address;

	// accidental attributes
	private Set<Vehicle> vehicles = new HashSet<>();
	private Set<PaymentMean> paymentMeans = new HashSet<>();

	// clients whom I sponsored
	private Set<Recommendation> sponsored = new HashSet<>();
	// client who sponsored me
	private Recommendation recommended;

	Client() {
	}

	public Client(String dni) {
		this(dni, "no-name", "no-surname");
	}

	public Client(String dni, String name, String surname) {
		ArgumentChecks.isNotEmpty(dni);
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotEmpty(surname);
		this.dni = dni;
		this.name = name;
		this.surname = surname;
		address = new Address();

	}

	public Client(String dni, String name, String surname, String email,
			String phone, Address address) {
		this(dni, name, surname);
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>(paymentMeans);
	}

	Set<PaymentMean> _getpaymentMeans() {
		return paymentMeans;
	}

	public Set<Recommendation> getSponsored() {
		return new HashSet<>(sponsored);
	}

	Set<Recommendation> _getSponsored() {
		return sponsored;
	}

	void _setRecommended(Recommendation recommended) {
		this.recommended = recommended;
	}

	public Recommendation getRecommended() {
		return recommended;
	}

	public List<WorkOrder> getWorkOrdersAvailableForVoucher() {
		return vehicles.stream().map(Vehicle::getWorkOrders)
				.flatMap(Set::stream).filter(WorkOrder::canBeUsedForVoucher)
				.collect(Collectors.toList());
	}

	public List<WorkOrder> getPaidWorkOrders() {
		return vehicles.stream().map(Vehicle::getWorkOrders)
				.flatMap(Set::stream).filter(WorkOrder::isInvoiced)
				.filter(x -> x.getInvoice().isSettled())
				.collect(Collectors.toList());
	}

	public boolean eligibleForRecommendationVoucher() {
		// check that the client has paid workorders
		return !getPaidWorkOrders().isEmpty() &&
		// check that the client has at least 3 recommendations
		// with paid workorders
				sponsored.stream().filter(x -> !x.isUsedForVoucher())
						.map(Recommendation::getRecommended)
						// check that the recommended clients are trustable
						.map(Client::getPaidWorkOrders)
						.filter(x -> !x.isEmpty()).count() >= 3;
	}

	public String getDni() {
		return dni;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Address getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() {
		return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", phone=" + phone + ", address="
				+ address + "]";
	}

	public void setAddress(Address address) {
		this.address = address;

	}

}
