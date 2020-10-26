package me.mandaveiga.concretesolutions.service;

import me.mandaveiga.concretesolutions.model.user.User;
import me.mandaveiga.concretesolutions.model.user.phone.Phone;
import me.mandaveiga.concretesolutions.service.impl.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void whenSaveUser_thenCorrect() {
        User user = new User("John", "john@domain.com", "123456");
        user.getPhones().add(new Phone(123456789, 31));

        Optional<User> createdUser = userService.save(user);

        assertThat(createdUser.isPresent()).isTrue();

        Optional<User> findUser = userService.findById(createdUser.get().getId());

        assertThat(findUser.isPresent()).isTrue();
        assertThat(findUser.get().getName()).isEqualTo("John");
    }
}
