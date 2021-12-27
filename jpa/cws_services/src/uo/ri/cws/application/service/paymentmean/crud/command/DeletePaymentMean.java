package uo.ri.cws.application.service.paymentmean.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.PaymentMean;

public class DeletePaymentMean implements Command<Void> {

	private PaymentMeanRepository repo = Factory.repository.forPaymentMean();

	private String id;

	public DeletePaymentMean(String id) {
		ArgumentChecks.isNotNull(id);
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {

		Optional<PaymentMean> opm = repo.findById(id);

		BusinessChecks.exists(opm);
		PaymentMean pm = opm.get();
		
		if ("Cash".equals(pm.getDtype()))
			throw new BusinessException("Cash cannot be removed");
		if (!pm.getCharges().isEmpty())
			throw new BusinessException(
					"Payment mean with charges cannot be removed");

		repo.remove(pm);
		return null;
	}
}
