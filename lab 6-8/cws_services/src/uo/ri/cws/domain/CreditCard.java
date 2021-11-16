package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import alb.util.assertion.ArgumentChecks;

public class CreditCard extends PaymentMean {
	
	private String number;
	private String type;
	private LocalDate validThru;
	
	public CreditCard(String number, String type, LocalDate validThru) {
		ArgumentChecks.isNotNull(type);
		ArgumentChecks.isNotNull(validThru);
		ArgumentChecks.isNotNull(number);
		
		ArgumentChecks.isNotEmpty(number);
		ArgumentChecks.isNotEmpty(type);
		
		this.number = number;
		this.type = type;
		this.validThru = validThru;
	}
	
	public CreditCard(String number) {
		this(number, "UNKNOWN", LocalDate.now().plusDays(1));
	}
	
	CreditCard() {}
	
	
	@Override
	public void pay(double price)
	{
		if (!isValidNow())
			throw new IllegalStateException("credit card is not valid");
		super.pay(price);
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

	public boolean isValidNow() {
		return LocalDate.now().isBefore(validThru);
	}

	public void setValidThru(LocalDate minusDays) {
		validThru = minusDays;
		
	}
}
