package uo.ri.cws.application.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
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
		record.number  = rs.getLong("number");
		record.vat     = rs.getDouble("vat");
		record.date    = rs.getDate("date").toLocalDate();//sqlDate.toLocalDate(); 
		record.amount  = rs.getDouble("amount");
		record.status  = rs.getString("status");
		
		
		return record;		
	}
	

}
