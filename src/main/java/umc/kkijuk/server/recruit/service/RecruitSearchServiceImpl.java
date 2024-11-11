package umc.kkijuk.server.recruit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.controller.port.RecruitSearchService;
import umc.kkijuk.server.recruit.controller.response.RecruitReviewListByKeywordResponse;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.review.domain.RecruitReviewDto;
import umc.kkijuk.server.recruit.service.port.RecruitRepository;
import umc.kkijuk.server.review.service.port.ReviewRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitSearchServiceImpl implements RecruitSearchService {
    private final RecruitRepository recruitRepository;
    private final ReviewRepository reviewRepository;

    public RecruitReviewListByKeywordResponse findRecruitByKeyword(Member requestMember, String keyword) {
        List<Recruit> recruits = recruitRepository.searchRecruitByKeyword(requestMember.getId(), keyword);
        List<RecruitReviewDto> reviews = reviewRepository.findReviewByKeyword(requestMember.getId(), keyword);

        for (RecruitReviewDto review : reviews) {
            log.info("keyword: {}, result: {}", keyword, review.getReviewContent());
        }

        return RecruitReviewListByKeywordResponse.from(keyword, recruits, reviews);
    }
}
