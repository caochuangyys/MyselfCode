package com.nft.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Documented
@Constraint(validatedBy = {DateValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Date {

    String message() default "";

    String pattern();

    /**
     * 这两行不加会报错 javax.validation.ConstraintDefinitionException: HV000074
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
