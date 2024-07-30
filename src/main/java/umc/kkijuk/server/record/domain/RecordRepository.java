package umc.kkijuk.server.record.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.introduce.domain.Introduce;

public interface RecordRepository extends JpaRepository<Introduce, Long> {
}
