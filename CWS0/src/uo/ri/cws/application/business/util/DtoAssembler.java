package uo.ri.cws.application.business.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.invoice.InvoicingWorkOrderDto;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.paymentmean.CardDto;
import uo.ri.cws.application.business.paymentmean.CashDto;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherDto;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherSummaryDto;
import uo.ri.cws.application.persistence.cash.CashRecord;
import uo.ri.cws.application.persistence.client.ClientRecord;
import uo.ri.cws.application.persistence.creditcard.CreditCardRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.paymentmean.PaymentmeanRecord;
import uo.ri.cws.application.persistence.voucher.VoucherRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class DtoAssembler {

	public static Optional<MechanicDto> toDto(Optional<MechanicRecord> arg) {
		Optional<MechanicDto> result = arg.isEmpty()?Optional.ofNullable(null)
				:Optional.ofNullable(toDto(arg.get()));
		return result;
	}

	public static List<MechanicDto> toDtoList(List<MechanicRecord> arg) {
		List<MechanicDto> result = new ArrayList<MechanicDto> ();
		for (MechanicRecord mr : arg) 
			result.add(toDto(mr));
		return result;
	}
	
	private static MechanicDto toDto(MechanicRecord arg)
	{
		MechanicDto result = new MechanicDto();
		result.id      = arg.id;
		result.dni     = arg.dni;
		result.name    = arg.name;
		result.surname = arg.surname;
		
		return result;
	}
	
	public static MechanicRecord toRecord(MechanicDto arg) {
		MechanicRecord result = new MechanicRecord ();
		result.id      = arg.id;
		result.dni     = arg.dni;
		result.name    = arg.name;
		result.surname = arg.surname;
		return result;
	}

	
	
	public static Optional<InvoiceDto> toInvoiceDto(Optional<InvoiceRecord> arg) {
		Optional<InvoiceDto> result = arg.isEmpty()?Optional.ofNullable(null)
				:Optional.ofNullable(toDto(arg.get()));
		return result;
	}



	private static InvoiceDto toDto(InvoiceRecord arg) {
		InvoiceDto result = new InvoiceDto();
		result.id     = arg.id;
		result.number = arg.number;
		result.status = arg.status; //?
		result.date   = arg.date;
		result.total  = arg.amount;
		result.vat    = arg.vat;
		return result;
	}
	
	public static InvoiceRecord toRecord(InvoiceDto arg) {
		InvoiceRecord result = new InvoiceRecord();
		result.id     = arg.id;
		result.number = arg.number;
		result.status = arg.status; //?
		result.date   = arg.date;
		result.amount = arg.total;
		result.vat    = arg.vat;
		return result;
	}

	public static List<InvoicingWorkOrderDto> toInvoicingWorkOrderList(List<WorkOrderRecord> arg) {
		List<InvoicingWorkOrderDto> result = new ArrayList<InvoicingWorkOrderDto> ();
		for (WorkOrderRecord record : arg) 
			result.add(toDto(record));
		return result;
	}

	
	
	private static InvoicingWorkOrderDto toDto(WorkOrderRecord record) {
		InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
		dto.id          = record.id;
		dto.date        = record.date;
		dto.description = record.description;
		dto.date        = record.date;
		dto.status      = record.status;
		dto.total       = record.amount;
		
		return dto;
	}

	public static List<VoucherDto> toVoucherDtoList(List<VoucherRecord> records) {
		List<VoucherDto> res = new ArrayList<>();
		for (var record : records)
			res.add(toDto(record));
		return res;
	}


	private static VoucherDto toDto(VoucherRecord record) {
		VoucherDto dto = new VoucherDto();
		dto.id      = record.id;
		dto.version = record.version;
		
		dto.clientId = record.client_id;
		dto.code     = record.code;
		
		dto.accumulated = record.accumulated;
		dto.balance     = record.available;
		dto.description = record.description;
		
		
		return dto;
	}

	public static List<CardDto> toCreditCardDtoList(List<CreditCardRecord> arg) {
		List<CardDto> result = new ArrayList<> ();
		for (var record : arg) 
			result.add(toDto(record));
		return result;
	}

	public static CreditCardRecord toRecord(CardDto card) {
		CreditCardRecord record = new CreditCardRecord();
		record.id      = card.id;
		record.version = card.version;
		
		record.number    = card.cardNumber;
		record.type      = card.cardType;
		record.validthru = card.cardExpiration;
		
		record.client_id   = card.clientId;
		record.accumulated = card.accumulated;
		record.dtype       = "CREDITCARD";
		
		return record;
	}
	
	private static CardDto toDto(CreditCardRecord arg)
	{
		CardDto dto = new CardDto();
		dto.id      = arg.id;
		dto.version = arg.version;
		
		dto.accumulated = arg.accumulated;
		dto.clientId    = arg.client_id;
		
		
		dto.cardExpiration = arg.validthru;
		dto.cardNumber     = arg.number;
		dto.cardType       = arg.type;
		
		return dto;
	}

	public static VoucherRecord toRecord(VoucherDto arg) {
		VoucherRecord record = new VoucherRecord();
		
		record.id      = arg.id;
		record.version = arg.version;
		
		record.client_id   = arg.clientId;
		record.accumulated = arg.accumulated;
		record.dtype       = "VOUCHER";
		
		record.available   = arg.balance;
		record.description = arg.description;
		record.code        = arg.code;
		
		return record;
	}

	public static Optional<CashDto> toCashDto(Optional<CashRecord> arg) {
		CashDto dto = null;
		
		if (arg.isPresent())
			dto = toDto(arg.get());
		
		return Optional.ofNullable(dto);
	}

	private static CashDto toDto(CashRecord arg) {
		CashDto dto = new CashDto();
		dto.id = arg.id;
		dto.version = arg.version;
		
		dto.accumulated = arg.accumulated;
		dto.clientId = arg.client_id;
		return dto;
	}

	public static VoucherSummaryDto toVoucherSummaryDto(ClientRecord client, int issued, double balance, double accumulated) {
		 VoucherSummaryDto dto = new VoucherSummaryDto();
		
		dto.dni     = client.dni;
		dto.name    = client.name;
		dto.surname = client.surname;
		
		dto.totalAmount      = accumulated + balance;
		dto.availableBalance = balance;
		dto.consumed         = accumulated;
		dto.issued           = issued;
		return dto;
	}
	
	public static CardDto toCardDto(PaymentmeanRecord arg, CreditCardRecord card) {
		CardDto dto = new CardDto();
		
		dto.id      = arg.id;
		dto.version = arg.version;
		
		dto.accumulated = arg.accumulated;
		dto.clientId    = arg.client_id;
		
		dto.cardExpiration = card.validthru;
		dto.cardNumber     = card.number;
		dto.cardType       = card.type;
				
		return dto;
	}

	public static VoucherDto toVoucherDto(PaymentmeanRecord arg, VoucherRecord voucher) {
		VoucherDto dto = new VoucherDto();
		
		dto.id      = arg.id;
		dto.version = arg.version;
		
		dto.accumulated = arg.accumulated;
		dto.clientId    = arg.client_id;
		
		dto.balance     = voucher.available;
		dto.code        = voucher.code;
		dto.description = voucher.description;
				
		return dto;
	}

	public static CashDto toCashDto(PaymentmeanRecord payment, CashRecord cash) {
		CashDto dto = new CashDto();
		
		dto.id      = payment.id;
		dto.version = payment.version;
		
		dto.accumulated = payment.accumulated;
		dto.clientId    = payment.client_id;		
		
		return dto;
	}
	
}
