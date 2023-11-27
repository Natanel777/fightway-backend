package spring.natanel.fightwaybackend.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueEmailValidator.class})
public @interface UniqueEmail {

    String message() default "Email address must be unique";

    // Enable validation groups
    Class<?>[] groups() default {};

    // The field value is stored in the payload
    Class<? extends Payload>[] payload() default {};
}
