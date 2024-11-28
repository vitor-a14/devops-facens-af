package devops.application;

import java.util.List;

import org.springframework.stereotype.Service;

import devops.adapter.InvoiceRepository;
import devops.domain.Invoice;

@Service
public class InvoiceService {
	private final InvoiceRepository repository;
	
	public InvoiceService(InvoiceRepository repository) {
		this.repository = repository;
	}
	
	public Invoice createInvoice(String productName, String customerNotes, double price) {
		Invoice invoice = new Invoice(productName, customerNotes, price);
		
		return repository.save(invoice);
	}
	
	public List<Invoice> getAllInvoices() {
		return repository.findAll();
	}
	
	public void deleteInvoice(Long id) {
		repository.deleteById(id);
	}
}
