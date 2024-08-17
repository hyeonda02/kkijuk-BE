package umc.kkijuk.server.unitTest.careerdetail.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.careerdetail.controller.CareerDetailController;
import umc.kkijuk.server.careerdetail.controller.response.CareerDetailResponse;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.careerdetail.domain.mapping.CareerTag;
import umc.kkijuk.server.careerdetail.dto.CareerDetailRequestDto;
import umc.kkijuk.server.careerdetail.dto.CareerDetailResponseDto;
import umc.kkijuk.server.careerdetail.service.CareerDetailServiceImpl;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.member.service.MemberServiceImpl;
import umc.kkijuk.server.tag.domain.Tag;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CareerDetailControllerTest {
    @InjectMocks
    private CareerDetailController careerDetailController;
    @Mock
    private CareerDetailServiceImpl careerDetailService;
    @Mock
    private MemberServiceImpl memberService;
    private LoginInfo loginInfo;
    private Long testMemberId1 = 1L;
    private Long testMemberId2 = 2L;
    private Member testRequestMember1;
    private Member testRequestMember2;

    private Career career1;
    private Career career2;
    private Category category1;
    private CareerDetail careerDetail1;
    private CareerDetail careerDetail2;
    private CareerDetail updateDetail;
    private CareerTag careerTag1;
    private CareerTag careerTag2;
    private Tag tag1;
    private Tag tag2;
    @BeforeEach
    void init() {
        testRequestMember1 = Member.builder()
                .id(testMemberId1)
                .email("test@test.com")
                .phoneNumber("000-0000-0000")
                .birthDate(LocalDate.of(2024, 7, 31))
                .password("test")
                .userState(State.ACTIVATE)
                .build();

        loginInfo = LoginInfo.from(testRequestMember1);

        testRequestMember2 = Member.builder()
                .id(testMemberId2)
                .email("test@test.com")
                .phoneNumber("000-0000-0000")
                .birthDate(LocalDate.of(2024, 7, 31))
                .password("test")
                .userState(State.ACTIVATE)
                .build();

        category1 = Category.builder()
                .id(1L)
                .name("동아리")
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
                .careerDetailList(new ArrayList<>())
                .year(2024)
                .build();

        career2 = Career.builder()
                .id(2L)
                .memberId(testMemberId1)
                .name("test2")
                .alias("alias2")
                .summary("summary2")
                .unknown(false)
                .category(category1)
                .startdate(LocalDate.of(2024, 4, 10))
                .enddate(LocalDate.of(2024, 7, 20))
                .careerDetailList(new ArrayList<>())
                .year(2024)
                .build();

        tag1 = Tag.builder()
                .id(1L)
                .name("test tag1")
                .build();

        tag2 = Tag.builder()
                .id(2L)
                .name("test tag2")
                .build();

        careerTag1 = CareerTag.builder()
                .id(1L)
                .tag(tag1)
                .build();

        careerTag2 = CareerTag.builder()
                .id(2L)
                .tag(tag2)
                .build();

        careerDetail1 = CareerDetail.builder()
                .id(1L)
                .title("test title1")
                .content("test content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .memberId(testMemberId1)
                .careerTagList(new ArrayList<>(Arrays.asList(careerTag1,careerTag2)))
                .career(career1)
                .build();


        careerDetail2 = CareerDetail.builder()
                .id(2L)
                .title("test title2")
                .content("test content2")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .memberId(testMemberId1).careerTagList(Arrays.asList(careerTag1,careerTag2))
                .career(career2)
                .build();

        updateDetail = CareerDetail.builder()
                .id(1L)
                .title("update title1")
                .content("update content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .memberId(testMemberId1).careerTagList(Arrays.asList(careerTag2))
                .career(career1)
                .build();
    }

    @Test
    @DisplayName("[create] 새로운 careerDetail 생성")
    void createCareerDetail() {
        //given
        CareerDetailRequestDto.CareerDetailCreate createRequestDto = CareerDetailRequestDto.CareerDetailCreate.builder()
                .title("test title1")
                .content("test content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .tagList(Arrays.asList(1L,2L))
                .build();

        when(memberService.getById(testMemberId1)).thenReturn(testRequestMember1);
        when(careerDetailService.create(testRequestMember1, createRequestDto, 1L)).thenReturn(careerDetail1);

        //when
        CareerDetailResponse<CareerDetailResponseDto.CareerDetailResult> resultResponse =
                careerDetailController.create(loginInfo, createRequestDto, 1L);
        CareerDetailResponseDto.CareerDetailResult resultData = resultResponse.getData();
        //then
        assertAll(
                () -> assertEquals(resultData.getCareerId(), 1L),
                () ->assertEquals(resultResponse.getStaus(),
                        HttpStatus.CREATED.value()),
                () -> assertEquals(resultResponse.getMessage(),"활동 기록을 성공적으로 생성했습니다.")
        );
    }
    @Test
    @DisplayName("[create] 없는 사용자의 요청")
    void createCareerDetailResourceNotFoundException() {
        //given
        final Long NotExistingMemberId = 9999L;
        LoginInfo NotExistLoginInfo = LoginInfo.builder().memberId(NotExistingMemberId).build();

        CareerDetailRequestDto.CareerDetailCreate createRequestDto = CareerDetailRequestDto.CareerDetailCreate.builder()
                .title("test title1")
                .content("test content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .tagList(Arrays.asList(1L,2L))
                .build();

        when(memberService.getById(NotExistingMemberId))
                .thenThrow(new ResourceNotFoundException("Member", NotExistingMemberId));
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            careerDetailController.create(NotExistLoginInfo, createRequestDto,1L);
        });

    }
    @Test
    @DisplayName("[delete] 존재하는 careerDetail 삭제")
    void deleteCareerDetail() {
        //given
        when(memberService.getById(testMemberId1)).thenReturn(testRequestMember1);
        doNothing().when(careerDetailService).delete(testRequestMember1, 1L,1L);
        //when
        CareerDetailResponse<Object> resultResponse =  careerDetailController.delete(loginInfo, 1L,1L);
        //then
        assertAll(
                () -> assertEquals(resultResponse.getData(),null),
                () -> assertEquals(resultResponse.getMessage(), "활동 기록을 성공적으로 삭제했습니다."),
                () -> assertEquals(resultResponse.getStaus(), HttpStatus.OK.value())
        );

    }
    @Test
    @DisplayName("[delete] 없는 사용자의 요청")
    void deleteCareerDetailResourceNotFoundException() {
        //given
        final Long NotExistingMemberId = 9999L;
        LoginInfo NotExistLoginInfo = LoginInfo.builder().memberId(NotExistingMemberId).build();
        when(memberService.getById(NotExistingMemberId))
                .thenThrow(new ResourceNotFoundException("Member", NotExistingMemberId));
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            careerDetailController.delete(NotExistLoginInfo, 1L,1L);
        });

    }
    @Test
    @DisplayName("[update] 존재하는 careerDetail 수장")
    void updateCareerDetail() {
        //given
        CareerDetail updatedDetail = CareerDetail.builder()
                .id(1L)
                .title("updated title")
                .content("updated content")
                .startDate(LocalDate.of(2024,2,1))
                .endDate(LocalDate.of(2024,3,1))
                .memberId(testMemberId1)
                .careerTagList(new ArrayList<>(Arrays.asList(careerTag1,careerTag2)))
                .career(career1)
                .build();

        CareerDetailRequestDto.CareerDetailUpdate updateRequestDto = CareerDetailRequestDto.CareerDetailUpdate.builder()
                .title("updated title")
                .content("updated content")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 3, 1))
                .tagList(Arrays.asList(tag1.getId(), tag2.getId()))
                .build();

        when(memberService.getById(testMemberId1)).thenReturn(testRequestMember1);
        when(careerDetailService.update(testRequestMember1, updateRequestDto, 1L,1L)).thenReturn(updatedDetail);

        //when
        CareerDetailResponse<CareerDetailResponseDto.CareerDetailResult> resultResponse =
                careerDetailController.update(loginInfo, 1L,1L,updateRequestDto);
        CareerDetailResponseDto.CareerDetailResult resultData = resultResponse.getData();
        //then
        assertAll(
                () -> assertEquals(resultData.getCareerId(), 1L),
                () -> assertEquals(resultData.getTitle(), "updated title"),
                () -> assertEquals(resultData.getContent(), "updated content"),
                () -> assertEquals(resultResponse.getStaus(), HttpStatus.OK.value()),
                () -> assertEquals(resultResponse.getMessage(),"활동 기록을 성공적으로 수정했습니다.")
        );

    }
    @Test
    @DisplayName("[update] 없는 사용자의 요청")
    void updateCareerDetailResourceNotFoundException() {
        //given
        CareerDetailRequestDto.CareerDetailUpdate updateRequestDto = CareerDetailRequestDto.CareerDetailUpdate.builder()
                .title("updated title")
                .content("updated content")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 3, 1))
                .tagList(Arrays.asList(tag1.getId(), tag2.getId()))
                .build();

        final Long NotExistingMemberId = 9999L;
        LoginInfo NotExistLoginInfo = LoginInfo.builder().memberId(NotExistingMemberId).build();
        when(memberService.getById(NotExistingMemberId))
                .thenThrow(new ResourceNotFoundException("Member", NotExistingMemberId));
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            careerDetailController.update(NotExistLoginInfo, 1L,1L,updateRequestDto);
        });

    }
}
