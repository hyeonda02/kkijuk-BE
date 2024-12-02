package umc.kkijuk.server.introduce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import umc.kkijuk.server.introduce.domain.Introduce;

import java.util.List;
import java.util.Optional;

public interface IntroduceRepository extends JpaRepository<Introduce, Long> {
    Optional<Introduce> findByRecruitId(Long recruitId);
    Optional<List<Introduce>> findAllByMemberId(Long memberId);

    @Query("SELECT i FROM Introduce i WHERE i.memberId = :memberId AND i.state = :state ORDER BY i.recruit.endTime ASC")
    Page<Introduce> findByMemberIdAndStateOrderByEndTimeAsc(@Param("memberId") Long memberId, @Param("state") int state, Pageable pageable);

    @Query("SELECT i FROM Introduce i " +
            "JOIN i.questions q " +
            "WHERE q.content LIKE %:keyword%")
    List<Introduce> searchIntroduceByKeyword(String keyword);


}
