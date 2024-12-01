package devops.infrastructure.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devops.application.usecases.InvoiceUsecase;
import devops.domain.entity.Invoice;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
	private final InvoiceUsecase invoiceUsecase;
	private final InvoiceDTOMapper invoiceDTOMapper;
	
	public InvoiceController(InvoiceUsecase invoiceUsecase, InvoiceDTOMapper invoiceDTOMapper) {
		this.invoiceUsecase = invoiceUsecase;
		this.invoiceDTOMapper = invoiceDTOMapper;
	}
	
	@PostMapping
	ResponseEntity<InvoiceResponse> createInvoice(@RequestBody InvoiceRequest request) {
		Invoice invoiceBusinessObj = invoiceDTOMapper.toInvoice(request);
		Invoice invoice = invoiceUsecase.createInvoice(invoiceBusinessObj);
		return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDTOMapper.toResponse(invoice));
	}
	
    @GetMapping("/{id}")
    public InvoiceResponse findInvoiceById(@PathVariable String id) {
        Invoice invoice = invoiceUsecase.findInvoiceById(id);
        if (invoice == null) {
            throw new InvoiceNotFoundException("Invoice with id " + id + " not found.");
        }
        return invoiceDTOMapper.toResponse(invoice);
    }

    @GetMapping
    public List<InvoiceResponse> getAllInvoices() {
        List<Invoice> invoices = invoiceUsecase.getAllInvoices();
        return invoices.stream()
                       .map(invoiceDTOMapper::toResponse)
                       .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable String id) {
        boolean deleted = invoiceUsecase.deleteInvoice(id);
        if (!deleted) {
            throw new InvoiceNotFoundException("Invoice with id " + id + " not found or could not be deleted.");
        }
        return ResponseEntity.noContent().build();
    }
}
