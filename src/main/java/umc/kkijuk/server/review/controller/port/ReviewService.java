package umc.kkijuk.server.review.controller.port;

import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.review.domain.Review;
import umc.kkijuk.server.review.domain.ReviewCreate;

public interface ReviewService {
    Review create(Recruit recruit, ReviewCreate reviewCreate);
}
