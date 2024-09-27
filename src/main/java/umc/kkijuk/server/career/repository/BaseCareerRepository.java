package umc.kkijuk.server.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.career.domain.BaseCareer;

import java.util.List;

public interface BaseCareerRepository extends JpaRepository<BaseCareer,Long> {
    List<BaseCareer> findByMemberId(Long memberId);
}
