package uo.ri.cws.application.persistence.charge.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.charge.ChargeGateway;
import uo.ri.cws.application.persistence.charge.ChargeRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class ChargeGatewayImpl implements ChargeGateway {
	
	private Conf conf = Conf.getInstance();

	@Override
	public void add(ChargeRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ChargeRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ChargeRecord> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChargeRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ChargeRecord> findByPaymentMeanId(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TCharges_findByPaymentMeanId"));
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toChargeRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
