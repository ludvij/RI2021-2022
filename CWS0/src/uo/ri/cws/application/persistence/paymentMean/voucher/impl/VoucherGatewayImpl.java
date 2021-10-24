package uo.ri.cws.application.persistence.paymentMean.voucher.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.paymentMean.voucher.VoucherGateway;
import uo.ri.cws.application.persistence.paymentMean.voucher.VoucherRecord;
import uo.ri.cws.application.persistence.paymentMean.voucher.VoucherSummaryRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class VoucherGatewayImpl implements VoucherGateway {

	@Override
	public void add(VoucherRecord t) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VoucherRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int generateVouchers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<VoucherRecord> findVouchersByClientId(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = Conf.getInstance().getProperty("TVouchers_findByClientId");
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(sql);
			
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toVoucherRecordList(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<VoucherSummaryRecord> getVoucherSummary() {
		// TODO Auto-generated method stub
		return null;
	}

}
