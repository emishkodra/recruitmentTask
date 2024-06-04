package com.developer.recruitmentTask.util.isbn;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ValidISBN, String> {

    @Override
    public void initialize(ValidISBN constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        value = value.replaceAll("[-\\s]", "");
        return isValidISBN10(value) || isValidISBN13(value);
    }

    private boolean isValidISBN10(String isbn) {
        if (isbn.length() != 10) {
            return false;
        }
        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += (isbn.charAt(i) - '0') * (10 - i);
            }
            char lastChar = isbn.charAt(9);
            if (lastChar != 'X' && (lastChar < '0' || lastChar > '9')) {
                return false;
            }
            sum += (lastChar == 'X') ? 10 : (lastChar - '0');
            return sum % 11 == 0;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isValidISBN13(String isbn) {
        if (isbn.length() != 13) {
            return false;
        }
        try {
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += (isbn.charAt(i) - '0') * (i % 2 == 0 ? 1 : 3);
            }
            int checksum = 10 - (sum % 10);
            if (checksum == 10) {
                checksum = 0;
            }
            return (checksum == (isbn.charAt(12) - '0'));
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
