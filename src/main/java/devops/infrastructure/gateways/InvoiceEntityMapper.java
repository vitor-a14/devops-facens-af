package devops.infrastructure.gateways;

import devops.domain.entity.Invoice;
import devops.infrastructure.persistence.InvoiceEntity;

public class InvoiceEntityMapper {
	InvoiceEntity toEntity(Invoice invoiceDomain) {
		return new InvoiceEntity(invoiceDomain.productName(), invoiceDomain.customerNote(), invoiceDomain.price());
	}
	
	Invoice toDomain(InvoiceEntity invoiceEntity) {
		return new Invoice(invoiceEntity.getProductName(), invoiceEntity.getCustomerNotes(), invoiceEntity.getPrice());
	}
}
