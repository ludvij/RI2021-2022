package uo.ri.cws.application.service.paymentmean.crud.command;

import java.time.LocalDate;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CardDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.CreditCard;

public class AddCard implements Command<CardDto> {

	private PaymentMeanRepository repo = Factory.repository.forPaymentMean();

	private CardDto card;

	public AddCard(CardDto card) {
		ArgumentChecks.isNotNull(card);
		ArgumentChecks.isNotNull(card.id);
		ArgumentChecks.isNotNull(card.clientId);
		ArgumentChecks.isNotNull(card.cardType);
		ArgumentChecks.isNotNull(card.cardNumber);
		ArgumentChecks.isNotNull(card.cardExpiration);

		ArgumentChecks.isNotEmpty(card.id);
		ArgumentChecks.isNotEmpty(card.clientId);
		ArgumentChecks.isNotEmpty(card.cardType);
		ArgumentChecks.isNotEmpty(card.cardNumber);

		this.card = card;
	}

	@Override
	public CardDto execute() throws BusinessException {

		// check if client exists
		Optional<Client> oc = Factory.repository.forClient()
				.findById(card.clientId);
		BusinessChecks.exists(oc);
		Client c = oc.get();

		if (card.cardExpiration.isBefore(LocalDate.now()))
			throw new BusinessException("Card is expired");

		if (repo.findCreditCardByNumber(card.cardNumber).isPresent())
			throw new BusinessException("Credit card is repeated");

		CreditCard cc = new CreditCard(c, card.cardNumber, card.cardType,
				card.cardExpiration);
		
		card.id = cc.getId();
		repo.add(cc);
		
		return card;
	}

}
