package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.Foreign;

public interface ForeignRepository extends JpaRepository<Foreign, Long> {
}
