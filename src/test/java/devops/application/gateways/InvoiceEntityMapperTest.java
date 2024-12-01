package devops.application.gateways;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import devops.domain.entity.Invoice;
import devops.infrastructure.gateways.InvoiceEntityMapper;
import devops.infrastructure.persistence.InvoiceEntity;

public class InvoiceEntityMapperTest {
    private InvoiceEntityMapper invoiceEntityMapper;

    @BeforeEach
    void setup() {
        invoiceEntityMapper = new InvoiceEntityMapper();
    }

    @Test
    void toEntity_ShouldConvertInvoiceToInvoiceEntity() {
        Invoice invoiceDomain = new Invoice("Product1", "Note1", 100.0);

        InvoiceEntity invoiceEntity = invoiceEntityMapper.toEntity(invoiceDomain);

        assertNotNull(invoiceEntity);
        assertEquals("Product1", invoiceEntity.getProductName());
        assertEquals("Note1", invoiceEntity.getCustomerNotes());
        assertEquals(100.0, invoiceEntity.getPrice());
    }

    @Test
    void toDomain_ShouldConvertInvoiceEntityToInvoice() {
        InvoiceEntity invoiceEntity = new InvoiceEntity("Product1", "Note1", 100.0);

        Invoice invoiceDomain = invoiceEntityMapper.toDomain(invoiceEntity);

        assertNotNull(invoiceDomain);
        assertEquals("Product1", invoiceDomain.productName());
        assertEquals("Note1", invoiceDomain.customerNote());
        assertEquals(100.0, invoiceDomain.price());
    }
}
