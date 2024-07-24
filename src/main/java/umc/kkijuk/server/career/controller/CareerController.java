package umc.kkijuk.server.career.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.career.controller.response.CareerGroupedByResponse;
import umc.kkijuk.server.career.controller.response.CareerResponse;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.CareerResponseDto;
import umc.kkijuk.server.career.dto.converter.CareerConverter;
import umc.kkijuk.server.career.service.CareerService;
import umc.kkijuk.server.common.LoginUser;

import java.util.List;

@Tag(name="career",description = "내커리어 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/career")
public class CareerController {

    private final CareerService careerService;

    @PostMapping("")
    @Operation(summary = "활동 추가 API", description = "내 커리어 - 활동을 추가하는 API")
    public CareerResponse<CareerResponseDto.CareerResultDto> create(@RequestBody @Valid CareerRequestDto.CreateCareerDto request){
        LoginUser loginUser = LoginUser.get();
        Career career = careerService.createCareer(request);
        return CareerResponse.success(HttpStatus.CREATED,
                CareerResponseMessage.CAREER_CREATE_SUCCESS,
                CareerConverter.toCareerResultDto(career));
    }

    @DeleteMapping("/{careerId}")
    @Operation(summary = "활동 삭제 API", description = "내 커리어 - 활동을 삭제하는 API")
    @Parameter(name="careerId", description = "활동 Id, path variable 입니다.",example = "1")
    public CareerResponse<Object> delete(@PathVariable Long careerId){
        LoginUser loginUser = LoginUser.get();
        careerService.deleteCareer(careerId);
        return CareerResponse.success(HttpStatus.OK,
                CareerResponseMessage.CAREER_DELETE_SUCCESS,null);
    }

    @PatchMapping("/{careerId}")
    @Operation(summary = "활동 수정 API", description = "내 커리어 - 활동을 수정하는 API")
    @Parameter(name="careerId", description = "활동 Id, path variable 입니다.",example = "1")
    public CareerResponse<Object> update(@RequestBody @Valid CareerRequestDto.UpdateCareerDto request,
                                         @PathVariable Long careerId) {
        LoginUser loginUser = LoginUser.get();
        Career updateCareer = careerService.updateCareer(careerId, request);
        return CareerResponse.success(HttpStatus.OK,
                CareerResponseMessage.CAREER_UPDATE_SUCCESS,
                CareerConverter.toCareerDto(updateCareer));
    }

    @GetMapping("")
    @Operation(
            summary = "활동 조회 API - category(카테고리 기준) , year(연도 기준) ",
            description = "내 커리어 - 활동을 카테고리 별로 조회하는 API입니다. query 값으로  category 나 year 값을 주세요. " )
    public CareerResponse<List<? extends CareerGroupedByResponse>> read(@RequestParam(name="status") String value){
        return CareerResponse.success(HttpStatus.OK,
                CareerResponseMessage.CAREER_FINDALL_SUCCESS,
                careerService.getCareerGroupedBy(value));
    }



}









