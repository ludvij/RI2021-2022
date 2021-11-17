package uo.ri.cws.application.service.client.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService;

public class ClientServiceImpl implements ClientCrudService {

	@Override
	public ClientDto addClient(ClientDto client, String recommenderId)
			throws BusinessException {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public void deleteClient(String idClient) throws BusinessException {
		throw new RuntimeException("Not yet implemented");

	}

	@Override
	public void updateClient(ClientDto client) throws BusinessException {
		throw new RuntimeException("Not yet implemented");

	}

	@Override
	public List<ClientDto> findAllClients() throws BusinessException {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public Optional<ClientDto> findClientById(String idClient)
			throws BusinessException {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public List<ClientDto> findClientsRecommendedBy(String sponsorID)
			throws BusinessException {
		throw new RuntimeException("Not yet implemented");
	}

}
