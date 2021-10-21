package uo.ri.cws.application.business;

import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.business.mechanic.MechanicCrudService;
import uo.ri.cws.application.business.mechanic.crud.MechanicCrudServiceImpl;

public class BusinessFactory {



	public static MechanicCrudService forMechanicCrudService() {
		return new MechanicCrudServiceImpl();
	}

	public static InvoicingService forInvoicingService() {
		return new InvoicingServiceImpl();
	}
	
//	public static PaymentmeanCrudService forPaymentMeanCrudService() {
//		return new PaymentmeanCrudServiceImpl();
//	}
	
//	public static VoucherService forVoucherService() {
//		return new VoucherServiceImpl();
//	}

//	public static ClientCrudService forClientCrudService() {
//		return new ClientCrudServiceImpl();
//	}

}

