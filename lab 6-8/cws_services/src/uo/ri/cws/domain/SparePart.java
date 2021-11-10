package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TspareParts")
public class SparePart extends BaseEntity{
	// natural attributes
	@Column(unique = true)
	private String code;
	private String description;
	private double price;

	// accidental attributes
	@OneToMany(mappedBy = "sparePart")
	private Set<Substitution> substitutions = new HashSet<>();
	
	SparePart() {}
	
	public SparePart(String code) {
		ArgumentChecks.isNotNull(code);
		ArgumentChecks.isNotEmpty(code);
		
		this.code = code;
	}
	
	
	public SparePart(String code, String description, double price) {
		this(code);
		this.description = description;
		this.price = price;
	}
	
	public String getCode() {
		return code;
	}


	public String getDescription() {
		return description;
	}


	public double getPrice() {
		return price;
	}
	


	public Set<Substitution> getSustitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SparePart other = (SparePart) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "SparePart [code=" + code + ", description=" + description
				+ ", price=" + price + "]";
	}
	

}
