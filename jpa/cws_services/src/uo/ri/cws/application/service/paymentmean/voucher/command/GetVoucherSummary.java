package uo.ri.cws.application.service.paymentmean.voucher.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.VoucherService.VoucherSummaryDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class GetVoucherSummary implements Command<List<VoucherSummaryDto>> {

	private ClientRepository clientRepo  = Factory.repository.forClient();
	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();

	@Override
	public List<VoucherSummaryDto> execute() throws BusinessException {

		List<VoucherSummaryDto> res = new ArrayList<>();
		for (var client : clientRepo.findAll()) {

			var aggregate = pmRepo
					.findAggregateVoucherDataByClientId(client.getId());
			if ((long) aggregate[0] > 0) {
				res.add(DtoAssembler.toDto(client, 
						(long)  aggregate[0], 
						(double)aggregate[1],
						(double)aggregate[2],
						(double)aggregate[3]));
			}
		}
		return res;
		
	}

}
