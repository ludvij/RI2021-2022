package uo.ri.cws.application.business.invoice.create;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.ChargeDto;
import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.InvoicingWorkOrderDto;
import uo.ri.cws.application.business.invoice.PaymentMeanForInvoicingDto;
import uo.ri.cws.application.business.invoice.create.commands.CreateInvoice;
import uo.ri.cws.application.business.invoice.create.commands.FindNotInvoicedWorkOrders;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class InvoicingServiceImpl implements InvoicingService {
	
	private CommandExecutor ce = new CommandExecutor();

	@Override
	public InvoiceDto createInvoiceFor(List<String> workOrderIds) throws BusinessException {
		if (workOrderIds == null)
			throw new IllegalArgumentException("invalid ids");
		if (workOrderIds.isEmpty())
			throw new IllegalArgumentException("list is empty");
		if (workOrderIds.stream().anyMatch(x -> x == null || x.isEmpty()))
			throw new IllegalArgumentException("workorder id is null or empty");
		return ce.execute(new CreateInvoice(workOrderIds));
	}

	@Override
	public List<InvoicingWorkOrderDto> findWorkOrdersByClientDni(String dni) throws BusinessException {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public List<InvoicingWorkOrderDto> findNotInvoicedWorkOrdersByClientDni(String dni) throws BusinessException {
		return ce.execute(new FindNotInvoicedWorkOrders(dni));
	}

	@Override
	public List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate) throws BusinessException {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public Optional<InvoiceDto> findInvoiceByNumber(Long number) throws BusinessException {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public List<PaymentMeanForInvoicingDto> findPayMeansByClientDni(String dni) throws BusinessException {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public void settleInvoice(String invoiceId, List<ChargeDto> charges) throws BusinessException {
		throw new RuntimeException("Not yet implemented");
		
	}

}
