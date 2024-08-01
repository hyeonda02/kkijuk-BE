package umc.kkijuk.server.careerdetail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.careerdetail.controller.response.CareerDetailResponse;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.careerdetail.dto.CareerDetailRequestDto;
import umc.kkijuk.server.careerdetail.dto.CareerDetailResponseDto;
import umc.kkijuk.server.careerdetail.dto.converter.CareerDetailConverter;
import umc.kkijuk.server.careerdetail.service.CareerDetailService;
import umc.kkijuk.server.common.LoginUser;
import umc.kkijuk.server.member.domain.Member;

@Tag(name="careerdetail",description = "내커리어 활동 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/career")
public class CareerDetailController {
    private final CareerDetailService careerDetailService;

    private final Member requestMember = Member.builder()
            .id(LoginUser.get().getId())
            .build();

    @PostMapping("/{careerId}")
    @Operation(summary = "활동 기록 추가 API", description = "내 커리어 - 활동 기록을 생성하는 API")
    @Parameter(name = "careerId", description = "활동 Id, path Variable 입니다.")
    public CareerDetailResponse<CareerDetailResponseDto.CareerDetailResult> create(@RequestBody @Valid CareerDetailRequestDto.CareerDetailCreate request , @PathVariable Long careerId) {
        CareerDetail newCareerDetail = careerDetailService.create(requestMember, request, careerId);
        return CareerDetailResponse.success(HttpStatus.CREATED, "활동 기록을 성공적으로 생성했습니다.", CareerDetailConverter.toCareerDetailResult(newCareerDetail));
    }

    @DeleteMapping("/{careerId}/{detailId}")
    @Operation(summary = "활동 기록 삭제 API", description = "내 커리어 - 활동 기록을 삭제하는 API")
    @Parameters({
            @Parameter(name = "careerId", description = "활동 Id, path variable 입니다."),
            @Parameter(name = "detailId", description = "활동 기록 Id, path variable 입니다.")
    })
    public CareerDetailResponse<Object> delete(@PathVariable Long careerId, @PathVariable Long detailId) {
        careerDetailService.delete(requestMember, detailId);
        return CareerDetailResponse.success(HttpStatus.OK, "활동 기록을 성공적으로 삭제했습니다.",null);
    }

}
