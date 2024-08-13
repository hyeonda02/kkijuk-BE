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
import umc.kkijuk.server.login.argumentresolver.Login;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.service.MemberService;

import java.util.List;

@Tag(name="career",description = "내커리어 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/career")
public class CareerController {

    private final CareerService careerService;
    private final MemberService memberService;


    @PostMapping("")
    @Operation(summary = "활동 생성", description = "주어진 정보를 바탕으로 활동을 추가합니다.")
    public CareerResponse<CareerResponseDto.CareerResultDto> create(
            @Login LoginInfo loginInfo,
            @RequestBody @Valid CareerRequestDto.CreateCareerDto request
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Career career = careerService.createCareer(requestMember, request);

        return CareerResponse.success(HttpStatus.CREATED,
                CareerResponseMessage.CAREER_CREATE_SUCCESS,
                CareerConverter.toCareerResultDto(career));
    }

    @GetMapping("/{careerId}")
    @Operation(summary = "활동 상세", description = "활동 ID에 해당하는 활동의 세부 내용과, 활동 기록을 조회합니다.")
    @Parameter(name = "careerId", description = "활동 Id, path variable 입니다.", example = "1")
    public CareerResponse<CareerResponseDto.CareerDetailDto> findCareer(
            @Login LoginInfo loginInfo,
            @PathVariable Long careerId
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Career careerDetail = careerService.findCareerDetail(requestMember, careerId);

        return CareerResponse.success(HttpStatus.OK,
                CareerResponseMessage.CAREER_FINDALL_SUCCESS,
                CareerConverter.toCareerDetailDto(careerDetail));
    }

    @DeleteMapping("/{careerId}")
    @Operation(summary = "활동 삭제", description = "활동 ID에 해당하는 활동을 삭제합니다.")
    @Parameter(name="careerId", description = "활동 Id, path variable 입니다.",example = "1")
    public CareerResponse<Object> delete(
            @Login LoginInfo loginInfo,
            @PathVariable Long careerId
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        careerService.deleteCareer(requestMember, careerId);
        return CareerResponse.success(HttpStatus.OK,
                CareerResponseMessage.CAREER_DELETE_SUCCESS, null);
    }

    @PatchMapping("/{careerId}")
    @Operation(summary = "활동 수정", description = "주어진 정보를 바탕으로 활동 데이터를 수정합니다.")
    @Parameter(name="careerId", description = "활동 Id, path variable 입니다.",example = "1")
    public CareerResponse<Object> update(
            @Login LoginInfo loginInfo,
            @RequestBody @Valid CareerRequestDto.UpdateCareerDto request,
            @PathVariable Long careerId
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Career updateCareer = careerService.updateCareer(requestMember,careerId, request);

        return CareerResponse.success(HttpStatus.OK,
                CareerResponseMessage.CAREER_UPDATE_SUCCESS,
                CareerConverter.toCareerDto(updateCareer));
    }

    @GetMapping("")
    @Operation(
            summary = "활동 목록",
            description = "활동을 카테고리, 연도 별로 조회합니다. query 값으로  category(카테고리 기준)나, year(연도 기준) 값을 주세요. " )
    public CareerResponse<List<? extends CareerGroupedByResponse>> read(
            @Login LoginInfo loginInfo,
            @RequestParam(name="status") String value
    ){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(HttpStatus.OK,
                CareerResponseMessage.CAREER_FINDALL_SUCCESS,
                careerService.getCareerGroupedBy(requestMember, value));
    }

    @PostMapping("/search")
    @Operation(
            summary = "활동 검색",
            description = "필터 조건에 맞게 활동들을 조회합니다."
    )
    public CareerResponse<?> search(
            @Login LoginInfo loginInfo,
            @RequestBody @Valid CareerRequestDto.SearchCareerDto request
    ) {

        Member requestMember = memberService.getById(loginInfo.getMemberId());

        if (request.getCareerName() && !request.getCareerDetail() && !request.getTag()) {
            List<Career> searchList = careerService.searchCareer(requestMember, request);
            return CareerResponse.success(HttpStatus.OK,
                    CareerResponseMessage.CAREER_FINDALL_SUCCESS,
                    CareerConverter.toCareerNameSearchDto(searchList));
        }
        return CareerResponse.success(HttpStatus.OK,
                CareerResponseMessage.CAREER_FINDALL_SUCCESS,
                careerService.searchCareerDetail(requestMember, request));
    }



}










