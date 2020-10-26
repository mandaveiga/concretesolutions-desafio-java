package me.mandaveiga.concretesolutions.controller.validator;

import me.mandaveiga.concretesolutions.model.user.User;
import me.mandaveiga.concretesolutions.repository.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class AuthenticationValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty");

        Optional<User> entity = userRepository.findByEmail(user.getEmail());

        if (!entity.isPresent()) {
            errors.rejectValue("email", "Usuário e/ou senha inválidos");
        }
    }
}