import static org.assertj.core.api.Assertions.assertThat;

import com.example.tasks.models.user;
import com.example.tasks.repositories.userRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;


    private userRepository repo;
    @Autowired
    public UserRepositoryTests(TestEntityManager entityManager, userRepository repo) {
        this.entityManager = entityManager;
        this.repo = repo;
    }

    @Test
    public void testCreateUser() {
        user userr = new user();
        userr.setUserName("ravikuma");
        userr.setPassword("ravi2020");
        userr.setFirstName("Ravi");
        userr.setLastName("Kumar");

        user savedUser = repo.save(userr);

        user existUser = entityManager.find(user.class, savedUser.getId());

        assertThat(userr.getUserName()).isEqualTo(existUser.getUserName());


    }
}