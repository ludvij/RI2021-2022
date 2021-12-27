package uo.ri.cws.application.business.client.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.client.ClientCrudService;
import uo.ri.cws.application.business.client.ClientDto;

public class ClientCrudServiceImpl implements ClientCrudService {

	@Override
	public ClientDto addClient(ClientDto client, String recommenderId) throws BusinessException {
		throw new RuntimeException("Not yet implemented");
		return null;
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
		return null;
	}

	@Override
	public Optional<ClientDto> findClientById(String idClient) throws BusinessException {
		throw new RuntimeException("Not yet implemented");
		return null;
	}

	@Override
	public List<ClientDto> findClientsRecommendedBy(String sponsorID) throws BusinessException {
		throw new RuntimeException("Not yet implemented");
		return null;
	}

}
