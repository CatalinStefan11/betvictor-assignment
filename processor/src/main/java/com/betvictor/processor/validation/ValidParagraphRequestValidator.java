package com.betvictor.processor.validation;

import com.betvictor.processor.util.ParagraphLength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidParagraphRequestValidator implements ConstraintValidator<ValidParagraphLength, String> {
  private static Set<String> allowedValues;

  static {
    allowedValues = Arrays.stream(ParagraphLength.values())
        .map(Enum::name)
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isValid(String paragraphLength, ConstraintValidatorContext context) {
    return allowedValues.contains(paragraphLength.toUpperCase());
  }
}