package me.mandaveiga.concretesolutions.controller.validator;

import me.mandaveiga.concretesolutions.model.user.User;
import me.mandaveiga.concretesolutions.repository.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public RegisterValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phones", "phones.empty");

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "E-mail já existente.");
        } else if (user.getPassword().length() < 5 || user.getPassword().length() > 64) {
            errors.rejectValue("password", "password.size");
        } else if (user.getEmail().length() < 5 || user.getEmail().length() > 128) {
            errors.rejectValue("email", "email.size");
        }
    }
}