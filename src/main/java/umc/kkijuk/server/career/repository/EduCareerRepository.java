package umc.kkijuk.server.career.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.kkijuk.server.career.domain.EduCareer;

public interface EduCareerRepository extends JpaRepository<EduCareer,Long> {
}
