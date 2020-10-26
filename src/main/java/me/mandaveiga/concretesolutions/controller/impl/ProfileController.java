package me.mandaveiga.concretesolutions.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.mandaveiga.concretesolutions.controller.BaseController;
import me.mandaveiga.concretesolutions.controller.dto.ProfileValidatorDto;
import me.mandaveiga.concretesolutions.controller.validator.ProfileValidator;
import me.mandaveiga.concretesolutions.model.error.ApplicationError;
import me.mandaveiga.concretesolutions.model.error.validator.ApplicationErrorBindingResult;
import me.mandaveiga.concretesolutions.model.user.User;
import me.mandaveiga.concretesolutions.service.impl.user.UserServiceImpl;
import me.mandaveiga.concretesolutions.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/profile")
@Api(value = "Users")
public class ProfileController extends BaseController<User> {

    private final ProfileValidator profileValidator;

    @Autowired
    public ProfileController(UserServiceImpl service, ProfileValidator profileValidator) {
        super(service);
        this.profileValidator = profileValidator;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ApiOperation(value = "Show user profile.")
    public ResponseEntity<Object> show(@PathVariable("id") @NonNull String id, @RequestHeader @Nullable String authorization) {
        String token = JwtTokenUtil.getTokenFromHeader(authorization);

        ProfileValidatorDto profileValidatorDto = new ProfileValidatorDto(id, authorization, token);
        ApplicationErrorBindingResult bindingResult = new ApplicationErrorBindingResult(profileValidatorDto);

        profileValidator.validate(profileValidatorDto, bindingResult);

        ResponseEntity<Object> applicationError = ApplicationError.verify(bindingResult);

        if (applicationError != null) {
            return applicationError;
        }

        Optional<User> entity = service.findById(UUID.fromString(id));

        return entity
                .map(user -> {
                    if (!user.getToken().equals(token)) {
                        return status(401).body((Object) new ApplicationError("Não autorizado"));
                    }

                    Date currentDateMinusThirtyMinutes = new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(30));

                    if (user.getLastLogin().before(currentDateMinusThirtyMinutes)) {
                        return status(401).body((Object) new ApplicationError("Sessão inválida"));
                    }

                    return ok((Object) user);
                })
                .orElseGet(() -> notFound().build());
    }
}
