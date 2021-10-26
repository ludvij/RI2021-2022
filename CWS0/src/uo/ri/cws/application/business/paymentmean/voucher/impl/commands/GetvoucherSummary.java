package uo.ri.cws.application.business.paymentmean.voucher.impl.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherSummaryDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;

public class GetvoucherSummary implements Command<List<VoucherSummaryDto>> {
	
	private PaymentMeanGateway pg = PersistenceFactory.forPaymentmean();
	private VoucherGateway vg     = PersistenceFactory.forVoucher();
	private ClientGateway cg      = PersistenceFactory.forClient();

	@Override
	public List<VoucherSummaryDto> execute() throws BusinessException {
		List<VoucherSummaryDto> summaries = new ArrayList<>();
		
		for (var client : cg.findAll() ) 
		{
			int issued = 0;
			double accumulated = 0;
			double balance = 0;
			
			for (var payment : pg.findByClientId(client.id)) 
			{	
				if (payment.dtype.equals("VOUCHER")) 
				{
					var voucher = vg.findById(payment.id).get();
					issued++;
					accumulated += payment.accumulated;
					balance += voucher.available;
				}
			}
			if (issued > 0)
				summaries.add(DtoAssembler.toVoucherSummaryDto(client, issued, balance, accumulated));
		}
		
		return summaries;
	}

}
