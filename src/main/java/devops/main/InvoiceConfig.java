package devops.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import devops.application.gateways.InvoiceGateway;
import devops.application.usecases.InvoiceUsecase;
import devops.infrastructure.controllers.InvoiceDTOMapper;
import devops.infrastructure.gateways.InvoiceEntityMapper;
import devops.infrastructure.gateways.InvoiceRepositoryGateway;
import devops.infrastructure.persistence.InvoiceRepository;

@Configuration
public class InvoiceConfig {
	@Bean
	InvoiceUsecase invoiceUsecase(InvoiceGateway invoiceGateway) {
		return new InvoiceUsecase(invoiceGateway);
	}
	
	@Bean
	InvoiceGateway invoiceGateway(InvoiceRepository invoiceRepository, InvoiceEntityMapper invoiceEntityMapper) {
		return new InvoiceRepositoryGateway(invoiceRepository, invoiceEntityMapper);
	}
	
	@Bean
	InvoiceEntityMapper invoiceEntityMapper() {
		return new InvoiceEntityMapper();
	}
	
	@Bean
	InvoiceDTOMapper invoiceDTOMapper() {
		return new InvoiceDTOMapper();
	}
}
