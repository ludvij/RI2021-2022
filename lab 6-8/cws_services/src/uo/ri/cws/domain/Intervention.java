package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(
	name = "TInterventions",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"MECHANIC_ID", "WORKORDER_ID", "DATE"})
	}
)
public class Intervention extends BaseEntity {
	// natural attributes
	private LocalDateTime date;
	private int minutes;

	// accidental attributes
	@ManyToOne private WorkOrder workOrder;
	@ManyToOne private Mechanic mechanic;
	
	@OneToMany(mappedBy = "intervention")
	private Set<Substitution> substitutions = new HashSet<>();
	
	Intervention() {}

	public Intervention(Mechanic Mechanic, WorkOrder workOrder,
			LocalDateTime date) {
		ArgumentChecks.isNotNull(Mechanic);
		ArgumentChecks.isNotNull(workOrder);
		ArgumentChecks.isNotNull(date);

		this.date = date.truncatedTo(ChronoUnit.MILLIS);

		Associations.Intervene.link(workOrder, this, Mechanic);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		this(mechanic, workOrder, LocalDateTime.now());
		this.minutes = minutes;
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder,
			LocalDateTime date, int minutes) {
		this(mechanic, workOrder, date);
		this.minutes = minutes;
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public int getMinutes() {
		return minutes;
	}

	public double getAmount() {
		if (workOrder == null)
			throw new IllegalStateException("workorder is null");

		double price = minutes / 60.0d
				* workOrder.getVehicle().getVehicleType().getPricePerHour();
		double subs = substitutions.stream().map(x -> x.getAmount())
				.reduce(0.0d, (a, b) -> a + b);

		return price + subs;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, mechanic, workOrder);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intervention other = (Intervention) obj;
		return Objects.equals(date, other.date)
				&& Objects.equals(mechanic, other.mechanic)
				&& Objects.equals(workOrder, other.workOrder);
	}

	@Override
	public String toString() {
		return "Intervention [date=" + date + ", minutes=" + minutes + "]";
	}

}
