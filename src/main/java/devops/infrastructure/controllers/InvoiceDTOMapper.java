package devops.infrastructure.controllers;

import devops.domain.entity.Invoice;

public class InvoiceDTOMapper {
	public InvoiceResponse toResponse(Invoice invoice) {
		return new InvoiceResponse(invoice.productName(), invoice.customerNote(), invoice.price());
	}
	
	public Invoice toInvoice(InvoiceRequest request) {
		return new Invoice(request.productName(), request.customerNotes(), request.price());
	}
}
