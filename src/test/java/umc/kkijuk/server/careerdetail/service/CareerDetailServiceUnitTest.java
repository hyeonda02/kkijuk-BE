package umc.kkijuk.server.careerdetail.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.careerdetail.domain.mapping.CareerTag;
import umc.kkijuk.server.careerdetail.dto.CareerDetailRequestDto;
import umc.kkijuk.server.careerdetail.repository.CareerDetailRepository;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.repository.TagRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CareerDetailServiceUnitTest {
    @Mock
    private CareerRepository careerRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private CareerDetailRepository careerDetailRepository;
    @InjectMocks
    private CareerDetailServiceImpl careerDetailService;
    private Long testMemberId1 = 333L;
    private Member testRequestMember1;

    private Career career;
    private Category category1;
    private CareerDetail careerDetail;
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

        category1 = Category.builder()
                .id(1L)
                .name("동아리")
                .build();

        career = Career.builder()
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
                .careerDetail(careerDetail)
                .tag(tag1)
                .build();
        careerTag2 = CareerTag.builder()
                .id(2L)
                .careerDetail(careerDetail)
                .tag(tag2)
                .build();

        careerDetail = CareerDetail.builder()
                .id(1L)
                .title("test title1")
                .content("test content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .memberId(testMemberId1).careerTagList(Arrays.asList(careerTag1,careerTag2))
                .career(career)
                .build();
    }
    @Test
    void create_새로운_careerDetail_생성_성공() {
        //given
        CareerDetailRequestDto.CareerDetailCreate createRequestDto = CareerDetailRequestDto.CareerDetailCreate.builder()
                .title("test title1")
                .content("test content1")
                .startDate(LocalDate.of(2024,1,1))
                .endDate(LocalDate.of(2024,1,2))
                .tagList(Arrays.asList(tag1.getId(),tag2.getId()))
                .build();

        when(careerRepository.findById(1L)).thenReturn(Optional.of(career));

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag1));
        when(tagRepository.findById(2L)).thenReturn(Optional.of(tag2));

        when(careerDetailRepository.save(any(CareerDetail.class))).thenReturn(careerDetail);

        //when
        CareerDetail newCareerDetail = careerDetailService.create(testRequestMember1, createRequestDto, career.getId());

        //then
        assertAll(
                () -> assertThat(newCareerDetail.getTitle()).isEqualTo(createRequestDto.getTitle()),
                () -> assertThat(newCareerDetail.getContent()).isEqualTo(createRequestDto.getContent()),
                () -> assertThat(newCareerDetail.getCareer()).isEqualTo(career),
                () -> assertThat(newCareerDetail.getStartDate()).isEqualTo(createRequestDto.getStartDate()),
                () -> assertThat(newCareerDetail.getEndDate()).isEqualTo(createRequestDto.getEndDate()),
                () -> assertThat(newCareerDetail.getMemberId()).isEqualTo(testMemberId1),
                () -> assertThat(newCareerDetail.getCareerTagList()).isEqualTo(Arrays.asList(careerTag1,careerTag2))
        );

        verify(careerRepository, times(1)).findById(career.getId());
        verify(tagRepository, times(2)).findById(any(Long.class));
        verify(careerDetailRepository, times(1)).save(any(CareerDetail.class));
    }
    @Test
    void delete_기존_careerDetail_성공() {
        //given
        when(careerDetailRepository.findById(any(Long.class))).thenReturn(Optional.of(careerDetail));
        //when
        careerDetailService.delete(testRequestMember1, 1L,1L);
        //then
        verify(careerDetailRepository,times(1)).delete(careerDetail);
    }
    @Test
    @DisplayName("delete - 존재하지 않는 CareerDetailId 입력시 ResourceNotFoundException 발 ")
    void delete_기존_careerDetail_실패() {
        //given
        when(careerDetailRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(ResourceNotFoundException.class, () ->
                careerDetailService.delete(testRequestMember1, 1L,1L));
        verify(careerDetailRepository, never()).delete(any(CareerDetail.class));
        verify(careerDetailRepository,times(1)).findById(any(Long.class));
    }
}
