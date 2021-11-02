package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class FindAllMechanics implements Command<List<MechanicDto>> {
	
	private MechanicGateway mg = PersistenceFactory.forMechanic();

	public List<MechanicDto> execute() throws BusinessException {
		
		var res = mg.findAll();
		return DtoAssembler.toDtoList(res);
		
	}

}
