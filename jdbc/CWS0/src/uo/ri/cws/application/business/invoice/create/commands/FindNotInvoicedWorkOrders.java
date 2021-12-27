package uo.ri.cws.application.business.invoice.create.commands;

import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingWorkOrderDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class FindNotInvoicedWorkOrders implements Command<List<InvoicingWorkOrderDto>> {
	
	private ClientGateway cg     = PersistenceFactory.forClient();
	private WorkOrderGateway wog = PersistenceFactory.forWorkOrder();
	private VehicleGateway vg    = PersistenceFactory.forVehicle();
	
	private String customerDni;
	
	public FindNotInvoicedWorkOrders(String customerDni) {
		if (customerDni == null || customerDni.isBlank())
			throw new IllegalArgumentException("invalid dni");
		this.customerDni = customerDni;
	}



	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException 
	{
		var client = cg.findByDni(customerDni);
		if (client.isEmpty())
			throw new BusinessException("Client does not exist");
		
		var workOrders = vg.findByClientId(client.get().id).stream()
				.map(x -> wog.findByVehicleId(x.id))
				.flatMap(x -> x.stream())
				.filter(x -> !x.status.equals("INVOICED"))
				.toList();
	
		
		return DtoAssembler.toInvoicingWorkOrderList(workOrders);
	}
	
}
