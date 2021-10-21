package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class InvoiceGatewayImpl implements InvoiceGateway {
	
	private Conf conf = Conf.getInstance();

	@Override
	public void add(InvoiceRecord t) {
		PreparedStatement pst = null;

		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("Tnvoices_add"));
			
			pst.setString(1, t.id);
			pst.setLong(2,   t.number);
			pst.setDate(3,   java.sql.Date.valueOf(t.date));
			pst.setDouble(4, t.vat);
			pst.setDouble(5, t.amount);
			pst.setString(6, t.status.toString());

			pst.executeUpdate();
			
		} catch (SQLException e){
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
		
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(InvoiceRecord t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<InvoiceRecord> findById(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("Invoice_findByDni"));
			
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toInvoiceRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<InvoiceRecord> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("Invoice_findAll"));
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toInvoiceRecordList(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public Optional<InvoiceRecord> findByNumber(Long number) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("Invoice_findByNumber"));
			
			pst.setLong(1, number);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toInvoiceRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public Long getNextInvoiceNumber() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TInvoices_getNextInvoiceNumber"));
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, next
			} else { // there is none yet
				return 1L;
			}
		} catch (SQLException e){
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
