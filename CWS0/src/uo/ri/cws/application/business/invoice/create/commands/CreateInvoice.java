package uo.ri.cws.application.business.invoice.create.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import alb.util.math.Round;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.invoice.InvoiceDto.InvoiceStatus;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class CreateInvoice implements Command<InvoiceDto>{
	
	
	private WorkOrderGateway wog = PersistenceFactory.forWorkOrder();
	private InvoiceGateway ig = PersistenceFactory.forInvoice();

	
	private List<String> workOrderIds;

	public CreateInvoice(List<String> workOrderIds) {
		if (workOrderIds.stream().anyMatch(x -> x == null || x.isBlank()))
			throw new IllegalArgumentException("invalid id");
		this.workOrderIds = workOrderIds;
	}
	
	public InvoiceDto execute() throws BusinessException 
	{
		InvoiceDto invoice = new InvoiceDto();


		if (! checkWorkOrdersExist(workOrderIds) )
			throw new BusinessException ("Workorder does not exist");
		if (! checkWorkOrdersFinished(workOrderIds) )
			throw new BusinessException ("Workorder is not finished yet");

		invoice.number = generateInvoiceNumber();
		invoice.date   = LocalDate.now();
		double amount  = calculateTotalInvoice(workOrderIds); // vat not included
		invoice.vat    = vatPercentage(amount, invoice.date);
		invoice.total = Round.twoCents(amount* (1 + invoice.vat/100)); // vat included
		invoice.id = UUID.randomUUID().toString();
		invoice.status = String.valueOf(InvoiceStatus.NOT_YET_PAID);

		createInvoice(invoice);
		linkWorkordersToInvoice(invoice.id, workOrderIds);
		markWorkOrdersAsInvoiced(workOrderIds);

		
		return invoice;
	}

	/*
	 * checks whether every work order exist	 
	 */
	private boolean checkWorkOrdersExist(List<String> workOrderIDS) {
		return workOrderIDS.stream()
				.map(wog::findById) // map id to workorder
				.allMatch(Optional::isPresent);
	}
	/*
	 * checks whether every work order id is FINISHED	 
	 */
	private boolean checkWorkOrdersFinished(List<String> workOrderIDS) {
		
		return workOrderIDS.stream()
				.map(x -> wog.findById(x).get())        // map id to workorder
				.map(x -> x.status)                     // map workorder to status
				.allMatch("FINISHED"::equalsIgnoreCase);// check if status is finished
		

	}

	/*
	 * Generates next invoice number (not to be confused with the inner id)
	 */
	private Long generateInvoiceNumber() {
		return ig.getNextInvoiceNumber();
	}

	/*
	 * Compute total amount of the invoice  (as the total of individual work orders' amount 
	 */
	private double calculateTotalInvoice(List<String> workOrderIDS) 
	{
		double totalInvoice = 0.0;
		for (String workOrderID : workOrderIDS) {
			totalInvoice += getWorkOrderTotal(workOrderID);
		}
		return totalInvoice;
	}

	/*
	 * checks whether every work order id is FINISHED	 
	 */
	private Double getWorkOrderTotal(String workOrderID) 
	{
		var record = wog.findById(workOrderID).get();
		return record.amount;
	}

	/*
	 * returns vat percentage 
	 */
	private double vatPercentage(double totalInvoice, LocalDate dateInvoice) 
	{
		return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0 : 18.0;
	}

	/*
	 * Creates the invoice in the database; returns the id
	 */
	private void createInvoice(InvoiceDto invoice) 
	{
		invoice.id = UUID.randomUUID().toString();
		
		InvoiceRecord ir = DtoAssembler.toRecord(invoice);
		ig.add(ir);
	}

	/*
	 * Set the invoice number field in work order table to the invoice number generated
	 */
	private void linkWorkordersToInvoice (String invoiceId, List<String> workOrderIDS) 
	{
		workOrderIDS.forEach(x -> wog.linkToinvoice(invoiceId, x));
	}
	/*
	 * Sets status to INVOICED for every workorder
	 */
	private void markWorkOrdersAsInvoiced(List<String> ids) 
	{
		ids.forEach(wog::markAsInvoiced);
	}

}
