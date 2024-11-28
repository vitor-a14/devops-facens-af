package devops.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import devops.domain.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	
}