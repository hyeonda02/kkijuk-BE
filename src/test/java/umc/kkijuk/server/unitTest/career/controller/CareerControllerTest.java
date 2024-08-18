package umc.kkijuk.server.unitTest.career.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import umc.kkijuk.server.career.controller.CareerController;
import umc.kkijuk.server.career.controller.response.CareerResponse;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.CareerResponseDto;
import umc.kkijuk.server.career.dto.converter.CareerConverter;
import umc.kkijuk.server.career.service.CareerServiceImpl;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.service.MemberServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CareerControllerTest {
    @InjectMocks
    private CareerController careerController;
    @Mock
    private CareerServiceImpl careerService;
    @Mock
    private MemberServiceImpl memberService;
    private final Long testMemberId1 = 1L;

    private LoginInfo loginInfo;
    private Career career1;
    private Career career2;
    private Career career3;
    private Category category1;
    private Category category2;
    private Member requestMember1;
    @BeforeEach
    void init() {
        requestMember1 = Member.builder()
                .id(testMemberId1)
                .build();

        loginInfo = LoginInfo.from(requestMember1);

        category1 = Category.builder()
                .id(1L)
                .name("동아리")
                .build();

        category2 = Category.builder()
                .id(2L)
                .name("대외활동")
                .build();

        career1 = Career.builder()
                .id(1L)
                .memberId(testMemberId1)
                .name("test")
                .alias("alias")
                .summary("summary")
                .unknown(false)
                .category(category1)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .year(2024)
                .build();
        career2 = Career.builder()
                .id(2L)
                .memberId(testMemberId1)
                .name("test2")
                .alias("alias2")
                .summary("summary2")
                .unknown(true)
                .category(category2)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.now())
                .year(2024)
                .build();

        career3 = Career.builder()
                .id(3L)
                .memberId(testMemberId1)
                .name("test3")
                .alias("alias3")
                .summary("summary3")
                .unknown(true)
                .category(category2)
                .startdate(LocalDate.of(2022, 4, 10))
                .enddate(LocalDate.of(2022, 8, 10))
                .year(2022)
                .build();
    }
    @Test
    @DisplayName("[create] 새로운 career 생성")
    void testCreate() {
        //given
        CareerRequestDto.CreateCareerDto requestDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test")
                .alias("alias")
                .summary("summary")
                .isUnknown(false)
                .startDate(LocalDate.of(2024,4,10))
                .endDate(LocalDate.of(2024,7,20))
                .category(1)
                .build();
        when(memberService.getById(testMemberId1)).thenReturn(requestMember1);
        when(careerService.createCareer(requestMember1, requestDto)).thenReturn(career1);
        //when
        CareerResponse<CareerResponseDto.CareerResultDto> resultResponse = careerController.create(loginInfo, requestDto);
        CareerResponseDto.CareerResultDto resultData = resultResponse.getData();
        //then
        assertAll(
                () -> assertEquals(resultData.getCareerId(),1L),
                () -> assertEquals(resultResponse.getMessage(),
                        CareerResponseMessage.CAREER_CREATE_SUCCESS)
        );
    }
    @Test
    @DisplayName("[create] 없는 사용자의 요청")
    void testCreateResourceNotFoundException() {
        //given
        final Long NotExistingMemberId = 9999L;
        LoginInfo NotExistLoginInfo = LoginInfo.builder().memberId(NotExistingMemberId).build();

        CareerRequestDto.CreateCareerDto requestDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test")
                .alias("alias")
                .summary("summary")
                .isUnknown(false)
                .startDate(LocalDate.of(2024,4,10))
                .endDate(LocalDate.of(2024,7,20))
                .category(1)
                .build();

        when(memberService.getById(NotExistingMemberId))
                .thenThrow(new ResourceNotFoundException("Member", NotExistingMemberId));
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            careerController.create(NotExistLoginInfo, requestDto);
        });
    }
    @Test
    @DisplayName("[delete] 존재하는 career 삭제")
    void testDelete() {
        //given
        when(memberService.getById(testMemberId1)).thenReturn(requestMember1);
        doNothing().when(careerService).deleteCareer(requestMember1, 1L);
        //when
        CareerResponse<Object> resultResponse =  careerController.delete(loginInfo, 1L);
        //then
        assertAll(
                () -> assertEquals(resultResponse.getData(),null),
                () -> assertEquals(resultResponse.getMessage(),
                        CareerResponseMessage.CAREER_DELETE_SUCCESS)
        );
    }
    @Test
    @DisplayName("[delete] 없는 사용자의 요청")
    void testDeleteResourceNotFoundException() {
        //given
        final Long NotExistingMemberId = 9999L;
        LoginInfo NotExistLoginInfo = LoginInfo.builder().memberId(NotExistingMemberId).build();
        when(memberService.getById(NotExistingMemberId))
                .thenThrow(new ResourceNotFoundException("Member", NotExistingMemberId));
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            careerController.delete(NotExistLoginInfo, 1L);
        });
    }
    @Test
    @DisplayName("[update] 기존 career 수정")
    void testUpdate() {
        //given
        CareerRequestDto.UpdateCareerDto updateRequestDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isUnknown(true)
                .startDate(LocalDate.of(2021, 1, 1))
                .category(2)
                .build();

        Career updatedCareer = Career.builder()
                .id(1L)
                .memberId(testMemberId1)
                .name("update test")
                .alias("update alias")
                .summary("update summary")
                .unknown(true)
                .category(category2)
                .startdate(LocalDate.of(2021,1,1))
                .enddate(LocalDate.now())
                .year(2024)
                .build();

        when(memberService.getById(testMemberId1)).thenReturn(requestMember1);
        when(careerService.updateCareer(requestMember1,1L,updateRequestDto)).thenReturn(updatedCareer);

        //when
        CareerResponse<CareerResponseDto.CareerDto> resultResponse = careerController.update(loginInfo, updateRequestDto, 1L);
        CareerResponseDto.CareerDto resultData = (CareerResponseDto.CareerDto) resultResponse.getData();

        //then
        assertAll(
                () -> assertEquals(resultResponse.getMessage(), CareerResponseMessage.CAREER_UPDATE_SUCCESS),


                () -> assertThat(resultData.getId()).isEqualTo(1L),
                () -> assertThat(resultData.getCareerName().equals("update test")),
                () -> assertThat(resultData.getAlias()).isEqualTo("update alias"),
                () -> assertThat(resultData.getSummary()).isEqualTo("update summary"),
                () -> assertThat(resultData.getStartDate()).isEqualTo(LocalDate.of(2021,1,1)),
                () -> assertThat(resultData.getEndDate()).isEqualTo(LocalDate.now()),
                () -> assertThat(resultData.getYear()).isEqualTo(LocalDate.now().getYear()),
                () -> assertThat(resultData.getCategoryId()).isEqualTo(2L),
                () -> assertThat(resultData.getCategoryName()).isEqualTo("대외활동")
        );
    }
    @Test
    @DisplayName("[update] 없는 사용자의 요청")
    void testUpdateResourceNotFoundException() {
        //given
        CareerRequestDto.UpdateCareerDto updateRequestDto = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isUnknown(true)
                .startDate(LocalDate.of(2021, 1, 1))
                .category(2)
                .build();

        final Long NotExistingMemberId = 9999L;
        LoginInfo NotExistLoginInfo = LoginInfo.builder().memberId(NotExistingMemberId).build();

        when(memberService.getById(NotExistingMemberId))
                .thenThrow(new ResourceNotFoundException("Member", NotExistingMemberId));
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            careerController.update(NotExistLoginInfo, updateRequestDto ,1L);
        });

    }
    @Test
    @DisplayName("[read] 활동 목록을 category 별로 조회 ")
    void testReadCategory() {
        // given
        String value = "category";

        Map<String, List<Career>> groupedCareers = new HashMap<>();
        groupedCareers.put(category1.getName(), Arrays.asList(career1));
        groupedCareers.put(category2.getName(), Arrays.asList(career2, career3));

        List<CareerResponseDto.CareerGroupedByCategoryDto> result = CareerConverter.toCareerGroupedByCategoryDto(groupedCareers);

        when(memberService.getById(testMemberId1)).thenReturn(requestMember1);
        when(careerService.getCareerGroupedByCategory(requestMember1)).thenReturn(result);

        // when
        CareerResponse<?> resultResponse = careerController.read(loginInfo, value);
        // then
        assertAll(
                () -> assertThat(resultResponse.getMessage()).isEqualTo(CareerResponseMessage.CAREER_FINDALL_SUCCESS),
                () -> assertThat(resultResponse.getData()).isEqualTo(result)
        );

    }
    @Test
    @DisplayName("[read] 활동 목록을 year 별로 조회")
    void testReadYear() {
        // given
        String value = "year";

        Map<String, List<Career>> groupedCareers = new HashMap<>();
        groupedCareers.put(String.valueOf(career1.getYear()), Arrays.asList(career1,career2));
        groupedCareers.put(String.valueOf(career3.getYear()), Arrays.asList(career3));

        List<CareerResponseDto.CareerGroupedByYearDto> result = CareerConverter.toCareerGroupedByYearDto(groupedCareers);

        when(memberService.getById(testMemberId1)).thenReturn(requestMember1);
        when(careerService.getCareerGroupedByYear(requestMember1)).thenReturn(result);

        // when
        CareerResponse<?> response = careerController.read(loginInfo, value);

        // then
        assertAll(
                () -> assertThat(response.getMessage()).isEqualTo(CareerResponseMessage.CAREER_FINDALL_SUCCESS),
                () -> assertThat(response.getData()).isEqualTo(result)
        );

    }
    @Test
    @DisplayName("[read] 없는 사용자의 요청")
    void testReadResourceNotFoundException() {
        //given
        final Long NotExistingMemberId = 9999L;
        LoginInfo NotExistLoginInfo = LoginInfo.builder().memberId(NotExistingMemberId).build();
        when(memberService.getById(NotExistingMemberId))
                .thenThrow(new ResourceNotFoundException("Member", NotExistingMemberId));
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            careerController.read(NotExistLoginInfo, "category");
        });

    }




}
