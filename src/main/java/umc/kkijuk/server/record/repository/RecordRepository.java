package umc.kkijuk.server.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.record.domain.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Record findByMemberId(Long memberId);
    boolean existsByMemberId(Long memberId);

}
