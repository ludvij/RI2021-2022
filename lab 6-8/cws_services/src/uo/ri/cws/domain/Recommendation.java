package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Recommendation extends BaseEntity {

	// client who sponsored
	private Client sponsor;
	// sponsored client
	private Client recommended;
	
	private boolean used = false;
	
	
	public Recommendation() {}
	
	public Recommendation(Client sponsor, Client recommended) {
		ArgumentChecks.isNotNull(sponsor);
		ArgumentChecks.isNotNull(recommended);
		
		Associations.Recommend.link(sponsor, this, recommended);
	}



	public boolean isUsed() {
		return used;
	}

	public Client getRecommended() {
		return recommended;
	}
	
	void _setRecommended(Client recommended) {
		this.recommended = recommended;
	}

	public Client getSponsor() {
		return sponsor;
	}
	
	void _setSponsor(Client sponsor) {
		this.sponsor = sponsor;
	}

	public void markAsUsed() {
		used = true;
	}


}
