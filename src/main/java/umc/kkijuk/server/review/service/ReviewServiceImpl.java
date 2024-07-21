package umc.kkijuk.server.review.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.review.controller.port.ReviewService;
import umc.kkijuk.server.review.domain.Review;
import umc.kkijuk.server.review.domain.ReviewCreate;
import umc.kkijuk.server.review.domain.ReviewUpdate;
import umc.kkijuk.server.review.service.port.ReviewRepository;

@Service
@Builder
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public Review getById(Long id) {
        return reviewRepository.getById(id);
    }

    @Override
    @Transactional //멤버 + recruit 사이의 인가 예외처리
    public Review create(Recruit recruit, ReviewCreate reviewCreate) {
        Review review = Review.from(recruit, reviewCreate);
        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public Review update(Recruit recruit, Long reviewId, ReviewUpdate reviewUpdate) {
        Review review = getById(reviewId);
        if (!review.getId().equals(recruit.getId())) {
            // 잘못된 요청 처리 나중에 작성
        }

        review = review.update(reviewUpdate);
        return reviewRepository.save(review);
    }
}
