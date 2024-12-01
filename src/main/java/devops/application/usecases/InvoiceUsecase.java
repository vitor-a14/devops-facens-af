package devops.application.usecases;

import java.util.List;

import devops.application.gateways.InvoiceGateway;
import devops.domain.entity.Invoice;

public class InvoiceUsecase {
	private InvoiceGateway invoiceGateway;
	
	public InvoiceUsecase(InvoiceGateway invoiceGateway) {
		this.invoiceGateway = invoiceGateway;
	}
	
	public Invoice createInvoice(Invoice invoice) {
		return invoiceGateway.createInvoice(invoice);
	}
	
    public Invoice findInvoiceById(String id) {
        return invoiceGateway.findInvoiceById(id);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceGateway.getAllInvoices();
    }

    public boolean deleteInvoice(String id) {
        return invoiceGateway.deleteInvoice(id);
    }
}
