package me.mandaveiga.concretesolutions.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.mandaveiga.concretesolutions.controller.BaseController;
import me.mandaveiga.concretesolutions.controller.validator.AuthenticationValidator;
import me.mandaveiga.concretesolutions.model.error.ApplicationError;
import me.mandaveiga.concretesolutions.model.user.User;
import me.mandaveiga.concretesolutions.service.impl.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/login")
@Api(value = "Users")
public class AuthenticationController extends BaseController<User> {

    private final AuthenticationValidator authenticationValidator;

    @Autowired
    public AuthenticationController(UserServiceImpl service, AuthenticationValidator authenticationValidator) {
        super(service);
        this.authenticationValidator = authenticationValidator;
    }

    @PostMapping(path = "", produces = "application/json")
    @ApiOperation(value = "Authenticate user.")
    public ResponseEntity<Object> authenticate(@RequestBody @NonNull User body, @Nullable BindingResult result) {
        authenticationValidator.validate(body, result);

        if (result.hasErrors()) {
            ObjectError validationError = result.getAllErrors().get(0);
            ApplicationError error = new ApplicationError(validationError.getCode());

            return badRequest().body(error);
        }

        Optional<User> entity = ((UserServiceImpl) service).authenticate(body.getEmail(), body.getPassword());
        ApplicationError error = new ApplicationError("Usuário e/ou senha inválidos");

        return entity
                .map((user) -> {
                    user.setLastLogin(new Date());
                    service.save(user);

                    return ok((Object) user);
                })
                .orElseGet(() -> ResponseEntity.status(401).body(error));
    }
}
