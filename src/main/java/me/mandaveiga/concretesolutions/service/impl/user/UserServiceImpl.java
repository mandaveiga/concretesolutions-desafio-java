package me.mandaveiga.concretesolutions.service.impl.user;

import me.mandaveiga.concretesolutions.model.user.User;
import me.mandaveiga.concretesolutions.repository.impl.PhoneRepository;
import me.mandaveiga.concretesolutions.repository.impl.UserRepository;
import me.mandaveiga.concretesolutions.service.BaseService;
import me.mandaveiga.concretesolutions.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    private PhoneRepository phoneRepository;

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserServiceImpl(UserRepository repository, PhoneRepository phoneRepository, JwtTokenUtil jwtTokenUtil) {
        super(repository);
        this.phoneRepository = phoneRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return ((UserRepository) repository).findByEmail(email);
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        Optional<User> entity = ((UserRepository) repository).findByEmail(email);

        if (!entity.isPresent()) {
            return Optional.empty();
        }

        User user = entity.get();

        user.setToken(jwtTokenUtil.generateToken(user));

        repository.save(user);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(password, user.getPassword())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setToken(jwtTokenUtil.generateToken(user));

        user.getPhones().forEach(phone -> {
            phone.setUserId(user.getId());
        });

        return super.save(user);
    }
}
