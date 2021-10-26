package uo.ri.cws.application.persistence.invoice;

import java.time.LocalDate;

public class InvoiceRecord {

	public String id;		// the surrogate id (UUID)
	public Long version;
	
	// public double total;	// total amount (money) vat included // changed to the line below
	public double amount;	// total amount (money) vat included
	public double vat;		// amount of vat (money)
	public long number;		// the invoice identity, a sequential number
	public LocalDate date;		// of the invoice
	public String status;	// the status as in FacturaStatus
	public boolean usedforvoucher;   // For loyalty program // deletion asked in erratum


}
