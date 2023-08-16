package com.thattechguy.pocketurl.validation.email;

import com.thattechguy.pocketurl.model.User;
import com.thattechguy.pocketurl.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {

    private final UserRepository userRepository;

    public UniqueUserEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isEmpty();
    }
}
