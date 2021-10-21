package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.UUID;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;

public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto mechanic;
	private MechanicGateway mg = PersistenceFactory.forMechanic();
	
	public AddMechanic(MechanicDto mechanic) {
		if (!isValidMechanic(mechanic))
			throw new IllegalArgumentException("invalid mechanic");
		this.mechanic = mechanic;
	}

	public MechanicDto execute() throws BusinessException {
		
		if (existMechanic(mechanic.dni))
			throw new BusinessException("Mechanic already exists");
		mechanic.id = UUID.randomUUID().toString();
	
		MechanicRecord mr = DtoAssembler.toRecord(mechanic);
		mg.add(mr);

		return mechanic;
		
	}
	
	
	private boolean isValidMechanic(MechanicDto mechanic)
	{
		if (mechanic == null) return false;
		if (mechanic.dni == null || mechanic.dni.isBlank()) return false;
		if (mechanic.name == null || mechanic.name.isBlank()) return false;
		if (mechanic.surname == null || mechanic.surname.isBlank()) return false;
		
		return true;
	}
	
	
	private boolean existMechanic(String dni)
	{
			
		return mg.findByDni(dni).isPresent();
	}
	
}
