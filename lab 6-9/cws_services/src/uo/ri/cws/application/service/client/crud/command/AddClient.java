package uo.ri.cws.application.service.client.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.repository.RecommendationRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Address;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Recommendation;

public class AddClient implements Command<ClientDto> {

	private ClientRepository clientRepo = Factory.repository.forClient();
	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
	private RecommendationRepository recRepo = Factory.repository
			.forRecomendacion();

	private String recommenderId;
	private ClientDto dto;

	public AddClient(ClientDto dto, String recommenderId) {
		ArgumentChecks.isNotNull(dto);

		ArgumentChecks.isNotEmpty(dto.dni);

		this.dto = dto;
		this.recommenderId = recommenderId;
	}

	@Override
	public ClientDto execute() throws BusinessException {
		if (clientRepo.findByDni(dto.dni).isPresent())
			throw new BusinessException("client is repeated");

		Address a = new Address(dto.addressStreet, dto.addressCity,
				dto.addressZipcode);
		Client c = new Client(dto.dni, dto.name, dto.surname, dto.email,
				dto.phone, a);
		Cash cash = new Cash(c);

		clientRepo.add(c);
		pmRepo.add(cash);

		if (recommenderId != null) {
			Optional<Client> oc = clientRepo.findById(recommenderId);
			BusinessChecks.exists(oc);
			Client sponsor = oc.get();

			Recommendation r = new Recommendation(sponsor, c);
			
			recRepo.add(r);
		}

		dto.id = c.getId();
		return dto;
	}

}
