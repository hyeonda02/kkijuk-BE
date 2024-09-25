package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.ForeignLanguage;

public interface ForeignRepository extends JpaRepository<ForeignLanguage, Long> {
}
