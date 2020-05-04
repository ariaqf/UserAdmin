package database.hsqldb.repository;

import api.App;
import database.hsqldb.model.UserModel;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;



@RunWith(SpringRunner.class)
@SpringBootTest(classes= App.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindingUserById_thenCorrect() {
        UserModel user = new UserModel();
        user.setName("Bob");
        user.setEmail("Mail1");
        userRepository.save(user);
        assertThat(userRepository.findById(1L)).isInstanceOf(Optional.class);
    }

    @Test
    public void whenFindingAllUsers_thenCorrect() {
        UserModel user = new UserModel();
        user.setName("Bob");
        user.setEmail("Mail1");
        userRepository.save(user);
        user = new UserModel();
        user.setName("Joe");
        user.setEmail("Mail2");
        userRepository.save(user);
        assertThat(userRepository.findAll()).isInstanceOf(List.class);
    }

    @Test
    public void whenSavingUser_thenCorrect() {
        UserModel user = new UserModel();
        user.setName("Bob");
        user.setEmail("Mail1");
        userRepository.save(user);
        user = userRepository.findById(1L).orElseGet(()
                -> new UserModel());
        assertThat(user.getName()).isEqualTo("Bob");
    }
}
