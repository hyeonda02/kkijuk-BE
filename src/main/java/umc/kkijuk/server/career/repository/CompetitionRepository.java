package umc.kkijuk.server.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.career.domain.Competition;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
