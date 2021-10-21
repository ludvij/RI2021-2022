package uo.ri.cws.application.business.mechanic.crud.commands;

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
		if (!isValidMechanic(mechanic))
			throw new IllegalArgumentException("Invalid mechanic");
		this.mechanic = mechanic;
	}
	
	public MechanicDto execute() throws BusinessException {

		if (!existMechanic(mechanic.id))
			throw new BusinessException("Mechanic does not exists");
		
		MechanicRecord mr = DtoAssembler.toRecord(mechanic);
		mg.update(mr);
		
		return null;
	}
	
	
	private boolean isValidMechanic(MechanicDto mechanic)
	{
		if (mechanic         == null)                               return false;
		if (mechanic.dni     == null || mechanic.dni.isBlank())     return false;
		if (mechanic.name    == null || mechanic.name.isBlank())    return false;
		if (mechanic.surname == null || mechanic.surname.isBlank()) return false;
		return true;	
	}
	
	
	private boolean existMechanic(String id) 
	{
		return mg.findById(id).isPresent();
	}

}
