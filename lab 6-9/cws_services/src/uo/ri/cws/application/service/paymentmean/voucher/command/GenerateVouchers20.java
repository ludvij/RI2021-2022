package uo.ri.cws.application.service.paymentmean.voucher.command;

import java.util.List;
import java.util.UUID;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.domain.WorkOrder;

public class GenerateVouchers20 implements Command<Integer> {

	private ClientRepository clientRepo = Factory.repository.forClient();
	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();

	@Override
	public Integer execute() throws BusinessException {
		
		int generatedVouchers = 0;
		
		for (var client : clientRepo.findAll()) 
		{
			List<WorkOrder> wos = client.getWorkOrdersAvailableForVoucher();
			
			for (int i = 0; i < wos.size() / 3; i++) 
			{
				wos.stream().skip(i * 3).limit(3)
						.forEach(x -> x.markAsUsedForVoucher());
				createVoucer(client);
				generatedVouchers++;
			}

		}

		return generatedVouchers;
	}
	
	private void createVoucer(Client client) {
		String code = UUID.randomUUID().toString();
		String description = "By three workorders";
		double balance = 20.0d;
		Voucher voucher = new Voucher(client, code, description, balance);
		pmRepo.add(voucher);
	}

}
