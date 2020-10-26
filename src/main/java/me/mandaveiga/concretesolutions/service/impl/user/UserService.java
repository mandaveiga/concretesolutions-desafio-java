package me.mandaveiga.concretesolutions.service.impl.user;

import me.mandaveiga.concretesolutions.model.user.User;
import me.mandaveiga.concretesolutions.service.CrudService;

import java.util.Optional;

public interface UserService extends CrudService<User> {

    Optional<User> findByEmail(String email);
    Optional<User> authenticate(String email, String password);
}
