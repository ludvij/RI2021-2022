package uo.ri.cws.application.persistence.voucher.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;
import uo.ri.cws.application.persistence.voucher.VoucherRecord;

public class VoucherGatewayImpl implements VoucherGateway {
	
	private Conf conf = Conf.getInstance();

	@Override
	public void add(VoucherRecord t) {
		PreparedStatement pst = null;
		try {
			pst = Jdbc.getCurrentConnection()
				.prepareStatement(conf.getProperty("TVouchers_add"));
			
			pst.setString(1, t.id);
			pst.setDouble(2, t.available);
			pst.setString(3, t.code);
			pst.setString(4, t.description);
			
			pst.executeUpdate();
			
		} catch( SQLException e) {
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
	public void update(VoucherRecord t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<VoucherRecord> findById(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
				.prepareStatement(conf.getProperty("TVouchers_findById"));
			
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toVoucherRecord(rs);
			
		} catch( SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<VoucherRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<VoucherRecord> findByCode(String code) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TVouchers_findByCode"));
			
			pst.setString(1, code);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toVoucherRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
