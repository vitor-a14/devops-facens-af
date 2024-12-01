package devops.application.gateways;

import java.util.List;

import devops.domain.entity.Invoice;

public interface InvoiceGateway {
	Invoice createInvoice(Invoice invoice);
    Invoice findInvoiceById(String id);
    List<Invoice> getAllInvoices();
    boolean deleteInvoice(String id);
}
