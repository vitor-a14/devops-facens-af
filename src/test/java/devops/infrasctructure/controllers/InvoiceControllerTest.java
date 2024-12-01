package devops.infrasctructure.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import devops.application.usecases.InvoiceUsecase;
import devops.domain.entity.Invoice;
import devops.infrastructure.controllers.InvoiceController;
import devops.infrastructure.controllers.InvoiceDTOMapper;
import devops.infrastructure.controllers.InvoiceNotFoundException;
import devops.infrastructure.controllers.InvoiceRequest;
import devops.infrastructure.controllers.InvoiceResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Collections;

@WebMvcTest(InvoiceController.class) 
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @MockBean
    private InvoiceUsecase invoiceUsecase; 

    @MockBean
    private InvoiceDTOMapper invoiceDTOMapper; 

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateInvoice() throws Exception {
        InvoiceRequest request = new InvoiceRequest("Product A", "Note for product A", 100.0);
        Invoice invoice = new Invoice("Product A", "Note for product A", 100.0);
        InvoiceResponse response = new InvoiceResponse("Product A", "Note for product A", 100.0);

        when(invoiceDTOMapper.toInvoice(request)).thenReturn(invoice);
        when(invoiceUsecase.createInvoice(invoice)).thenReturn(invoice);
        when(invoiceDTOMapper.toResponse(invoice)).thenReturn(response);

        mockMvc.perform(post("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value("Product A"))
                .andExpect(jsonPath("$.customerNotes").value("Note for product A"))
                .andExpect(jsonPath("$.price").value(100.0));

        verify(invoiceDTOMapper).toInvoice(request);
        verify(invoiceUsecase).createInvoice(invoice);
        verify(invoiceDTOMapper).toResponse(invoice);
    }

    @Test
    void testFindInvoiceById() throws Exception {
        String invoiceId = "1";
        Invoice invoice = new Invoice("Product A", "Note for product A", 100.0);
        InvoiceResponse response = new InvoiceResponse("Product A", "Note for product A", 100.0);

        when(invoiceUsecase.findInvoiceById(invoiceId)).thenReturn(invoice);
        when(invoiceDTOMapper.toResponse(invoice)).thenReturn(response);

        mockMvc.perform(get("/api/invoices/{id}", invoiceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Product A"))
                .andExpect(jsonPath("$.customerNotes").value("Note for product A"))
                .andExpect(jsonPath("$.price").value(100.0));

        verify(invoiceUsecase).findInvoiceById(invoiceId);
        verify(invoiceDTOMapper).toResponse(invoice);
    }

    @Test
    void testFindInvoiceByIdNotFound() throws Exception {
        String invoiceId = "2";

        when(invoiceUsecase.findInvoiceById(invoiceId)).thenThrow(InvoiceNotFoundException.class);

        mockMvc.perform(get("/api/invoices/{id}", invoiceId))
                .andExpect(status().isNotFound());

        verify(invoiceUsecase).findInvoiceById(invoiceId);
    }

    @Test
    void testGetAllInvoices() throws Exception {
        Invoice invoice = new Invoice("Product A", "Note for product A", 100.0);
        InvoiceResponse response = new InvoiceResponse("Product A", "Note for product A", 100.0);

        when(invoiceUsecase.getAllInvoices()).thenReturn(Collections.singletonList(invoice));
        when(invoiceDTOMapper.toResponse(invoice)).thenReturn(response);

        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Product A"))
                .andExpect(jsonPath("$[0].customerNotes").value("Note for product A"))
                .andExpect(jsonPath("$[0].price").value(100.0));

        verify(invoiceUsecase).getAllInvoices();
        verify(invoiceDTOMapper).toResponse(invoice);
    }

    @Test
    void testDeleteInvoice() throws Exception {
        String invoiceId = "1";
        when(invoiceUsecase.deleteInvoice(invoiceId)).thenReturn(true);

        mockMvc.perform(delete("/api/invoices/{id}", invoiceId))
                .andExpect(status().isNoContent());

        verify(invoiceUsecase).deleteInvoice(invoiceId);
    }

    @Test
    void testDeleteInvoiceNotFound() throws Exception {
        String invoiceId = "2";
        when(invoiceUsecase.deleteInvoice(invoiceId)).thenReturn(false);

        mockMvc.perform(delete("/api/invoices/{id}", invoiceId))
                .andExpect(status().isNotFound());

        verify(invoiceUsecase).deleteInvoice(invoiceId);
    }
}