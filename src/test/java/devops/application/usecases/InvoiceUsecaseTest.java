package devops.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import devops.application.gateways.InvoiceGateway;
import devops.domain.entity.Invoice;
import devops.infrastructure.controllers.InvoiceNotFoundException;

class InvoiceUsecaseTest {
    @Mock
    private InvoiceGateway invoiceGateway;

    private InvoiceUsecase invoiceUsecase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        invoiceUsecase = new InvoiceUsecase(invoiceGateway);
    }

    @Test
    void createInvoice_ShouldReturnCreatedInvoice() {
        Invoice invoice = new Invoice("1", "Test Invoice", 100.0);
        when(invoiceGateway.createInvoice(invoice)).thenReturn(invoice);

        Invoice result = invoiceUsecase.createInvoice(invoice);

        assertNotNull(result);
        assertEquals("1", result.productName());
        assertEquals("Test Invoice", result.customerNote());
        assertEquals(100.0, result.price());
        verify(invoiceGateway).createInvoice(invoice);
    }

    @Test
    void findInvoiceById_ShouldReturnInvoice() {
        Invoice invoice = new Invoice("1", "Test Invoice", 100.0);
        when(invoiceGateway.findInvoiceById("1")).thenReturn(invoice);

        Invoice result = invoiceUsecase.findInvoiceById("1");

        assertNotNull(result);
        assertEquals("1", result.productName());
        verify(invoiceGateway).findInvoiceById("1");
    }

    @Test
    void findInvoiceById_ShouldThrowInvoiceNotFoundException_WhenNotFound() {
        when(invoiceGateway.findInvoiceById("4")).thenReturn(null);

        assertThrows(InvoiceNotFoundException.class, () -> invoiceUsecase.findInvoiceById("4"));
    }
    
    @Test
    void getAllInvoices_ShouldReturnListOfInvoices() {
        Invoice invoice1 = new Invoice("Product1", "Note1", 100.0);
        Invoice invoice2 = new Invoice("Product2", "Note2", 200.0);
        List<Invoice> invoiceList = Arrays.asList(invoice1, invoice2);
        
        when(invoiceGateway.getAllInvoices()).thenReturn(invoiceList);

        List<Invoice> result = invoiceUsecase.getAllInvoices();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product1", result.get(0).productName());
        assertEquals("Product2", result.get(1).productName());
    }

    @Test
    void deleteInvoice_ShouldReturnTrue_WhenDeletedSuccessfully() {
        when(invoiceGateway.deleteInvoice("1")).thenReturn(true);

        boolean result = invoiceUsecase.deleteInvoice("1");

        assertTrue(result);
    }

    @Test
    void deleteInvoice_ShouldReturnFalse_WhenNotFound() {
        when(invoiceGateway.deleteInvoice("999")).thenReturn(false);

        boolean result = invoiceUsecase.deleteInvoice("999");

        assertFalse(result);
    }

    @Test
    void findInvoiceById_ShouldThrowInvoiceNotFoundException_WhenInvoiceNotFound() {
        when(invoiceGateway.findInvoiceById("999")).thenReturn(null);

        assertThrows(InvoiceNotFoundException.class, () -> invoiceUsecase.findInvoiceById("999"));
    }
}