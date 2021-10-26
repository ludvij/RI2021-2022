package uo.ri.cws.application.business.paymentmean.voucher.impl.commands;

import java.util.ArrayList;
import java.util.List;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;

public class FindVouchersByClientId implements Command<List<VoucherDto>> {
	
	private VoucherGateway vg = PersistenceFactory.forVoucher();
	private PaymentMeanGateway pg = PersistenceFactory.forPaymentmean();
	
	private String id;
	
	public FindVouchersByClientId(String id) {
		Argument.isNotNull(id, "id is null");
		Argument.isNotEmpty(id, "id is empty");
		this.id = id;
	}

	@Override
	public List<VoucherDto> execute() throws BusinessException {
		List<VoucherDto> res = new ArrayList<>();
		
		for (var payment : pg.findByClientId(id)) {
			if (payment.dtype.equals("VOUCHER")) {
				var voucher = vg.findById(payment.id).get();
				res.add(DtoAssembler.toVoucherDto(payment, voucher));
			}
		}
		return res;
	}

}
