package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

	private Conf conf = Conf.getInstance();
	
	@Override
	public void add(WorkOrderRecord t) {
		throw new RuntimeException("Not yet implemented");
		
	}

	@Override
	public void remove(String id) {
		throw new RuntimeException("Not yet implemented");
		
	}

	@Override
	public void update(WorkOrderRecord t) {
		throw new RuntimeException("Not yet implemented");
		
	}

	@Override
	public Optional<WorkOrderRecord> findById(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TWorkOrders_findById"));

			pst.setString(1, id);
			
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toWorkOrderRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<WorkOrderRecord> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TWorkOrders_findAll"));			
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toWorkOrderRecordList(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public Optional<WorkOrderRecord> findByMechanicId(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TWorkOrders_findByMechanicId"));

			pst.setString(1, id);
			
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toWorkOrderRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void markAsInvoiced(String id) {
		PreparedStatement pst = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TWorkOrders_markAsInvoiced"));

			pst.setString(1, id);

			pst.executeUpdate();
			
		} catch (SQLException e){
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public void linkToinvoice(String invoiceId, String workOrderId) {
		PreparedStatement pst = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TWorkOrders_linkToInvoice"));

			pst.setString(1, invoiceId);
			pst.setString(2, workOrderId);

			pst.executeUpdate();
		} catch (SQLException e){
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}
		
	}

	@Override
	public List<WorkOrderRecord> findByVehicleId(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TWorkOrders_findByVehicleId"));

			pst.setString(1, id);
			
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toWorkOrderRecordList(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}
		

}
