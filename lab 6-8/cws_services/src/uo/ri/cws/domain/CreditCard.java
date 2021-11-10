package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCreditCards")
public class CreditCard extends PaymentMean {
	@Column(unique = true)
	private String number;
	private String type;
	private LocalDate validThru;
	
	public CreditCard(String number) {
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isNotEmpty(number);
		this.number = number;
	}
	
	CreditCard() {}
	
	public CreditCard(String number, String type, LocalDate validThru) {
		this(number);
		ArgumentChecks.isNotNull(type);
		ArgumentChecks.isNotNull(validThru);
		
		ArgumentChecks.isNotEmpty(type);
		
		this.number = number;
		this.type = type;
		this.validThru = validThru;
	}
	
	@Override
	public void validate(Charge charge)
	{
		if (validThru.isBefore(charge.getInvoice().getDate()))
			throw new IllegalStateException("credit card is invalid");
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public LocalDate getValidThru() {
		return validThru;
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
		CreditCard other = (CreditCard) obj;
		return Objects.equals(number, other.number);
	}

	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type
				+ ", validThru=" + validThru + "]";
	}
}
