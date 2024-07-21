package umc.kkijuk.server.review.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByRecruitId(Long id);
}
