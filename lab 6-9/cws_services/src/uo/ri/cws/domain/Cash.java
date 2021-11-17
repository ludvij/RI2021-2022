package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;

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
