package me.mandaveiga.concretesolutions.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.mandaveiga.concretesolutions.controller.BaseController;
import me.mandaveiga.concretesolutions.controller.validator.RegisterValidator;
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

import java.util.Optional;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@Api(value = "Users")
public class RegisterController extends BaseController<User> {

    private final RegisterValidator registerValidator;

    @Autowired
    public RegisterController(UserServiceImpl service, RegisterValidator registerValidator) {
        super(service);
        this.registerValidator = registerValidator;
    }

    @PostMapping(path = "", produces = "application/json")
    @ApiOperation(value = "Persist a new user in database.")
    public ResponseEntity<Object> save(@RequestBody @NonNull User body, @Nullable BindingResult result) {
        registerValidator.validate(body, result);

        if (result.hasErrors()) {
            ObjectError validationError = result.getAllErrors().get(0);
            ApplicationError error = new ApplicationError(validationError.getCode());

            return badRequest().body(error);
        }

        Optional<User> entity = service.save(body);

        return entity
                .map((x) -> ok((Object) x))
                .orElseGet(() -> badRequest().build());
    }
}
