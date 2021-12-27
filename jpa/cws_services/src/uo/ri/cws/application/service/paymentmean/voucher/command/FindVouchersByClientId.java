package uo.ri.cws.application.service.paymentmean.voucher.command;

import java.util.List;
import java.util.stream.Collectors;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindVouchersByClientId implements Command<List<VoucherDto>> {

	private PaymentMeanRepository repo = Factory.repository.forPaymentMean();

	private String id;

	public FindVouchersByClientId(String id) {
		ArgumentChecks.isNotNull(id);
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}

	@Override
	public List<VoucherDto> execute() throws BusinessException {
		return repo.findVouchersByClientId(id).stream()
				.map(DtoAssembler::toDto)
				.collect(Collectors.toList());
	}

}
