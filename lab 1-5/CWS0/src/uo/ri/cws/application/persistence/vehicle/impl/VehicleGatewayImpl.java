package uo.ri.cws.application.persistence.vehicle.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleRecord;

public class VehicleGatewayImpl implements VehicleGateway {
	
	private Conf conf = Conf.getInstance();

	@Override
	public void add(VehicleRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(VehicleRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<VehicleRecord> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleRecord> findByClientId(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst  =Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TVehicles_findByClientId"));
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toVehicleRecordList(rs);
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		
	}

}
