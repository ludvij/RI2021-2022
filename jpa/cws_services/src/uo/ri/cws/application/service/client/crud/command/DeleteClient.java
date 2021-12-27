package uo.ri.cws.application.service.client.crud.command;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.repository.RecommendationRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Associations;
import uo.ri.cws.domain.Client;

public class DeleteClient implements Command<Void> {

	private ClientRepository clientRepo = Factory.repository.forClient();
	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
	private RecommendationRepository recRepo = Factory.repository
			.forRecomendacion();

	private String id;

	public DeleteClient(String id) {
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		var oc = clientRepo.findById(id);
		BusinessChecks.exists(oc);

		Client c = oc.get();

		if (!c.getVehicles().isEmpty()) {
			throw new BusinessException("client has vehicles");
		}
		// TODO: preguntar por esto=========================
		for (var sponsored : c.getSponsored()) {
			Associations.Recommend.unlink(sponsored);
			recRepo.remove(sponsored);
		}
		if (c.getRecommended() != null) {
			Associations.Recommend.unlink(c.getRecommended());
			recRepo.remove(c.getRecommended());
		}
		//===================================================

		for (var pm : c.getPaymentMeans()) {
			pmRepo.remove(pm);
		}
		clientRepo.remove(c);

		return null;
	}

}
