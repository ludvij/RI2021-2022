package uo.ri.cws.application.service.paymentmean.voucher;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.service.paymentmean.VoucherService;
import uo.ri.cws.application.service.paymentmean.voucher.command.FindVouchersByClientId;
import uo.ri.cws.application.service.paymentmean.voucher.command.GenerateVouchers20;
import uo.ri.cws.application.service.paymentmean.voucher.command.GenerateVouchers25;
import uo.ri.cws.application.service.paymentmean.voucher.command.GenerateVouchers30;
import uo.ri.cws.application.service.paymentmean.voucher.command.GetVoucherSummary;
import uo.ri.cws.application.util.command.CommandExecutor;

public class VoucherServiceImpl implements VoucherService {

	private CommandExecutor executor = Factory.executor.forExecutor();
	
	@Override
	public int generateVouchers() throws BusinessException {
		int v20 = executor.execute(new GenerateVouchers20());
		int v25 = executor.execute(new GenerateVouchers25());
		int v30 = executor.execute(new GenerateVouchers30());
		return v20 + v25 + v30;
	}

	@Override
	public Optional<VoucherDto> findVouchersById(String id)
			throws BusinessException {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public List<VoucherDto> findVouchersByClientId(String id)
			throws BusinessException {
		return executor.execute(new FindVouchersByClientId(id));
	}

	@Override
	public List<VoucherSummaryDto> getVoucherSummary()
			throws BusinessException {
		return executor.execute(new GetVoucherSummary());
	}

}
