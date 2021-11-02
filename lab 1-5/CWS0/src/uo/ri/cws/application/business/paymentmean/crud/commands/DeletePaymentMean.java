package uo.ri.cws.application.business.paymentmean.crud.commands;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.paymentmean.PaymentMeanDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.charge.ChargeGateway;
import uo.ri.cws.application.persistence.creditcard.CreditCardGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;

public class DeletePaymentMean implements Command<PaymentMeanDto>{
	
	private PaymentMeanGateway pg = PersistenceFactory.forPaymentmean();
	private CreditCardGateway ccg = PersistenceFactory.forCreditCard();
	private VoucherGateway vg     = PersistenceFactory.forVoucher();
	
	private String id;
	
	public DeletePaymentMean(String id) {
		Argument.isNotNull(id, "id is null");
		Argument.isNotEmpty(id, "id is empty");
		this.id = id;
	}

	@Override
	public PaymentMeanDto execute() throws BusinessException {
		var paymentOp = pg.findById(id);
		if (paymentOp.isEmpty())
			throw new BusinessException("payment mean does not exist");
		if (hasCharges(id))
			throw new BusinessException("Payment mean has charges");
		
		var payment = paymentOp.get();
		
		switch (payment.dtype) 
		{
		case "CREDITCARD":
			ccg.remove(id);
			break;
			
		case "VOUCHER":
			vg.remove(id);
			break;
			
		case "CASH":	
			throw new BusinessException("Cash can't be removed");
		}
		
		pg.remove(id);
		
		return null;
	}
	
	
	private boolean hasCharges(String id)
	{
		ChargeGateway gat = PersistenceFactory.forCharge();
		var charge = gat.findByPaymentMeanId(id);
		
		return charge.isPresent();
	}

}
