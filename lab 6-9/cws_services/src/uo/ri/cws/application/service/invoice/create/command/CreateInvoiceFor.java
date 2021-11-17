package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;

public class CreateInvoiceFor implements Command<InvoiceDto>{

	private List<String> workOrderIds;
	private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
	private InvoiceRepository invsRepo = Factory.repository.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		ArgumentChecks.isNotNull( workOrderIds );
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		Long number = invsRepo.getNextInvoiceNumber();
		List<WorkOrder> wrkOrders = wrkrsRepo.findByIds(workOrderIds);
		
		checkAllExists(wrkOrders); 
		checkAllFinished(wrkOrders); 
		
		Invoice i = new Invoice(number, wrkOrders);
		invsRepo.add(i);
		
		return DtoAssembler.toDto(i);
	}
	
	private void checkAllExists(List<WorkOrder> workOrders) 
			throws BusinessException 
	{
		if (workOrders.size() != workOrderIds.size())
			throw new BusinessException("A workOrder doesn't exist");
	}
	
	private void checkAllFinished(List<WorkOrder> workOrders) 
			throws BusinessException 
	{
		if (!workOrders.stream().allMatch(WorkOrder::isFinished))
			throw new BusinessException("some workOrders are not finished");
	}

}
