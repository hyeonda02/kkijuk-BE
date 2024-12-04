package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.Award;
import umc.kkijuk.server.record.domain.Education;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
