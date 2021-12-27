package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.Optional;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class FindMechanicByDni implements Command<Optional<MechanicDto>> {

	private String dni;
	
	private MechanicGateway mg = PersistenceFactory.forMechanic();
	
	public FindMechanicByDni(String dni) {
		Argument.isNotNull(dni, "mechanic.dni is null");
		Argument.isNotEmpty(dni, "mechanic.dni is empty");

		this.dni = dni;
	}

	@Override
	public Optional<MechanicDto> execute() throws BusinessException {
		
		var mechanic = mg.findByDni(dni);
		
		return DtoAssembler.toDto(mechanic);
		
	}
}
