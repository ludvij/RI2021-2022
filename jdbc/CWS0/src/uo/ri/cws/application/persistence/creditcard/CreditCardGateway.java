package uo.ri.cws.application.persistence.creditcard;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface CreditCardGateway extends Gateway<CreditCardRecord> {
	
	
	Optional<CreditCardRecord> findByNumber(String number);


}
