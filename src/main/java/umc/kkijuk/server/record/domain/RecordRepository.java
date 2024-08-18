package umc.kkijuk.server.record.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Record findByMemberId(Long memberId);
    boolean existsByMemberId(Long memberId);
}
