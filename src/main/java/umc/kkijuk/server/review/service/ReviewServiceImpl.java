package umc.kkijuk.server.review.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.common.domian.exception.RecruitOwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ReviewRecruitMismatchException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.review.controller.port.ReviewService;
import umc.kkijuk.server.review.domain.Review;
import umc.kkijuk.server.review.domain.ReviewCreate;
import umc.kkijuk.server.review.domain.ReviewUpdate;
import umc.kkijuk.server.review.service.port.ReviewRepository;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> findAllByRecruitId(Long recruitId) {
        return reviewRepository.findAllByRecruitId(recruitId);
    }

    public Review getById(Long id) {
        return reviewRepository.getById(id);
    }

    @Override
    @Transactional //멤버 + recruit 사이의 인가 예외처리
    public Review create(Member requestMember, Recruit recruit, ReviewCreate reviewCreate) {
        if (!recruit.getMemberId().equals(requestMember.getId())) {
            throw new RecruitOwnerMismatchException();
        }

        Review review = Review.from(recruit, reviewCreate);
        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public Review update(Member requestMember, Recruit recruit, Long reviewId, ReviewUpdate reviewUpdate) {
        if (!recruit.getMemberId().equals(requestMember.getId())) {
            throw new RecruitOwnerMismatchException();
        }

        Review review = getById(reviewId);
        if (!review.getRecruitId().equals(recruit.getId())) {
            throw new ReviewRecruitMismatchException(recruit.getId(), reviewId);
        }

        review = review.update(reviewUpdate);
        return reviewRepository.save(review);
    }

    @Override
    public void delete(Member requestMember, Recruit recruit, Long reviewId) {
        if (!recruit.getMemberId().equals(requestMember.getId())) {
            throw new RecruitOwnerMismatchException();
        }

        Review review = getById(reviewId);
        if (!review.getRecruitId().equals(recruit.getId())) {
            throw new ReviewRecruitMismatchException(recruit.getId(), reviewId);
        }

        reviewRepository.delete(review);
    }
}
