package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Recommendation extends BaseEntity {

	// client who sponsored
	private Client sponsor;
	// sponsored client
	private Client recommended;
	
	private boolean usedForVoucher;
	
	
	public Recommendation() {}
	
	public Recommendation(Client sponsor, Client recommended) {
		ArgumentChecks.isNotNull(sponsor);
		ArgumentChecks.isNotNull(recommended);
		
		Associations.Recommend.link(sponsor, this, recommended);
	}

	

	public boolean isUsedForVoucher() {
		return usedForVoucher;
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
		usedForVoucher = true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((recommended == null) ? 0 : recommended.hashCode());
		result = prime * result + ((sponsor == null) ? 0 : sponsor.hashCode());
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
		Recommendation other = (Recommendation) obj;
		if (recommended == null) {
			if (other.recommended != null)
				return false;
		} else if (!recommended.equals(other.recommended))
			return false;
		if (sponsor == null) {
			if (other.sponsor != null)
				return false;
		} else if (!sponsor.equals(other.sponsor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Recommendation [sponsor=" + sponsor + ", recommended=" + recommended + ", usedForVoucher="
				+ usedForVoucher + "]";
	}


	
	
}
