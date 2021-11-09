package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;

public class Invoice {
	public enum InvoiceStatus { NOT_YET_PAID, PAID }

	// natural attributes
	private Long number;
	private LocalDate date;
	private double amount;
	private double vat;
	private InvoiceStatus status = InvoiceStatus.NOT_YET_PAID;

	// accidental attributes
	private Set<WorkOrder> workOrders = new HashSet<>();
	private Set<Charge> charges = new HashSet<>();

	public Invoice(Long number) {
		this(number, LocalDate.now(), new ArrayList<WorkOrder>());
	}

	public Invoice(Long number, LocalDate date) {
		this(number, date, new ArrayList<WorkOrder>());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	// full constructor
	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotNull(workOrders);
		// check arguments (always), through IllegalArgumentException
		// store the number
		// store a copy of the date
		// add every work order calling addWorkOrder( w )
		
		this.number = number;
		this.date = date;
		workOrders.forEach(this::addWorkOrder);
	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		this.amount = workOrders.stream()
				.map(x -> x.getAmount())
				.reduce(0.0d, (a, b) -> a + b);
		
		this.vat = date.isBefore(LocalDate.of(2021, 7, 01)) ? 1.18 : 1.21;
		
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		if (status != InvoiceStatus.NOT_YET_PAID)
			throw new IllegalStateException("invoice not yet paid");
		Associations.ToInvoice.link(this, workOrder);
		workOrder.markAsInvoiced();
		computeAmount();

	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		if (status != InvoiceStatus.NOT_YET_PAID)
			throw new IllegalStateException("invoice not yet paid");
		Associations.ToInvoice.unlink(this, workOrder);
		workOrder.markBackToFinished();
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * @throws IllegalStateException if
	 * 	- Is already settled
	 *  - Or the amounts paid with charges to payment means do not cover
	 *  	the total of the invoice
	 */
	public void settle() {
		if (status == InvoiceStatus.PAID)
			throw new IllegalStateException("invoice already settled");
		if (charges.stream().map(x -> x.getAmount()).reduce(0.0d, (a, b) -> a + b) < amount)
			throw new IllegalStateException("Not enough money");
		
		status = InvoiceStatus.PAID;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>( workOrders );
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>( charges );
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public Long getNumber() {
		return number;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getAmount() {
		return Math.round(amount * vat * 100.0d) / 100.0d;
	}

	public double getVat() {
		return vat;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		return Objects.equals(number, other.number);
	}

	@Override
	public String toString() {
		return "Invoice [number=" + number + ", date=" + date + ", amount="
				+ amount + ", vat=" + vat + ", status=" + status + "]";
	}

	public boolean isNotSettled() {
		return status != InvoiceStatus.PAID;
	}
	
	

}
