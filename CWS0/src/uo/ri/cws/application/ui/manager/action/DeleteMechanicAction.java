package uo.ri.cws.application.ui.manager.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicCrudService;

public class DeleteMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		// get info
		String idMechanic = Console.readString("Type mechanic id "); 
		
		MechanicCrudService service = BusinessFactory.forMechanicCrudService();
		
		service.deleteMechanic(idMechanic);
		
		// print result
		Console.println("Mechanic deleted");
	}

}
