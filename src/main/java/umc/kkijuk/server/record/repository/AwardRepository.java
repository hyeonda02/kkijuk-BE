package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.Award;

import java.util.List;


public interface AwardRepository extends JpaRepository<Award, Long> {
    List<Award> findByRecordId(Long recordId);
}
