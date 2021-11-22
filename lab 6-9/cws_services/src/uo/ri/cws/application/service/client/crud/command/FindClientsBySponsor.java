package uo.ri.cws.application.service.client.crud.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindClientsBySponsor implements Command<List<ClientDto>> {
	
	private ClientRepository repo = Factory.repository.forClient();
	
	private String id;
	
	public FindClientsBySponsor(String id) {
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}
	
	
	@Override
	public List<ClientDto> execute() throws BusinessException { 
		return DtoAssembler.toClientDtoList(repo.findSponsoredByClient(id));
	}

}
