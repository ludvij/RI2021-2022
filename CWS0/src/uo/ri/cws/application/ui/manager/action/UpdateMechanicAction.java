package uo.ri.cws.application.ui.manager.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicCrudService;
import uo.ri.cws.application.business.mechanic.MechanicDto;

public class UpdateMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		// Get info
		MechanicDto mechanic = new MechanicDto();
		mechanic.id      = Console.readString("Type mechahic id to update"); 
		mechanic.name    = Console.readString("Name"); 
		mechanic.surname = Console.readString("Surname");
		
		MechanicCrudService service = BusinessFactory.forMechanicCrudService();
		
		service.updateMechanic(mechanic);
		
		// Print result
		Console.println("Mechanic updated");
	}

}
