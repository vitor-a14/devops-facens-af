package devops.application.gateways;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import devops.domain.entity.Invoice;
import devops.infrastructure.controllers.InvoiceNotFoundException;
import devops.infrastructure.gateways.InvoiceEntityMapper;
import devops.infrastructure.gateways.InvoiceRepositoryGateway;
import devops.infrastructure.persistence.InvoiceEntity;
import devops.infrastructure.persistence.InvoiceRepository;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvoiceRepositoryGatewayTest {
    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private InvoiceEntityMapper invoiceEntityMapper;

    private InvoiceRepositoryGateway invoiceRepositoryGateway;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        invoiceRepositoryGateway = new InvoiceRepositoryGateway(invoiceRepository, invoiceEntityMapper);
    }

    @Test
    void createInvoice_ShouldReturnCreatedInvoice() {
        Invoice invoice = new Invoice("1", "Test Invoice", 100.0);
        InvoiceEntity invoiceEntity = new InvoiceEntity("Test Product", "Customer Note", 100.0);
        when(invoiceEntityMapper.toEntity(invoice)).thenReturn(invoiceEntity);
        when(invoiceRepository.save(invoiceEntity)).thenReturn(invoiceEntity);
        when(invoiceEntityMapper.toDomain(invoiceEntity)).thenReturn(invoice);

        Invoice result = invoiceRepositoryGateway.createInvoice(invoice);

        assertNotNull(result);
        assertEquals("1", result.productName());
        assertEquals("Test Invoice", result.customerNote());
        verify(invoiceRepository).save(invoiceEntity);
    }

    @Test
    void findInvoiceById_ShouldReturnInvoice() {
        InvoiceEntity invoiceEntity = new InvoiceEntity("Test Product", "Customer Note", 100.0);
        Invoice invoice = new Invoice("1", "Test Invoice", 100.0);
        when(invoiceRepository.findById(1L)).thenReturn(java.util.Optional.of(invoiceEntity));
        when(invoiceEntityMapper.toDomain(invoiceEntity)).thenReturn(invoice);

        Invoice result = invoiceRepositoryGateway.findInvoiceById("1");

        assertNotNull(result);
        assertEquals("1", result.productName());
        verify(invoiceRepository).findById(1L);
    }

    @Test
    void findInvoiceById_ShouldThrowInvoiceNotFoundException_WhenNotFound() {
        when(invoiceRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(InvoiceNotFoundException.class, () -> invoiceRepositoryGateway.findInvoiceById("1"));
    }
    
    @Test
    void getAllInvoices_ShouldReturnInvoices() {
        InvoiceEntity invoiceEntity1 = new InvoiceEntity("Product1", "Note1", 100.0);
        InvoiceEntity invoiceEntity2 = new InvoiceEntity("Product2", "Note2", 150.0);

        when(invoiceRepository.findAll()).thenReturn(Arrays.asList(invoiceEntity1, invoiceEntity2));
        when(invoiceEntityMapper.toDomain(invoiceEntity1)).thenReturn(new Invoice("Product1", "Note1", 100.0));
        when(invoiceEntityMapper.toDomain(invoiceEntity2)).thenReturn(new Invoice("Product2", "Note2", 150.0));

        List<Invoice> invoices = invoiceRepositoryGateway.getAllInvoices();

        assertEquals(2, invoices.size());
        assertEquals("Product1", invoices.get(0).productName());
        assertEquals("Product2", invoices.get(1).productName());
    }

    @Test
    void deleteInvoice_ShouldReturnTrue_WhenInvoiceExists() {
        String invoiceId = "1";
        when(invoiceRepository.existsById(1L)).thenReturn(true);

        boolean result = invoiceRepositoryGateway.deleteInvoice(invoiceId);

        assertTrue(result);
        verify(invoiceRepository).deleteById(1L); 
    }

    @Test
    void deleteInvoice_ShouldReturnFalse_WhenInvoiceDoesNotExist() {
        String invoiceId = "1";
        when(invoiceRepository.existsById(1L)).thenReturn(false);

        boolean result = invoiceRepositoryGateway.deleteInvoice(invoiceId);

        assertFalse(result);
        verify(invoiceRepository, never()).deleteById(1L); 
    }

    @Test
    void deleteInvoice_ShouldReturnFalse_WhenInvalidIdFormat() {
        String invalidInvoiceId = "invalid_id";

        boolean result = invoiceRepositoryGateway.deleteInvoice(invalidInvoiceId);

        assertFalse(result);
        verify(invoiceRepository, never()).deleteById(anyLong()); 
    }
}