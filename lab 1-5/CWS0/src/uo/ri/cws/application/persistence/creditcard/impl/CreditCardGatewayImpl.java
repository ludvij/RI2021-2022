package uo.ri.cws.application.persistence.creditcard.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.creditcard.CreditCardGateway;
import uo.ri.cws.application.persistence.creditcard.CreditCardRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class CreditCardGatewayImpl implements CreditCardGateway {
	
	private Conf conf = Conf.getInstance();

	@Override
	public void add(CreditCardRecord t) {
		PreparedStatement pst = null;
		try {
			pst = Jdbc.getCurrentConnection()
				.prepareStatement(conf.getProperty("TCreditCards_add"));
			
			pst.setString(1, t.id);
			pst.setString(2, t.number);
			pst.setString(3, t.type);
			pst.setDate(4,   java.sql.Date.valueOf(t.validthru));
			
			pst.executeUpdate();
			
		} catch( SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public void remove(String id) {
		PreparedStatement pst = null;
		try {
			pst = Jdbc.getCurrentConnection()
				.prepareStatement(conf.getProperty("TCreditCards_remove"));
			
			pst.setString(1, id);
			
			pst.executeUpdate();
			
		} catch( SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public void update(CreditCardRecord t) {
		throw new RuntimeException("Not yet implemented");

	}

	@Override
	public Optional<CreditCardRecord> findById(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
				.prepareStatement(conf.getProperty("TCreditCards_findById"));
			
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toCreditCardRecord(rs);
			
		} catch( SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<CreditCardRecord> findAll() {
		throw new RuntimeException("Not yet implemented");
		return null;
	}

	@Override
	public Optional<CreditCardRecord> findByNumber(String number) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
				.prepareStatement(conf.getProperty("TCreditCards_findByNumber"));
			
			pst.setString(1, number);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toCreditCardRecord(rs);
			
		} catch( SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}
}
