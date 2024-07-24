package umc.kkijuk.server.review.controller.port;

import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.review.domain.Review;
import umc.kkijuk.server.review.domain.ReviewCreate;
import umc.kkijuk.server.review.domain.ReviewUpdate;

import java.util.List;

public interface ReviewService {
    Review create(Recruit recruit, ReviewCreate reviewCreate);

    Review update(Recruit recruit, Long reviewId, ReviewUpdate reviewUpdate);

    List<Review> findAllByRecruitId(Long recruitId);

    Review getById(Long reviewId);

    void delete(Recruit recruit, Long reviewId);
}