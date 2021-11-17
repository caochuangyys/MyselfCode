package com.nft.core.validation;

import cn.hutool.core.date.LocalDateTimeUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class DateValidator implements ConstraintValidator<Date, String> {

    private String pattern;

    @Override
    public void initialize(Date constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean result = true;
        if (value == null){
            return true;
        }
        try {
            LocalDateTimeUtil.parse(value, this.pattern);
        }catch (Exception ex){
            result = false;
        }
        return result;
    }

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(formatter.parse("2020-12-17 00:00:00"));
    }
}
