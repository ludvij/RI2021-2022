package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.domain.CreditCard;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class PaymentMeanJpaRepository extends BaseJpaRepository<PaymentMean>
		implements PaymentMeanRepository {

	@Override
	public List<PaymentMean> findPaymentMeansByClientId(String id) {
		return Jpa.getManager()
				.createNamedQuery("PaymentMean.findPaymentMeansByClientId",
						PaymentMean.class)
				.setParameter(1, id).getResultList();
	}

	@Override
	public List<PaymentMean> findPaymentMeansByInvoiceId(String idFactura) {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public List<PaymentMean> findByClientId(String id) {
		return Jpa.getManager()
				.createNamedQuery("PaymentMean.findPaymentMeansByClientId",
						PaymentMean.class)
				.setParameter(1, id).getResultList();
	}

	@Override
	public Object[] findAggregateVoucherDataByClientId(String id) {
		return Jpa.getManager()
				.createNamedQuery("Voucher.findAggregateVoucherDataByClientId",
						Object[].class)
				.setParameter(1, id).getSingleResult();
	}

	@Override
	public Optional<CreditCard> findCreditCardByNumber(String pan) {
		return Jpa.getManager()
				.createNamedQuery("CreditCard.findCreditCardByNumber",
						CreditCard.class)
				.setParameter(1, pan).getResultStream().findFirst();
	}

	@Override
	public List<Voucher> findVouchersByClientId(String id) {
		return Jpa.getManager()
				.createNamedQuery("Voucher.findByClientId", Voucher.class)
				.setParameter(1, id).getResultList();
	}

	@Override
	public Optional<Voucher> findVoucherByCode(String code) {
		return Jpa.getManager()
				.createNamedQuery("Voucher.findVoucherByCode", Voucher.class)
				.setParameter(1, code).getResultStream().findFirst();
	}

}
