package me.mandaveiga.concretesolutions.repository.impl;

import me.mandaveiga.concretesolutions.model.user.User;
import me.mandaveiga.concretesolutions.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByEmail(String email);
}
