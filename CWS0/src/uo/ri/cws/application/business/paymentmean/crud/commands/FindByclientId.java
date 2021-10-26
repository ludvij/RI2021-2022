package uo.ri.cws.application.business.paymentmean.crud.commands;

import java.util.ArrayList;
import java.util.List;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.paymentmean.PaymentMeanDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.cash.CashGateway;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.creditcard.CreditCardGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;

public class FindByclientId implements Command<List<PaymentMeanDto>> {
	
	private String id;

	private PaymentMeanGateway pg = PersistenceFactory.forPaymentmean();
	private CreditCardGateway ccg = PersistenceFactory.forCreditCard();
	private VoucherGateway vg     = PersistenceFactory.forVoucher();
	private CashGateway cg        = PersistenceFactory.forCash();
	
	public FindByclientId(String id) {
		Argument.isNotNull(id, "id is null");
		Argument.isNotEmpty(id, "id is empty");
		this.id = id;
	}

	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		List<PaymentMeanDto> res = new ArrayList<>();
		if (!clientExists(id))
			return res;
		
		
		for (var payment : pg.findByClientId(id)) {
			switch (payment.dtype) {
			case "CREDITCARD":
				var card = ccg.findById(payment.id).get();
				res.add(DtoAssembler.toCardDto(payment, card));
				break;
			case "VOUCHER":
				var voucher = vg.findById(payment.id).get();
				res.add(DtoAssembler.toVoucherDto(payment, voucher));
				break;
			case "CASH":
				var cash = cg.findById(payment.id).get();
				res.add(DtoAssembler.toCashDto(payment, cash));
				break;
			}
		}
		
		return res;
	}
	
	
	private boolean clientExists(String clientId)
	{
		ClientGateway cg = PersistenceFactory.forClient();
	
		var res = cg.findById(clientId);
		
		return res.isPresent();
	}

}
