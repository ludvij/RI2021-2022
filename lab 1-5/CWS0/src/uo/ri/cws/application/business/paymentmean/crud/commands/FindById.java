package uo.ri.cws.application.business.paymentmean.crud.commands;

import java.util.Optional;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.paymentmean.PaymentMeanDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.creditcard.CreditCardGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;

public class FindById implements Command<Optional<PaymentMeanDto>> {
	
	private String id;
	
	private PaymentMeanGateway pg = PersistenceFactory.forPaymentmean();
	private CreditCardGateway cg  = PersistenceFactory.forCreditCard();
	private VoucherGateway vg     = PersistenceFactory.forVoucher();
	
	public FindById(String id) {
		Argument.isNotNull(id, "id is null");
		Argument.isNotEmpty(id, "id is empty");
		this.id = id;
	}

	@Override
	public Optional<PaymentMeanDto> execute() throws BusinessException 
	{
		PaymentMeanDto dto = null;
		var paymentOp = pg.findById(id);
		
		if (paymentOp.isPresent()) 
		{			
			var payment = paymentOp.get();
			
			switch (payment.dtype) {
			case "CREDITCARD":
				var card = cg.findById(id).get();
				dto = DtoAssembler.toCardDto(payment, card);
				break;
			case "VOUCHER":
				var voucher = vg.findById(id).get();
				dto = DtoAssembler.toVoucherDto(payment, voucher);
				break;
			default:
				throw new BusinessException("Type not implemented");
			}
		}
	
		return Optional.ofNullable(dto);
	}
}
