package umc.kkijuk.server.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.career.domain.Employment;

public interface EmploymentRepository extends JpaRepository<Employment,Long> {
}
