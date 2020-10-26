package me.mandaveiga.concretesolutions.repository;

import me.mandaveiga.concretesolutions.model.user.User;
import me.mandaveiga.concretesolutions.repository.impl.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    public void whenFindingUserById_thenCorrect() {
        User createdUser = userRepository.save(new User("John", "john@domain.com", "123456"));

        Optional<User> user = userRepository.findById(createdUser.getId());

        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getName()).isEqualTo("John");
    }

    @Test
    public void whenFindingAllUsers_thenCorrect() {
        userRepository.save(new User("John", "john@domain.com", "123456"));
        userRepository.save(new User("Julie", "julie@domain.com", "123456"));

        List<User> users = (List<User>) userRepository.findAll();

        System.out.println(Arrays.toString(users.toArray()));

        assertThat(users.size()).isEqualTo(2);
    }
}
