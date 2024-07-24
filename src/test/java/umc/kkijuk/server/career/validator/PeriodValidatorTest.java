package umc.kkijuk.server.career.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.validation.PeriodValidator;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PeriodValidatorTest {

    private ConstraintValidatorContext context;
    private PeriodValidator periodValidator;
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder;
    @BeforeEach
    void init(){
        periodValidator = new PeriodValidator();
        context = mock(ConstraintValidatorContext.class);
        builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        nodeBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);
    }

    @Test
    @DisplayName("유효한 기간을 가진 활동일 경우 유효성 검사(PeriodValidator) 성공")
    void 활동_시작날짜가_활동종료날짜보다_앞에_있을_경우_유효성_검사(){
        //given
        CareerRequestDto.CreateCareerDto careerDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test")
                .alias("alias")
                .isCurrent(false)
                .startDate(LocalDate.of(2024,5,10))
                .endDate(LocalDate.of(2024,7,10))
                .category(1)
                .build();

        //when
        boolean isValid = periodValidator.isValid(careerDto, context);

        //then
        assertTrue(isValid);
        verify(context,never()).disableDefaultConstraintViolation();

    }

    @Test
    @DisplayName("현재 진행중인 활동이 아닌데 활동 종료 날짜를 입력하지 않은 경우 유효성 검사(PeriodValidator) 실패")
    void 종료날짜가_null일때_유효성_검사_실패() {
        //given
        CareerRequestDto.CreateCareerDto careerDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test")
                .alias("alias")
                .isCurrent(false)
                .startDate(LocalDate.of(2024,7,10))
                .endDate(null)
                .category(1)
                .build();
        when(context.buildConstraintViolationWithTemplate(CareerResponseMessage.CAREER_ENDDATE)).thenReturn(builder);
        when(builder.addPropertyNode("endDate")).thenReturn(nodeBuilder);
        //when
        boolean isValid = periodValidator.isValid(careerDto, context);
        //then
        assertFalse(isValid);
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(CareerResponseMessage.CAREER_ENDDATE);
        verify(builder).addPropertyNode("endDate");
        verify(nodeBuilder).addConstraintViolation();

    }

    @Test
    @DisplayName("유효하지 않은 기간을 가진 활동에 대해 유효성 검사(PeriodValidator)  실패")
    void 활동_시작날짜가_활동_종료날짜보다_나중일_경우_유효성_검사(){
        //given
        CareerRequestDto.CreateCareerDto careerDto = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test")
                .alias("alias")
                .isCurrent(false)
                .startDate(LocalDate.of(2024,7,10))
                .endDate(LocalDate.of(2024,5,10))
                .category(1)
                .build();
        when(context.buildConstraintViolationWithTemplate(CareerResponseMessage.CAREER_PERIOD_FAIL)).thenReturn(builder);
        when(builder.addPropertyNode("endDate")).thenReturn(nodeBuilder);

        //when
        boolean isValid = periodValidator.isValid(careerDto, context);

        //then
        assertFalse(isValid);
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(CareerResponseMessage.CAREER_PERIOD_FAIL);
        verify(builder).addPropertyNode("endDate");
        verify(nodeBuilder).addConstraintViolation();
    }

}
