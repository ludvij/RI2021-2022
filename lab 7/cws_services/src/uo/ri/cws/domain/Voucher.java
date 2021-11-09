package uo.ri.cws.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

import alb.util.assertion.ArgumentChecks;

@Entity
public class Voucher extends PaymentMean {
	@Column(unique = true)
	private String code;
	private double available = 0.0;
	private String description;

	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the available
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		super.pay(amount);
		available -= amount;
	}

	Voucher() {}
	
	
	public Voucher(String code) {
		ArgumentChecks.isNotNull(code);
		ArgumentChecks.isNotEmpty(code);
		this.code = code;
	}

	public Voucher(String code, String description, double available) {
		this(code);
		ArgumentChecks.isNotNull(description);
		ArgumentChecks.isNotEmpty(description);
		this.description = description;
		this.available = available;
	}
	
	@Override
	public void validate(Charge charge)
	{
		if (charge.getAmount() > available) {
			throw new IllegalStateException("Invalid Voucher");
		}
	}

	public String getCode() {
		return code;
	}

	public double getAvailable() {
		return available;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(code);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voucher other = (Voucher) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "Voucher [code=" + code + ", available=" + available
				+ ", description=" + description + "]";
	}

	
	
	

}
