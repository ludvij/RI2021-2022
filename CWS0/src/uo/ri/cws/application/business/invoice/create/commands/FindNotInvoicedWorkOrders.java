package uo.ri.cws.application.business.invoice.create.commands;

import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingWorkOrderDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class FindNotInvoicedWorkOrders implements Command<List<InvoicingWorkOrderDto>> {
	
	private WorkOrderGateway wog = PersistenceFactory.forWorkOrder();
	
	private String customerDni;
	
	public FindNotInvoicedWorkOrders(String customerDni) {
		this.customerDni = customerDni;
	}



	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException 
	{
		if (!clientExists())
			throw new BusinessException("Client does not exist");
		var records = wog.findNotInvoiced(customerDni);
		
		return DtoAssembler.toInvoicingWorkOrderList(records);
	}
	
	
	private boolean clientExists() 
	{
		// TODO
		return true;
	}
	
}
