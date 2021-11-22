package uo.ri.cws.application.persistence.client.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class ClientGatewayImpl implements ClientGateway {
	
	private Conf conf = Conf.getInstance();

	@Override
	public void add(ClientRecord t) {
		throw new RuntimeException("Not yet implemented");

	}

	@Override
	public void remove(String id) {
		throw new RuntimeException("Not yet implemented");

	}

	@Override
	public void update(ClientRecord t) {
		throw new RuntimeException("Not yet implemented");

	}

	@Override
	public Optional<ClientRecord> findById(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {

			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TClients_findById"));
			
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toClientRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<ClientRecord> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {

			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TClients_findAll"));
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toClientRecordList(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public Optional<ClientRecord> findByDni(String dni) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {

			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TClients_findByDni"));
			
			pst.setString(1, dni);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toClientRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
