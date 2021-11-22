package uo.ri.cws.application.service.paymentmean.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.PaymentMeanDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindById implements Command<Optional<PaymentMeanDto>>{

	private PaymentMeanRepository repo = Factory.repository.forPaymentMean();
	
	private String id;
	
	public FindById(String id) {
		ArgumentChecks.isNotNull(id);
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}
	
	
	
	@Override
	public Optional<PaymentMeanDto> execute() throws BusinessException {
		return repo.findById(id).map(DtoAssembler::toDto);
	}

}
