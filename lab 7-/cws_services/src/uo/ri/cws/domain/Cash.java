package uo.ri.cws.domain;

import javax.persistence.Entity;

import alb.util.assertion.ArgumentChecks;

@Entity
public class Cash extends PaymentMean {

	Cash() {}
	
	public Cash(Client client) {
		ArgumentChecks.isNotNull(client);
		Associations.Pay.link(client, this);
	}

	@Override
	public String toString() {
		return "Cash [getCharges()=" + getCharges() + ", getAccumulated()="
				+ getAccumulated() + ", getClient()=" + getClient() + "]";
	}
	
	
}
