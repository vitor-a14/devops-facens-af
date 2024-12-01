package devops.infrastructure.gateways;

import devops.domain.entity.Invoice;
import devops.infrastructure.persistence.InvoiceEntity;

public class InvoiceEntityMapper {
	public InvoiceEntity toEntity(Invoice invoiceDomain) {
		return new InvoiceEntity(invoiceDomain.productName(), invoiceDomain.customerNote(), invoiceDomain.price());
	}
	
	public Invoice toDomain(InvoiceEntity invoiceEntity) {
		return new Invoice(invoiceEntity.getProductName(), invoiceEntity.getCustomerNotes(), invoiceEntity.getPrice());
	}
}
