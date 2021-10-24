package uo.ri.cws.application.persistence.paymentMean.voucher;

import java.util.List;


import uo.ri.cws.application.persistence.Gateway;

public interface VoucherGateway extends Gateway<VoucherRecord> {
	
	int generateVouchers();
	
	List<VoucherRecord> findVouchersByClientId(String id);
	
	
	List<VoucherSummaryRecord> getVoucherSummary();

}
