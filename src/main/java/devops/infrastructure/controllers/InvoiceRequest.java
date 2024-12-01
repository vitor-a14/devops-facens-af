package devops.infrastructure.controllers;

public record InvoiceRequest(String productName, String customerNotes, double price) {

}
