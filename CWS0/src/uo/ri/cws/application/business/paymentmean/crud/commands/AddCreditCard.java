package uo.ri.cws.application.business.paymentmean.crud.commands;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.paymentmean.CardDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.creditcard.CreditCardGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;

public class AddCreditCard implements Command<CardDto> {
	
	
	private CreditCardGateway cg  = PersistenceFactory.forCreditCard();
	private PaymentMeanGateway pg = PersistenceFactory.forPaymentmean();
	private CardDto card;
	
	public AddCreditCard(CardDto card) {
		validate(card);
		this.card = card;
	}


	@Override
	public CardDto execute() throws BusinessException {
		
		if (!clientExists(card.clientId))
			throw new BusinessException("client does not exist");
		if (isExpired(card.cardExpiration)) 
			throw new BusinessException("card is expired");		
		if (isRepeated(card.cardNumber)) 
			throw new BusinessException("card is repeated");
		
		card.id = UUID.randomUUID().toString();
		var record = DtoAssembler.toRecord(card);
		
		pg.add(record);
		cg.add(record);
		
		return null;
	}



	private boolean isExpired(LocalDate cardExpiration) 
	{
		var date = new Date(System.currentTimeMillis()).toLocalDate();
		return cardExpiration.compareTo(date) < 0;
	}
	
	
	private boolean isRepeated(String number) {
		var res = cg.findByNumber(number);
		
		return res.isPresent();
	}


	private boolean clientExists(String clientId) {
		ClientGateway cg = PersistenceFactory.forClient();
		
		var res = cg.findById(clientId);
		return res.isPresent();
	}
	private void validate(CardDto card) {
		Argument.isNotNull(card,                "card is null");
		Argument.isNotNull(card.cardNumber,     "card number is null");
		Argument.isNotNull(card.cardType,       "card type is null");
		Argument.isNotNull(card.clientId,       "client id is null");
		Argument.isNotNull(card.cardExpiration, "expiration date is null");
		
		Argument.isNotEmpty(card.cardNumber, "card number is empty");
		Argument.isNotEmpty(card.cardType,   "card type is empty");
		Argument.isNotEmpty(card.clientId,   "cleint is empty");
		
	}
}
