package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
public class Vehicle extends BaseEntity {
	@Column(unique = true)
	private String plateNumber;
	private String make;
	private String model;
	
	// accidental
	@ManyToOne private Client client;
	@ManyToOne private VehicleType vehicleType;
	
	@OneToMany(mappedBy = "vehicle")
	private Set<WorkOrder> workOrders = new HashSet<>();
	
	Vehicle() {}

	public Vehicle(String plateNumber) {
		ArgumentChecks.isNotNull(plateNumber);
		ArgumentChecks.isNotEmpty(plateNumber);

		this.plateNumber = plateNumber;
	}

	public Vehicle(String plateNumber, String make, String model) {
		this(plateNumber);
		this.make = make;
		this.model = model;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Client getClient() {
		return client;
	}

	void _setClient(Client client) {
		this.client = client;
	}

	public String getMake() {
		return make;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public String getModel() {
		return model;
	}

	@Override
	public int hashCode() {
		return Objects.hash(plateNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(plateNumber, other.plateNumber);
	}

	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make
				+ ", model=" + model + "]";
	}

}
