package uo.ri.cws.application.business.paymentmean.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.paymentmean.CardDto;
import uo.ri.cws.application.business.paymentmean.PaymentMeanDto;
import uo.ri.cws.application.business.paymentmean.PaymentmeanCrudService;
import uo.ri.cws.application.business.paymentmean.crud.commands.AddCreditCard;
import uo.ri.cws.application.business.paymentmean.crud.commands.AddVoucher;
import uo.ri.cws.application.business.paymentmean.crud.commands.DeletePaymentMean;
import uo.ri.cws.application.business.paymentmean.crud.commands.FindById;
import uo.ri.cws.application.business.paymentmean.crud.commands.FindByclientId;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherDto;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class PaymentmeanCrudServiceImpl implements PaymentmeanCrudService {
	
	private CommandExecutor ce = new CommandExecutor();

	@Override
	public void addCardPaymentMean(CardDto card) throws BusinessException {
		ce.execute(new AddCreditCard(card));

	}

	@Override
	public void addVoucherPaymentMean(VoucherDto voucher) throws BusinessException {
		ce.execute(new AddVoucher(voucher));

	}

	@Override
	public void deletePaymentMean(String id) throws BusinessException {
		ce.execute(new DeletePaymentMean(id));

	}

	@Override
	public Optional<PaymentMeanDto> findById(String id) throws BusinessException {
		return ce.execute(new FindById(id));
	}

	@Override
	public List<PaymentMeanDto> findPaymentMeansByClientId(String id) throws BusinessException {
		return ce.execute(new FindByclientId(id));
	}

}
