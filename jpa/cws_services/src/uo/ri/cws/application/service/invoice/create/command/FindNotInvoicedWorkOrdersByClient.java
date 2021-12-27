package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;
import java.util.stream.Collectors;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.DtoAssembler;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Vehicle;

public class FindNotInvoicedWorkOrdersByClient
		implements Command<List<InvoicingWorkOrderDto>> {

	private ClientRepository clientRepo = Factory.repository.forClient();

	private String dni;

	public FindNotInvoicedWorkOrdersByClient(String dni) {
		ArgumentChecks.isNotEmpty(dni);
		this.dni = dni;
	}

	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		var oc = clientRepo.findByDni(dni);
		BusinessChecks.exists(oc);
		var c = oc.get();

		return DtoAssembler.toWorkOrderDtoList(c.getVehicles().stream()
				.map(Vehicle::getWorkOrders).flatMap(x -> x.stream())
				.filter(x -> !x.isInvoiced()).collect(Collectors.toList()));

	}

}
