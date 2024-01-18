package spring.natanel.fightwaybackend.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//we took this class from @Email and change it
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME) //we need to check in runtime
@Constraint(validatedBy = {UniqueProductNameValidator.class})
public @interface UniqueProductName {
    String message() default "Product Name must be unique";
    //enable validation groups
    Class<?>[] groups() default {};
    //the annotation stores a payload
    //the field value is stored in the payload
    Class<? extends Payload>[] payload() default { };
}