package uo.ri.cws.application.persistence.recommendation.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.recommendation.RecommendationGateway;
import uo.ri.cws.application.persistence.recommendation.RecommendationRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class RecommendationGatewayImpl implements RecommendationGateway {
	
	private Conf conf = Conf.getInstance();

	@Override
	public void add(RecommendationRecord t) {
		throw new RuntimeException("Not yet implemented");

	}

	@Override
	public void remove(String id) {
		throw new RuntimeException("Not yet implemented");

	}

	@Override
	public void update(RecommendationRecord t) {
		PreparedStatement pst = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TRecommendations_update"));
			
			pst.setBoolean(1, t.usedForVoucher);
			
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public Optional<RecommendationRecord> findById(String id) {
		throw new RuntimeException("Not yet implemented");
		return null;
	}

	@Override
	public List<RecommendationRecord> findAll() {
		throw new RuntimeException("Not yet implemented");
		return null;
	}

	@Override
	public List<RecommendationRecord> findBySponsorId(String id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Jdbc.getCurrentConnection()
					.prepareStatement(conf.getProperty("TRecommendations_findBySponsorId"));
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return RecordAssembler.toRecommendationRecordList(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
