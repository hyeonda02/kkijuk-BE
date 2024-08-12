package umc.kkijuk.server.introduce.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IntroduceRepository extends JpaRepository<Introduce, Long> {
    Optional<Introduce> findByRecruitId(Long recruitId);
    Optional<List<Introduce>> findAllByMemberId(Long memberId);

    @Query("SELECT i FROM Introduce i WHERE i.memberId = :memberId ORDER BY i.recruit.endTime ASC")
    Page<Introduce> findTop2ByMemberIdOrderByEndTimeAsc(@Param("memberId") Long memberId, Pageable pageable);
}
