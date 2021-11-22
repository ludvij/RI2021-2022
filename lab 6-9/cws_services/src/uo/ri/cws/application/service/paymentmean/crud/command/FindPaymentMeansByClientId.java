package uo.ri.cws.application.service.paymentmean.crud.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.PaymentMeanDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindPaymentMeansByClientId implements Command<List<PaymentMeanDto>>{

	
	private PaymentMeanRepository repo = Factory.repository.forPaymentMean();
	
	private String id;
	
	public FindPaymentMeansByClientId(String id) {
		ArgumentChecks.isNotNull(id);
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}
	
	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		return DtoAssembler.toPaymentMeanDtoList(repo.findByClientId(id));
	}

}
