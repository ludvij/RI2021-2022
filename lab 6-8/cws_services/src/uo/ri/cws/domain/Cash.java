package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCashes")
public class Cash extends PaymentMean {

	Cash() {}
	
	public Cash(Client client) {
		ArgumentChecks.isNotNull(client);
		Associations.Pay.link(this, client);
	}

	@Override
	public String toString() {
		return "Cash [getCharges()=" + getCharges() + ", getAccumulated()="
				+ getAccumulated() + ", getClient()=" + getClient() + "]";
	}
	
	
}
