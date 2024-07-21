package umc.kkijuk.server.review.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
}
