package uo.ri.cws.application.service.mechanic.crud.command;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class DeleteMechanic implements Command<Void>{

	private String mechanicId;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotNull(mechanicId);
		ArgumentChecks.isNotEmpty(mechanicId);
		
		this.mechanicId = mechanicId;
	}

	public Void execute() throws BusinessException {
		
		var om = repo.findById(mechanicId);
		BusinessChecks.exists(om, "mechanic does not exists");
		
		var m = om.get();
		checkCanBeDeleted(m);
		
		repo.remove(m);
		
		return null;
	}
	
	
	private void checkCanBeDeleted(Mechanic  m ) throws BusinessException {

		BusinessChecks.isTrue(m.getAssigned().isEmpty(),      
				"mechanic has dependencies");
		BusinessChecks.isTrue(m.getInterventions().isEmpty(), 
				"mechanic has dependencies");
	}

}
