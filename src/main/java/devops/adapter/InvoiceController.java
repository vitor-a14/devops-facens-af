package devops.adapter;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devops.application.InvoiceService;
import devops.domain.Invoice;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
	private final InvoiceService service;
	
	public InvoiceController(InvoiceService service) {
		this.service = service;
	}
	
	@PostMapping
	public Invoice createInvoice(@RequestParam String productName, @RequestParam String customerNotes, @RequestParam double price) {
		return service.createInvoice(productName, customerNotes, price);
	}
	
	@GetMapping
	public List<Invoice> getAllInvoices() {
		return service.getAllInvoices();
	}
	
	@DeleteMapping("/{id}")
	public void deleteInvoice(@PathVariable Long id) {
		service.deleteInvoice(id);
	}
}
