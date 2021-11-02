package uo.ri.cws.application.persistence.cash.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.cash.CashGateway;
import uo.ri.cws.application.persistence.cash.CashRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class CashGatewayImpl implements CashGateway {
	
	private Conf conf = Conf.getInstance();

	@Override
	public void add(CashRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) {

	}

	@Override
	public void update(CashRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<CashRecord> findById(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
				.prepareStatement(conf.getProperty("TCashes_findById"));
			
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toCashRecord(rs);
			
		} catch( SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<CashRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
