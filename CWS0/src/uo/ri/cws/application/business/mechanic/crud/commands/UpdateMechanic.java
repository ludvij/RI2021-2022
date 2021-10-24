package uo.ri.cws.application.business.mechanic.crud.commands;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;

public class UpdateMechanic implements Command<MechanicDto> {
	
	private MechanicDto mechanic;
	private MechanicGateway mg = PersistenceFactory.forMechanic();
	
	
	
	public UpdateMechanic(MechanicDto mechanic) {
		isValidMechanic(mechanic);
		
		this.mechanic = mechanic;
	}
	
	public MechanicDto execute() throws BusinessException {

		if (!existMechanic(mechanic.id))
			throw new BusinessException("Mechanic does not exists");
		
		MechanicRecord mr = DtoAssembler.toRecord(mechanic);
		mg.update(mr);
		
		return null;
	}
	
	
	private void isValidMechanic(MechanicDto mechanic)
	{
		Argument.isNotNull(mechanic,         "mechanic is null");
		Argument.isNotNull(mechanic.dni,     "mechanic.name is null");
		Argument.isNotNull(mechanic.name,    "mechanic.name is null");
		Argument.isNotNull(mechanic.surname, "mechanic.surname is null");
		
		Argument.isNotEmpty(mechanic.dni,     "mechanic.dni is empty");
		Argument.isNotEmpty(mechanic.name,    "mechanic.name is empty");
		Argument.isNotEmpty(mechanic.surname, "mechanic.surname is empty");
	}
	
	private boolean existMechanic(String id) 
	{
		return mg.findById(id).isPresent();
	}

}
