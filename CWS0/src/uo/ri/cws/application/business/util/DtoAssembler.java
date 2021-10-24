package uo.ri.cws.application.business.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.invoice.InvoicingWorkOrderDto;
import uo.ri.cws.application.business.invoice.InvoiceDto.InvoiceStatus;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherDto;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.paymentMean.voucher.VoucherRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class DtoAssembler {

	public static Optional<MechanicDto> toDto(Optional<MechanicRecord> arg) {
		Optional<MechanicDto> result = arg.isEmpty()?Optional.ofNullable(null)
				:Optional.ofNullable(toMechanicDto(arg.get()));
		return result;
	}

	public static List<MechanicDto> toDtoList(List<MechanicRecord> arg) {
		List<MechanicDto> result = new ArrayList<MechanicDto> ();
		for (MechanicRecord mr : arg) 
			result.add(toMechanicDto(mr));
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

	private static MechanicDto toMechanicDto(MechanicRecord arg) {

		MechanicDto result = new MechanicDto();
		result.id      = arg.id;
		result.name    = arg.name;
		result.surname = arg.surname;
		result.dni     = arg.dni;
		return result;
	}
	
	
	public static Optional<InvoiceDto> toInvoiceDto(Optional<InvoiceRecord> arg) {
		Optional<InvoiceDto> result = arg.isEmpty()?Optional.ofNullable(null)
				:Optional.ofNullable(toInvoiceDto(arg.get()));
		return result;
	}



	private static InvoiceDto toInvoiceDto(InvoiceRecord arg) {
		InvoiceDto result = new InvoiceDto();
		result.id     = arg.id;
		result.number = arg.number;
		result.status = InvoiceStatus.valueOf(arg.status); //?
		result.date   = arg.date;
		result.total  = arg.amount;
		result.vat    = arg.vat;
		return result;
	}
	
	public static InvoiceRecord toRecord(InvoiceDto arg) {
		InvoiceRecord result = new InvoiceRecord();
		result.id     = arg.id;
		result.number = arg.number;
		result.status = arg.status+""; //?
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
		
		dto.accumulated = record.accumulated;
		dto.balance     = record.balance;
		dto.clientId    = record.clientId;
		dto.code        = record.code;
		dto.description = record.description;
		dto.id          = record.id;
		dto.version     = record.version;
		
		return dto;
	}
}
