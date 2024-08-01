package umc.kkijuk.server.careerdetail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name="careerdetail",description = "내커리어 활동 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/career")
public class CareerDetailController {
    private final CareerDetailService careerDetailService;
    @PostMapping("/{careerId}")
    @Operation(summary = "활동 기록 추가 API", description = "내 커리어 - 활동 기록을 생성하는 API")
    @Parameter(name = "careerId", description = "활동 Id, path Variable 입니다.")
    public CareerDetailResponse<CareerDetailResponseDto.CareerDetailResult> create(@RequestBody @Valid CareerDetailRequestDto.CareerDetailCreate request , @PathVariable Long careerId) {
        CareerDetail newCareerDetail = careerDetailService.create(request, careerId);
        return CareerDetailResponse.success(HttpStatus.CREATED, "활동 기록을 성공적으로 생성했습니다.", CareerDetailConverter.toCareerDetailResult(newCareerDetail));
    }

}
