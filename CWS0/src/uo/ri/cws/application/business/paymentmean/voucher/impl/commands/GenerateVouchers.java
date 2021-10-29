package uo.ri.cws.application.business.paymentmean.voucher.impl.commands;

import java.util.UUID;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoiceDto.InvoiceStatus;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.recommendation.RecommendationGateway;
import uo.ri.cws.application.persistence.recommendation.RecommendationRecord;

public class GenerateVouchers implements Command<Integer> {
	
	private RecommendationGateway rg = PersistenceFactory.forRecommendation(); 

	@Override
	public Integer execute() throws BusinessException {
		// we filter out the clients that don't have a paid invoiced workorder
		var clients = PersistenceFactory.forClient().findAll().stream()
			.filter(x -> hasPaidInvoice(x.id))         
			.toList();
		
		// we create voucher if any for the valid clients
		int generatedVouchers = 0;
		for (var client : clients) {
			// filter clients by their recommendations
			var recommendations = rg.findBySponsorId(client.id).stream()
					.filter(x -> x.usedForVoucher == false)
					.filter(x -> hasPaidInvoice(x.recommended_id))
					.toList();
			
			for (int i = 0; i < recommendations.size() / 3; i++) {
				// set recommendations to used for voucher
				recommendations.stream()
					.skip(i * 3)
					.forEach(x -> markRecommendationAsUsed(x));

				addVoucher(client.id);
				generatedVouchers++;
			}
		}
		
		return generatedVouchers;
	}
	
	
	private void addVoucher(String id) {
		var record = DtoAssembler.toRecord(createVoucher(id));
		
		PersistenceFactory.forPaymentmean().add(record);
		PersistenceFactory.forVoucher().add(record);
	}
	
	
	private boolean hasPaidInvoice(String clientId) {
		return PersistenceFactory.forVehicle().findByClientId(clientId).stream()
				.map(x -> PersistenceFactory.forWorkOrder().findByVehicleId(x.id))
				.flatMap(x -> x.stream())
				.filter(x -> x.status.equals("INVOICED"))
				.map(x -> PersistenceFactory.forInvoice().findById(x.invoice_id).get())
				.anyMatch(x -> String.valueOf(InvoiceStatus.PAID).equals(x.status));
	}
	
	
	private void markRecommendationAsUsed(RecommendationRecord record) {
		record.usedForVoucher = true;
		rg.update(record);
	}
	
	
	
	private VoucherDto createVoucher(String id)
	{
		VoucherDto voucher = new VoucherDto();
		voucher.id = UUID.randomUUID().toString();
		voucher.balance = 25.0d;
		voucher.clientId = id;
		voucher.description = "By recommendation";
		voucher.code = UUID.randomUUID().toString();
		voucher.accumulated = 0.0d;
		
		return voucher;
	}

}
