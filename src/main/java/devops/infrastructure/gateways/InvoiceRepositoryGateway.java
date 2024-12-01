package devops.infrastructure.gateways;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import devops.application.gateways.InvoiceGateway;
import devops.domain.entity.Invoice;
import devops.infrastructure.persistence.InvoiceEntity;
import devops.infrastructure.persistence.InvoiceRepository;

public class InvoiceRepositoryGateway implements InvoiceGateway {
	private final InvoiceRepository invoiceRepository;
	private final InvoiceEntityMapper invoiceEntityMapper;
	
	public InvoiceRepositoryGateway(InvoiceRepository invoiceRepository, InvoiceEntityMapper invoiceEntityMapper) {
		this.invoiceRepository = invoiceRepository;
		this.invoiceEntityMapper = invoiceEntityMapper;
	}

	@Override
	public Invoice createInvoice(Invoice invoiceDomain) {
		InvoiceEntity invoiceEntity = invoiceEntityMapper.toEntity(invoiceDomain);
		InvoiceEntity savedObj = invoiceRepository.save(invoiceEntity);
		return invoiceEntityMapper.toDomain(savedObj);
	}
	
    @Override
    public Invoice findInvoiceById(String id) {
        try {
            Long invoiceId = Long.parseLong(id);
            Optional<InvoiceEntity> invoiceEntity = invoiceRepository.findById(invoiceId);
            return invoiceEntity.map(invoiceEntityMapper::toDomain).orElse(null);
        } catch (NumberFormatException e) {
            return null; 
        }
    }
    
    @Override
    public List<Invoice> getAllInvoices() {
        List<InvoiceEntity> invoiceEntities = invoiceRepository.findAll();
        return invoiceEntities.stream()
                .map(invoiceEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteInvoice(String id) {
        try {
            Long invoiceId = Long.parseLong(id);
            if (invoiceRepository.existsById(invoiceId)) {
                invoiceRepository.deleteById(invoiceId);
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false; 
        }
    }
}
