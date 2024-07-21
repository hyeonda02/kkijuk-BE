package umc.kkijuk.server.review.service.port;

import umc.kkijuk.server.review.domain.Review;

import java.util.Optional;

public interface ReviewRepository {
    Review save(Review review);

    Optional<Review> findById(Long id);

    Review getById(Long id);

    void delete(Review review);
}
