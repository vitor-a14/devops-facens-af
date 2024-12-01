package devops.infrasctructure.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import devops.domain.entity.Invoice;
import devops.infrastructure.controllers.InvoiceDTOMapper;
import devops.infrastructure.controllers.InvoiceRequest;
import devops.infrastructure.controllers.InvoiceResponse;

class InvoiceDTOMapperTest {
    private InvoiceDTOMapper invoiceDTOMapper;

    @BeforeEach
    void setUp() {
        invoiceDTOMapper = new InvoiceDTOMapper();
    }

    @Test
    void testToResponse() {
        Invoice invoice = new Invoice("Product A", "Customer note", 100.0);

        InvoiceResponse response = invoiceDTOMapper.toResponse(invoice);

        assertEquals(invoice.productName(), response.productName());
        assertEquals(invoice.customerNote(), response.customerNotes());
        assertEquals(invoice.price(), response.price());
    }

    @Test
    void testToInvoice() {
        InvoiceRequest request = new InvoiceRequest("Product B", "Customer note", 200.0);

        Invoice invoice = invoiceDTOMapper.toInvoice(request);

        assertEquals(request.productName(), invoice.productName());
        assertEquals(request.customerNotes(), invoice.customerNote());
        assertEquals(request.price(), invoice.price());
    }
}