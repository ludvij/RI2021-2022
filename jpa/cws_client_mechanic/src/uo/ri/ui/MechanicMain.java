package uo.ri.ui;

import alb.util.console.Printer;
import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessFactory;
import uo.ri.cws.infrastructure.persistence.jpa.executor.JpaExecutorFactory;
import uo.ri.cws.infrastructure.persistence.jpa.repository.JpaRepositoryFactory;

public class MechanicMain {

	private static class MainMenu extends BaseMenu {
		MainMenu() {
			menuOptions = new Object[][] { 
				{ "Mechanic", null },
				{ "List assigned work orders", 		NotYetImplementedAction.class }, 
				{ "Add parts to work order", 		NotYetImplementedAction.class },
				{ "Remove parts from work order", 	NotYetImplementedAction.class },
				{ "Close a work order", 			NotYetImplementedAction.class },
			};
		}
	}

	private MainMenu menu = new MainMenu();
	
	public static void main(String[] args) {
		new MechanicMain()
			.config()
			.run()
			.close();
	}

	private MechanicMain config() {
		Factory.service = new BusinessFactory();
		Factory.repository = new JpaRepositoryFactory();
		Factory.executor = new JpaExecutorFactory();

		return this;
	}

	public MechanicMain run() {
		try {
			menu.execute();

		} catch (RuntimeException rte) {
			Printer.printRuntimeException(rte);
		}
		return this;
	}

	private void close() {
	}

}
