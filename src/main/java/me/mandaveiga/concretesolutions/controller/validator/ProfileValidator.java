package me.mandaveiga.concretesolutions.controller.validator;

import me.mandaveiga.concretesolutions.controller.dto.ProfileValidatorDto;
import me.mandaveiga.concretesolutions.model.user.User;
import me.mandaveiga.concretesolutions.repository.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.UUID;

@Component
public class ProfileValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public ProfileValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProfileValidatorDto profileValidatorDto = (ProfileValidatorDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "authorization", "Não autorizado");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "token", "Não autorizado");

        try {
            UUID.fromString(profileValidatorDto.getId());
        } catch (Exception ignore) {
            errors.rejectValue("id", "Usuário não encontrado");
        }
    }
}