package umc.kkijuk.server.career.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.service.CategoryService;
import umc.kkijuk.server.career.validation.CategoryValidator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CategoryValidatorTest {
    @Mock
    private CategoryService categoryService;
    @Mock
    private ConstraintValidatorContext context;
    @InjectMocks
    private CategoryValidator categoryValidator;
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("CategoryValidator 클래스가 존재하는 카테고리에 대해 유효성 검사를 제대로 수행하는지 검증")
    void 카테고리가_존재할_때_유효성_검사_성공(){
        //given
        int categoryId = 1;
        Category category = Category.builder()
                .id((long) categoryId)
                .name("테스트카테고리")
                .build();
        when(categoryService.findById((long) categoryId)).thenReturn(Optional.of(category));

        //when
        boolean isValid = categoryValidator.isValid(categoryId, context);

        //then
        assertTrue(isValid);
        verify(context,never()).disableDefaultConstraintViolation();
    }
    @Test
    @DisplayName("CategoryValidator 클래스가 존재하지 않는 카테고리에 대해 유효성 검사를 제대로 수행하는지 검증")
    void 카테고리가_존재하지_않을_때_유효성_검사_실패() {
        //given
        int categoryId = 1;
        when(categoryService.findById((long)categoryId)).thenReturn(Optional.empty());
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(CareerResponseMessage.CATEGORY_NOT_FOUND)).thenReturn(builder);

        //when
        boolean isValid = categoryValidator.isValid(categoryId, context);

        //then
        assertFalse(isValid);
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(CareerResponseMessage.CATEGORY_NOT_FOUND);
        verify(builder).addConstraintViolation();


    }

}
