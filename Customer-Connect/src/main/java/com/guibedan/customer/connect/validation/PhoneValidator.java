package com.guibedan.customer.connect.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class PhoneValidator implements ConstraintValidator<PhoneValidation, String> {

    @Override
    public void initialize(PhoneValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String tel = s.replaceAll("\\D", "");

        if (!StringUtils.hasText(s)) {
            return true;
        }

        if (tel.length() != 10 && tel.length() != 11) {
            return false;
        }

        return tel.length() != 11 || tel.charAt(2) == '9';
    }

}
