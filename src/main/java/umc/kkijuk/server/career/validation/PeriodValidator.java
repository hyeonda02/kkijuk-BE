package umc.kkijuk.server.career.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.dto.CareerRequestDto;

@Component
@RequiredArgsConstructor
public class PeriodValidator implements ConstraintValidator<ValidPeriod, CareerRequestDto.CreateCareerDto> {
    @Override
    public void initialize(ValidPeriod constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(CareerRequestDto.CreateCareerDto request, ConstraintValidatorContext context) {
        if(request.getIsUnknown() == false){
            if(request.getEndDate()==null){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(CareerResponseMessage.CAREER_ENDDATE)
                        .addPropertyNode("endDate")
                        .addConstraintViolation();
                return false;
            }else if(request.getEndDate().isBefore(request.getStartDate())){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(CareerResponseMessage.CAREER_PERIOD_FAIL)
                        .addPropertyNode("endDate")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
