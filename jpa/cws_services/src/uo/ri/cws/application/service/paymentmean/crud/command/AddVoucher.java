package uo.ri.cws.application.service.paymentmean.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Voucher;

public class AddVoucher implements Command<VoucherDto> {

	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
	private ClientRepository clientRepo = Factory.repository.forClient();

	private VoucherDto dto;

	public AddVoucher(VoucherDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotNull(dto.id);
		ArgumentChecks.isNotNull(dto.code);
		ArgumentChecks.isNotNull(dto.clientId);
		ArgumentChecks.isNotNull(dto.description);

		ArgumentChecks.isNotEmpty(dto.id);
		ArgumentChecks.isNotEmpty(dto.code);
		ArgumentChecks.isNotEmpty(dto.clientId);
		ArgumentChecks.isNotEmpty(dto.description);

		ArgumentChecks.isTrue(dto.balance >= 0);

		this.dto = dto;
	}

	@Override
	public VoucherDto execute() throws BusinessException {
		Optional<Client> oc = clientRepo.findById(dto.clientId);
		BusinessChecks.exists(oc);

		Client c = oc.get();
		if (pmRepo.findVoucherByCode(dto.code).isPresent())
			throw new BusinessException("Voucher code already exists");

		Voucher v = new Voucher(c, dto.code, dto.description, dto.balance);
		pmRepo.add(v);
		dto.id = v.getId();

		return dto;
	}

}
