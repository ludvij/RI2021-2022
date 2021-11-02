package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import alb.util.assertion.ArgumentChecks;

public class CreditCard extends PaymentMean {
	private String number;
	private String type;
	private LocalDate validThru;
	
	public CreditCard(String number) {
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isNotEmpty(number);
		this.number = number;
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
