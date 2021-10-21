package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.stream.Stream;

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

		if (!existMechanic(mechanic.dni))
			throw new BusinessException("Mechanic does not exists");
		
		MechanicRecord mr = DtoAssembler.toRecord(mechanic);
		mg.update(mr);
		
		return null;
	}
	
	
	private boolean isValidMechanic(MechanicDto mechanic)
	{
		return !Stream.of(mechanic,
						  mechanic.dni, 
						  mechanic.id, 
						  mechanic.name, 
						  mechanic.surname).anyMatch(x -> x == null);		
	}
	
	
	private boolean existMechanic(String dni) 
	{
		return mg.findByDni(dni).isPresent();
	}

}
