package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;

public class WorkOrder {
	public enum WorkOrderStatus {
		OPEN,
		ASSIGNED,
		FINISHED,
		INVOICED
	}

	// natural attributes
	private LocalDateTime date;
	private String description;
	private double amount = 0.0;
	private WorkOrderStatus status = WorkOrderStatus.OPEN;

	// accidental attributes
	private Vehicle vehicle;
	private Mechanic mechanic;
	private Invoice invoice;
	private Set<Intervention> interventions = new HashSet<>();
	
	
	
	public WorkOrder(Vehicle vehicle) {
		ArgumentChecks.isNotNull(vehicle);
		
		Associations.Fix.link(vehicle, this);
	}
	
	
	
	
	
	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle);
		this.description = description;
	}

	/**
	 * Changes it to INVOICED state given the right conditions
	 * This method is called from Invoice.addWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not FINISHED, or
	 *  - The work order is not linked with the invoice
	 */
	public void markAsInvoiced() {

	}

	/**
	 * Changes it to FINISHED state given the right conditions and
	 * computes the amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state, or
	 *  - The work order is not linked with a mechanic
	 */
	public void markAsFinished() {
		if (status != WorkOrderStatus.ASSIGNED)
			throw new IllegalStateException("status is not assigned");
		if (mechanic == null)
			throw new IllegalStateException("workOrder is not linked");
		
		status = WorkOrderStatus.FINISHED;


	}

	/**
	 * Changes it back to FINISHED state given the right conditions
	 * This method is called from Invoice.removeWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not INVOICED, or
	 *  - The work order is still linked with the invoice
	 */
	public void markBackToFinished() {

	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its status
	 * to ASSIGNED
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in OPEN status, or
	 *  - The work order is already linked with another mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		if (status != WorkOrderStatus.OPEN)
			throw new IllegalStateException("workorder not open");
		if (this.mechanic != null) 
			throw new IllegalStateException("workorder already assigned");
		Associations.Assign.link(mechanic, this);
		status = WorkOrderStatus.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes
	 * its status back to OPEN
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED status
	 */
	public void desassign() {

	}

	/**
	 * In order to assign a work order to another mechanic is first have to
	 * be moved back to OPEN state and unlinked from the previous mechanic.
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in FINISHED status
	 */
	public void reopen() {

	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}


	public LocalDateTime getDate() {
		return date;
	}


	public String getDescription() {
		return description;
	}


	public double getAmount() {
		return amount;
	}


	public WorkOrderStatus getStatus() {
		return status;
	}


	public Mechanic getMechanic() {
		return mechanic;
	}


	public Invoice getInvoice() {
		return invoice;
	}
	
	public boolean isInvoiced() {
		return status == WorkOrderStatus.INVOICED;
	}
	
	public boolean isFinished() {
		return status == WorkOrderStatus.FINISHED;
	}


	@Override
	public int hashCode() {
		return Objects.hash(vehicle);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkOrder other = (WorkOrder) obj;
		return Objects.equals(vehicle, other.vehicle);
	}


	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description
				+ ", amount=" + amount + ", status=" + status + ", vehicle="
				+ vehicle + "]";
	}
	
}
