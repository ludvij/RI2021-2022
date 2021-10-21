package uo.ri.cws.application.business.invoice.create.commands;

import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingWorkOrderDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class FindNotInvoicedWorkOrders implements Command<List<InvoicingWorkOrderDto>> {
	
	private WorkOrderGateway wog = PersistenceFactory.forWorkOrder();
	
	private String customerDni;
	
	public FindNotInvoicedWorkOrders(String customerDni) {
		if (customerDni == null || customerDni.isBlank())
			throw new IllegalArgumentException("invalid dni");
		this.customerDni = customerDni;
	}



	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException 
	{
		if (!clientExists(customerDni))
			throw new BusinessException("Client does not exist");
		var records = wog.findNotInvoiced(customerDni);
		
		return DtoAssembler.toInvoicingWorkOrderList(records);
	}
	
	
	private boolean clientExists(String dni) 
	{
		ClientGateway cg = PersistenceFactory.forClient();
		return cg.findByDni(dni).isPresent();
	}
	
}
