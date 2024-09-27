package umc.kkijuk.server.detail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.careerdetail.controller.response.CareerDetailResponse;
import umc.kkijuk.server.detail.controller.response.BaseCareerDetailResponse;
import umc.kkijuk.server.detail.dto.BaseCareerDetailReqDto;
import umc.kkijuk.server.detail.service.BaseCareerDetailService;
import umc.kkijuk.server.login.argumentresolver.Login;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.service.MemberService;

@Tag(name="careerdetail",description = "내커리어 활동 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/basecareer")
public class BaseCareerDetailController {
    private final MemberService memberService;
    private final BaseCareerDetailService careerDetailService;

    @PostMapping("/{careerId}")
    @Operation(summary = "활동 기록 생성", description = "주어진 정보를 바탕으로 활동기록을 생성합니다.")
    @Parameters({
            @Parameter(name = "careerId", description = "활동 Id, path variable 입니다."),
    })
    public CareerDetailResponse<BaseCareerDetailResponse> create(
            @Login LoginInfo loginInfo,
            @PathVariable Long careerId,
            @RequestBody @Valid BaseCareerDetailReqDto request
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerDetailResponse.success(HttpStatus.CREATED, "활동 기록을 성공적으로 생성했습니다.",
                careerDetailService.createDetail(requestMember, request, careerId)
        );
    }
}
