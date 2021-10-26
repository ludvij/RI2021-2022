package uo.ri.cws.application.persistence.paymentmean;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface PaymentMeanGateway extends Gateway<PaymentmeanRecord> {
	
	Optional<String> getDType(String id);
	
	
	List<PaymentmeanRecord> findByClientId(String id);
}
