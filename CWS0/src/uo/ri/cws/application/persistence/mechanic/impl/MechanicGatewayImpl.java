package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class MechanicGatewayImpl implements MechanicGateway {

	@Override
	public void add(MechanicRecord t) {

		PreparedStatement pst = null;

		try {
			String sql = Conf.getInstance().getProperty("TMechanics_add");
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(sql);
			
			pst.setString(1, t.id);
			pst.setString(2, t.dni);
			pst.setString(3, t.name);
			pst.setString(4, t.surname);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
		
	}

	@Override
	public void remove(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			String sql = Conf.getInstance().getProperty("TMechanics_delete");
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(sql);
			pst.setString(1, id);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs, pst);
		}
		
	}

	@Override
	public void update(MechanicRecord t) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {	
			String sql = Conf.getInstance().getProperty("TMechanics_update");
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(sql);
			
			pst.setString(1, t.name);
			pst.setString(2, t.surname);
			pst.setString(3, t.id);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		
	}

	@Override
	public Optional<MechanicRecord> findById(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = Conf.getInstance().getProperty("TMechanics_findById");
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(sql);
			
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toMechanicRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<MechanicRecord> findAll() {
		List<MechanicRecord> mechanics = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			String sql = Conf.getInstance().getProperty("TMechanics_findAll");
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(sql);
			
			rs = pst.executeQuery();
			
			mechanics = RecordAssembler.toMechanicRecordList(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs, pst);
		}
		
		return mechanics;
	}

	@Override
	public Optional<MechanicRecord> findByDni(String dni) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = Conf.getInstance().getProperty("TMechanics_findByDni");
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(sql);

			pst.setString(1, dni);
			
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toMechanicRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
