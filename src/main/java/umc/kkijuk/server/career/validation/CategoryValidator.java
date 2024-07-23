package umc.kkijuk.server.career.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.service.CategoryService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryValidator implements ConstraintValidator<ValidCategory,Integer>{
    private final CategoryService categoryService;
    @Override
    public void initialize(ValidCategory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        Optional<Category> target = categoryService.findById(value.longValue());
        if(target.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(CareerResponseMessage.CATEGORY_NOT_FOUND).addConstraintViolation();
            return false;
        }
        return true;
    }
}
