package uo.ri.cws.application.service.paymentmean.voucher.command;

import java.util.UUID;
import java.util.stream.Collectors;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.domain.WorkOrder;

public class GenerateVouchers30 implements Command<Integer> {

	private ClientRepository clientRepo = Factory.repository.forClient();
	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
	
	@Override
	public Integer execute() throws BusinessException {
		int generatedvouchers = 0;
		
		var clients = clientRepo.findAll();
		
		for (var client : clients) 
		{
			var invoicesOver500 = client.getPaidWorkOrders().stream()
					.map(WorkOrder::getInvoice)
					.filter(Invoice::canGenerate500Voucher)
					.collect(Collectors.toList());
			
			for (var inv : invoicesOver500) 
			{
				inv.markAsUsed();
				createVoucer(client);
				
				generatedvouchers++;
			}
		}
		return generatedvouchers;
	}
	
	private void createVoucer(Client client) {
		String code = UUID.randomUUID().toString();
		String description = "By invoice over 500";
		double balance = 30.0d;
		Voucher voucher = new Voucher(client, code, description, balance);
		pmRepo.add(voucher);
	}

}
