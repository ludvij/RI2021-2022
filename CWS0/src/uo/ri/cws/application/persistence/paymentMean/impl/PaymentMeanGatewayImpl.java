package uo.ri.cws.application.persistence.paymentmean.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentmeanRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class PaymentMeanGatewayImpl implements PaymentMeanGateway {

	private Conf conf = Conf.getInstance();
	
	@Override
	public void add(PaymentmeanRecord t) {
		PreparedStatement pst = null;
		try {
			pst = Jdbc.getCurrentConnection()
				.prepareStatement(conf.getProperty("TPaymentMeans_add"));
			
			pst.setString(1, t.id);
			pst.setString(2, t.dtype);
			pst.setDouble(3, t.accumulated);
			pst.setString(4, t.client_id);
			
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
				.prepareStatement(conf.getProperty("TPaymentMeans_remove"));
			
			pst.setString(1, id);
			
			pst.executeUpdate();
			
		} catch( SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public void update(PaymentmeanRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<PaymentmeanRecord> findById(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
				.prepareStatement(conf.getProperty("TPaymentMeans_findById"));
			
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toPaymentMeanRecord(rs);
			
		} catch( SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		
	}

	@Override
	public List<PaymentmeanRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> getDType(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
				.prepareStatement(conf.getProperty("TPaymentMeans_getDType"));
			
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			String dtype = null;
			if (rs.next())
				dtype = rs.getString("dtype");
			return Optional.ofNullable(dtype);
		} catch( SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<PaymentmeanRecord> findByClientId(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
				.prepareStatement(conf.getProperty("TPaymentMeans_findByClientId"));
			
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toPaymentMeanRecordList(rs);

		} catch( SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
