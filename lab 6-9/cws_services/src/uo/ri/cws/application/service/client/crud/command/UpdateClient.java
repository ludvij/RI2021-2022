package uo.ri.cws.application.service.client.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Address;
import uo.ri.cws.domain.Client;

public class UpdateClient implements Command<Void> {

	private ClientRepository repo = Factory.repository.forClient();
	
	private ClientDto dto;
	
	public UpdateClient(ClientDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotEmpty(dto.id);
		this.dto = dto;
	}
	
	@Override
	public Void execute() throws BusinessException {
		Optional<Client> oc = repo.findById(dto.id);
		
		BusinessChecks.exists(oc);
		
		Client c = oc.get();
		Address a = new Address(dto.addressStreet, dto.addressCity, dto.addressZipcode);
		
		BusinessChecks.hasVersion(c, dto.version);
		
		c.setName(dto.name);
		c.setSurname(dto.surname);
		c.setPhone(dto.phone);
		c.setEmail(dto.email);
		c.setAddress(a);
		
		return null;
	}

}
