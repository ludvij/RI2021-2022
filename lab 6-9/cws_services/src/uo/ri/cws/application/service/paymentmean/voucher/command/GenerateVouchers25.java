package uo.ri.cws.application.service.paymentmean.voucher.command;

import java.util.UUID;
import java.util.stream.Collectors;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Voucher;

public class GenerateVouchers25 implements Command<Integer> {

	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
	private ClientRepository clientRepo = Factory.repository.forClient();

	@Override
	public Integer execute() throws BusinessException {
		var clients = clientRepo.findAll().stream()
				.filter(Client::eligibleForRecommendationVoucher)
				.collect(Collectors.toList());

		int generatedVouchers = 0;

		for (var client : clients) {
			var recommendedBy = client.getSponsored().stream()
					.filter(x -> !x.isUsedForVoucher()).filter(x -> !x
							.getRecommended().getPaidWorkOrders().isEmpty())
					.collect(Collectors.toList());

			for (int i = 0; i < recommendedBy.size() / 3; i++) {
				recommendedBy.stream().skip(i * 3).limit(3)
						.forEach(x -> x.markAsUsed());
				generatedVouchers++;
				createVoucer(client);
			}
		}

		return generatedVouchers;
	}

	private void createVoucer(Client client) {
		String code = UUID.randomUUID().toString();
		String description = "By recommendation";
		double balance = 25.0d;
		Voucher voucher = new Voucher(client, code, description, balance);
		pmRepo.add(voucher);
	}

}
