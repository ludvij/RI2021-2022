package uo.ri.cws.application.business.mechanic.crud.commands;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class DeleteMechanic implements Command<MechanicDto> {
	
	
	private String id;	
	private MechanicGateway mg = PersistenceFactory.forMechanic();
	
	
	public DeleteMechanic(String id) {
		if (id == null)
			throw new IllegalArgumentException("invalid id");
		this.id = id;
	}
	
	
	public MechanicDto execute() throws BusinessException { 
		if (!exists(id)) 
			throw new BusinessException("Mechanic does not exist");
		if (linked(id))
			throw new BusinessException("Mechanic is linked to a workorder");
		mg.remove(id);
		
		return null;
	}
	
	private boolean linked(String id) 
	{
		var wog = PersistenceFactory.forWorkOrder();
		
		return !wog.findByMechanicId(id).isEmpty();
	}
	
	
	private boolean exists(String id)
	{
		return !mg.findById(id).isEmpty(); 
		
	}
	
}



