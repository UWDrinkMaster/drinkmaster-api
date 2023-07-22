package ca.uwaterloo.drinkmasterapi.integreation;

import ca.uwaterloo.drinkmasterapi.feature.user.model.User;
import ca.uwaterloo.drinkmasterapi.feature.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserRepository() {
        // Create a new user
        User user = new User();
        user.setUsername("test@example.com");
        user.setEmail("test@example.com");
        user.setPassword("password");

        // Save the user
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());

        // Retrieve the user by ID
        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());
        assertTrue(retrievedUser.isPresent());
        assertEquals(user.getEmail(), retrievedUser.get().getEmail());

        // Update the user
        retrievedUser.get().setEmail("updated@example.com");
        User updatedUser = userRepository.save(retrievedUser.get());
        assertEquals("updated@example.com", updatedUser.getEmail());

        // Delete the user
        userRepository.delete(updatedUser);

        // Verify that the user is deleted
        Optional<User> deletedUser = userRepository.findById(updatedUser.getId());
        assertFalse(deletedUser.isPresent());
    }
}
