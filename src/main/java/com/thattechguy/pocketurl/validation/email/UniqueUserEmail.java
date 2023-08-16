package com.thattechguy.pocketurl.validation.email;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUserEmailValidator.class)
@Documented
public @interface UniqueUserEmail {
    String message() default "Email is already in use";
}
