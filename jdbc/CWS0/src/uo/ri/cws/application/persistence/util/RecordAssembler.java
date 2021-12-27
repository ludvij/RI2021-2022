package uo.ri.cws.application.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.cash.CashRecord;
import uo.ri.cws.application.persistence.charge.ChargeRecord;
import uo.ri.cws.application.persistence.client.ClientRecord;
import uo.ri.cws.application.persistence.creditcard.CreditCardRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.paymentmean.PaymentmeanRecord;
import uo.ri.cws.application.persistence.recommendation.RecommendationRecord;
import uo.ri.cws.application.persistence.vehicle.VehicleRecord;
import uo.ri.cws.application.persistence.voucher.VoucherRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class RecordAssembler {

	public static Optional<MechanicRecord> toMechanicRecord(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToMechanicRecord(m));
		}
		else 	
			return Optional.ofNullable(null);
	}
	

	public static List<MechanicRecord> toMechanicRecordList(ResultSet rs) throws SQLException {
		List<MechanicRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToMechanicRecord(rs));
		}

		return res;
	}
	
	public static List<ClientRecord> toClientRecordList(ResultSet rs) throws SQLException {
		List<ClientRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToClientRecord(rs));
		}

		return res;
	}

	private static MechanicRecord resultSetToMechanicRecord(ResultSet rs) throws SQLException {
		MechanicRecord value = new MechanicRecord();
		value.id = rs.getString("id");

		value.dni     = rs.getString("dni");
		value.name    = rs.getString("name");
		value.surname = rs.getString("surname");
		return value;
	}

	public static Optional<WorkOrderRecord> toWorkOrderRecord ( ResultSet rs ) throws SQLException {
		WorkOrderRecord record = null;
		
		if (rs.next()) {
			record = resultSetToWorkOrderRecord(rs);
			}
		return Optional.ofNullable(record);
		
	}

	public static List<WorkOrderRecord> toWorkOrderRecordList(ResultSet rs) throws SQLException {
		List<WorkOrderRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToWorkOrderRecord(rs)	);
		}
		
		return res;
	}
	
	
	private static WorkOrderRecord resultSetToWorkOrderRecord ( ResultSet rs ) throws SQLException {
		WorkOrderRecord record = new WorkOrderRecord();
		
		record.id = rs.getString("id");
		record.version = rs.getLong("version");

		record.vehicle_id  = rs.getString("vehicle_Id");
		record.description = rs.getString("description");
		record.date        = rs.getTimestamp("date").toLocalDateTime();//sqlDate.toLocalDate(); 
		record.amount      = rs.getDouble("amount");
		record.status      = rs.getString("status");
		record.mechanic_id = rs.getString("mechanic_Id");
		record.invoice_id  = rs.getString("invoice_Id");
		
		return record;		
	}
	
	public static Optional<InvoiceRecord> toInvoiceRecord ( ResultSet rs ) throws SQLException {
		InvoiceRecord record = null;
		
		if (rs.next()) {
			record = resultSetToInvoiceRecord(rs);
			}
		return Optional.ofNullable(record);
		
	}

	public static List<InvoiceRecord> toInvoiceRecordList(ResultSet rs) throws SQLException {
		List<InvoiceRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToInvoiceRecord(rs)	);
		}
		
		return res;
	}
	
	private static InvoiceRecord resultSetToInvoiceRecord ( ResultSet rs ) throws SQLException {
		InvoiceRecord record = new InvoiceRecord();
		
		record.id      = rs.getString("id");
		record.version = rs.getLong("version");
		
		record.number = rs.getLong("number");
		record.vat    = rs.getDouble("vat");
		record.date   = rs.getDate("date").toLocalDate();//sqlDate.toLocalDate(); 
		record.amount = rs.getDouble("amount");
		record.status = rs.getString("status");
		
		
		return record;		
	}
	
	
	public static Optional<ClientRecord> toClientRecord ( ResultSet rs ) throws SQLException {
		ClientRecord record = null;
		
		if (rs.next()) {
			record = resultSetToClientRecord(rs);
		}
		return Optional.ofNullable(record);
		
	}
	
	
	private static ClientRecord resultSetToClientRecord(ResultSet rs) throws SQLException {
		ClientRecord record = new ClientRecord();
		
		record.id      = rs.getString("id");
		record.version = rs.getLong("version");
		
		record.dni     = rs.getString("dni");
		record.name    = rs.getString("name");
		record.surname = rs.getString("surname");
		
		record.email = rs.getString("email");
		record.phone = rs.getString("phone");
		
		record.addressCity    = rs.getString("city");
		record.addressStreet  = rs.getString("street");
		record.addressZipcode = rs.getString("zipcode");
		
		return record;
	
	}


	public static List<VoucherRecord> toVoucherRecordList(ResultSet rs) throws SQLException {
		List<VoucherRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToVoucherRecord(rs)	);
		}
		return res;
	}


	private static VoucherRecord resultSetToVoucherRecord(ResultSet rs) throws SQLException {
		VoucherRecord record = new VoucherRecord();
					
		record.available   = rs.getDouble("available");
		record.code        = rs.getString("code");
		record.description = rs.getString("description");
		
		return record;
	}


	public static Optional<CreditCardRecord> toCreditCardRecord(ResultSet rs) throws SQLException {
		CreditCardRecord record = null;
		if (rs.next()) {
			record = resultSetToCreditCardRecord(rs);
		}
		return Optional.ofNullable(record);
		
	}


	private static CreditCardRecord resultSetToCreditCardRecord(ResultSet rs) throws SQLException {
		CreditCardRecord record = new CreditCardRecord();
		
		
		record.number    = rs.getString("number");
		record.type      = rs.getString("type");
		record.validthru = rs.getDate("validThru").toLocalDate();
		
		return record;
	}


	public static Optional<VoucherRecord> toVoucherRecord(ResultSet rs) throws SQLException {
		VoucherRecord record = null;
		if (rs.next()) {
			record = resultSetToVoucherRecord(rs);
		}
		return Optional.ofNullable(record);
	}


	public static List<CreditCardRecord> toCreditCardRecordList(ResultSet rs) throws SQLException {
		List<CreditCardRecord> res = new ArrayList<>();
		while(rs.next())
			res.add(resultSetToCreditCardRecord(rs));
		return res;
	}


	public static Optional<CashRecord> toCashRecord(ResultSet rs) throws SQLException {
		CashRecord record = null;
		if (rs.next())
			record = resultSetToCashRecord(rs);
		return Optional.ofNullable(record);
	}


	private static CashRecord resultSetToCashRecord(ResultSet rs) throws SQLException {
		CashRecord record = new CashRecord();
		
		return record;
	}


	public static Optional<ChargeRecord> toChargeRecord(ResultSet rs) throws SQLException {
		ChargeRecord record = null;
		if (rs.next())
			record = resultSetToChargeRecord(rs);
		return Optional.ofNullable(record);
	}


	private static ChargeRecord resultSetToChargeRecord(ResultSet rs) throws SQLException {
		ChargeRecord record = new ChargeRecord();
		record.id      = rs.getString("id");
		record.version = rs.getLong("version");
	
		record.paymentMean_id = rs.getString("paymentMean_Id");
		record.invoice_id     = rs.getString("invoice_id");
		
		return record;
	}


	public static List<PaymentmeanRecord> toPaymentMeanRecordList(ResultSet rs) throws SQLException {
		List<PaymentmeanRecord> res = new ArrayList<>();
		while (rs.next())
			res.add(resultSetToPaymentMean(rs));
		return res;
	}


	private static PaymentmeanRecord resultSetToPaymentMean(ResultSet rs) throws SQLException {
		PaymentmeanRecord record = new PaymentmeanRecord();
		
		record.id      = rs.getString("id");
		record.version = rs.getLong("version");
		
		record.dtype       = rs.getString("dtype");
		record.client_id   = rs.getString("client_id");
		record.accumulated = rs.getDouble("accumulated");
		
		return record;
	}


	public static Optional<PaymentmeanRecord> toPaymentMeanRecord(ResultSet rs) throws SQLException {
		PaymentmeanRecord record = null;
		if (rs.next()) {
			record = resultSetToPaymentMean(rs);
		}
		return Optional.ofNullable(record);
	}


	public static List<RecommendationRecord> toRecommendationRecordList(ResultSet rs) throws SQLException {
		List<RecommendationRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(resultSetToRecommendationRecord(rs));
		}
		return res;
	}


	private static RecommendationRecord resultSetToRecommendationRecord(ResultSet rs) throws SQLException {
		RecommendationRecord record = new RecommendationRecord();
		record.id      = rs.getString("id");
		record.version = rs.getLong("version");
		
		record.usedForVoucher = rs.getBoolean("usedForVoucher");
		record.sponsor_id     = rs.getString("sponsor_Id");
		record.recommended_id = rs.getString("recommended_Id");
		return record;
	}


	public static List<VehicleRecord> toVehicleRecordList(ResultSet rs) throws SQLException {
		List<VehicleRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(resultSetToVehicleRecord(rs));
		}
		return res;
	}


	private static VehicleRecord resultSetToVehicleRecord(ResultSet rs) throws SQLException {
		VehicleRecord record = new VehicleRecord();
		record.id      = rs.getString("id");
		record.version = rs.getLong("version");
		
		record.client_id      = rs.getString("client_Id");
		record.vehicletype_id = rs.getString("vehicleType_Id");
		
		record.make        = rs.getString("make");
		record.model       = rs.getString("model");
		record.platenumber = rs.getString("plateNumber");
		
		return record;
	}
}
