package com.todatseafood.api.util;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class TestValidator {
    public static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private TestValidator() {};
}
