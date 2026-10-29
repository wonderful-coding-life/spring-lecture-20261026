package com.example.demo.form;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, MemberForm> {

    @Override
    public boolean isValid(MemberForm form, ConstraintValidatorContext context) {
        if (form == null || form.getPassword() == null || form.getPasswordConfirmation() == null) {
            return true;
        }

        if (form.getPassword().equals(form.getPasswordConfirmation())) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("passwordConfirmation")
                .addConstraintViolation();
        return false;
    }
}
