package uo.ri.cws.application.business.paymentmean.crud.commands;

import java.util.UUID;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;
import uo.ri.cws.application.persistence.voucher.VoucherRecord;

public class AddVoucher implements Command<VoucherDto> {
	
	private VoucherDto voucher;
	
	private VoucherGateway vg = PersistenceFactory.forVoucher();
	private PaymentMeanGateway pg = PersistenceFactory.forPaymentmean();
	
	
	public AddVoucher(VoucherDto voucher) {
		validate(voucher);
		this.voucher = voucher;
	}
	

	@Override
	public VoucherDto execute() throws BusinessException {
		if (!clientExists(voucher.clientId))
			throw new BusinessException("client does not exist");
		if (isRepeated(voucher.code))
			throw new BusinessException("voucher code already exists");
		
		voucher.id = UUID.randomUUID().toString();
		VoucherRecord record = DtoAssembler.toRecord(voucher);
		
		pg.add(record);
		vg.add(record);
		
		return null;
	}
	
	
	private void validate(VoucherDto voucher) 
	{
		Argument.isNotNull(voucher,          "voucher is null");
		Argument.isNotNull(voucher.code,     "voucher code is null");
		Argument.isNotNull(voucher.clientId, "client id is null");
		
		Argument.isNotEmpty(voucher.code,        "voucher code is empty");
		Argument.isNotEmpty(voucher.clientId,    "client id is empty");
		Argument.isNotEmpty(voucher.description, "description is empty");

		Argument.isTrue(voucher.balance >= 0, "balance is lower than 0");
	}
	
	private boolean isRepeated(String code)
	{
		var res = vg.findByCode(code);
		
		return res.isPresent();
	}
	
	
	private boolean clientExists(String clientId)
	{
		ClientGateway cg = PersistenceFactory.forClient();
		
		var res = cg.findById(clientId);
		return res.isPresent();
	}

}
