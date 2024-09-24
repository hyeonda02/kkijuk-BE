package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
