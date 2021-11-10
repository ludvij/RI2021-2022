package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "Tclients")
public class Client extends BaseEntity {
	
	@Column(unique = true)
	private String dni;
	
	private String name;
	private String surname;
	private String email;
	private String phone;
	@Embedded
	private Address address;
	
	// accidental attributes
	@OneToMany(mappedBy = "client") 
	private Set<Vehicle> vehcicles = new HashSet<>();
	@OneToMany(mappedBy = "client") 
	private Set<PaymentMean> paymentMeans = new HashSet<>();
	
	
	Client(){}
	
	public Client(String dni) {
		ArgumentChecks.isNotNull(dni);
		ArgumentChecks.isNotEmpty(dni);
		this.dni = dni;
	}
	
	public Client(String dni, String name, String surname) {
		this(dni);
		ArgumentChecks.isNotNull(name);
		ArgumentChecks.isNotNull(surname);
		this.name = name;
		this.surname = surname;
		
	}
	

	public Set<Vehicle> getVehicles() {
		return new HashSet<>( vehcicles );
	}
	
	Set<Vehicle> _getVehicles() {
		return vehcicles;
	}

	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>( paymentMeans );
	}
	
	Set<PaymentMean> _getpaymentMeans() {
		return paymentMeans;
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

