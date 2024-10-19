package umc.kkijuk.server.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.career.domain.EduCareer;

import java.util.List;

public interface EduCareerRepository extends JpaRepository<EduCareer,Long> {
    List<EduCareer> findByMemberId(Long memberId);
}
