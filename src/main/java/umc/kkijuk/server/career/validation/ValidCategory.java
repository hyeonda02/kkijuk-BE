package umc.kkijuk.server.career.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCategory {
    String message() default "존재하는 카테고리 Id가 아닙니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};

}
