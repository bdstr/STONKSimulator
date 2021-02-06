package net.kloczkowski.STONKSimulator.API.service;

import net.kloczkowski.STONKSimulator.API.model.User;
import net.kloczkowski.STONKSimulator.API.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    public void getAllShouldReturnListOfUsers() {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(1L);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        var result = userService.getAll();

        verify(userRepository).findAll();

        assertEquals(user1.getId(), result.get(0).getId());
        assertEquals(user2.getId(), result.get(1).getId());
    }

    @Test
    public void getAllShouldReturnEmptyListOfUsersIfRepositoryIsEmpty() {
        when(userRepository.findAll()).thenReturn(List.of());

        var result = userService.getAll();

        verify(userRepository).findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    public void getByIdShouldReturnUserWithGivenId() {
        User user = new User();
        Long id = 1L;
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        var result = userService.getById(id);

        verify(userRepository).findById(id);

        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void getByIdShouldThrowEntityNotFoundExceptionWhenUserIsNotPresent() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userService.getById(id);
        });

        verify(userRepository).findById(id);
    }

    @Test
    public void deleteShouldCallRepositoryDeleteWhenValidIdIsGiven() {
        User user = new User();
        Long id = 1L;
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.delete(id);

        verify(userRepository).delete(user);
    }

    @Test
    public void deleteShouldThrowNotFoundExceptionWhenUserWithGivenIdDoesNotExist() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                userService.delete(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void registerSavesNewUserToDatabase() throws Exception {
        User userWithId = new User();
        userWithId.setId(1L);
        userWithId.setUsername("user");
        userWithId.setPassword("password");
        userWithId.setAuthorities("ROLE_USER");

        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(userWithId);
        when(passwordEncoder.encode("password")).thenReturn("password");

        var result = userService.register("user", "password");

        verify(userRepository).save(any(User.class));

        var capturedUser = userArgumentCaptor.getValue();
        assertNull(capturedUser.getId());
        assertEquals(userWithId.getUsername(), capturedUser.getUsername());
        assertEquals(userWithId.getPassword(), capturedUser.getPassword());
        assertEquals(userWithId.getAuthorities(), capturedUser.getAuthorities());

        assertEquals(1L, result.getId());
        assertEquals(userWithId.getUsername(), result.getUsername());
        assertEquals(userWithId.getPassword(), result.getPassword());
        assertEquals(userWithId.getAuthorities(), result.getAuthorities());
    }

    @Test
    public void registerThrowsExceptionWhenPasswordTooShort() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                userService.register("user", "pass"))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void registerThrowsExceptionWhenUsernameTooShort() throws Exception {
        when(userRepository.findByUsername("us")).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                userService.register("us", "password"))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void updateUpdatesExistingUser() {
        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setUsername("user");
        userToUpdate.setPassword("new_password");
        userToUpdate.setAuthorities("ROLE_MODERATOR");

        User userFromDb = new User();
        userFromDb.setId(1L);
        userFromDb.setUsername("user");
        userFromDb.setPassword("password");
        userFromDb.setAuthorities("ROLE_USER");

        when(passwordEncoder.encode(userToUpdate.getPassword())).thenReturn(userToUpdate.getPassword());
        when(userRepository.findById(userToUpdate.getId())).thenReturn(Optional.of(userFromDb));
        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        var result = userService.update(userToUpdate);

        verify(userRepository).save(any(User.class));

        assertEquals(userToUpdate.getId(), result.getId());
        assertEquals(userToUpdate.getUsername(), result.getUsername());
        assertEquals(userToUpdate.getPassword(), result.getPassword());
        assertEquals(userToUpdate.getAuthorities(), result.getAuthorities());
    }

    @Test
    public void updateUserShouldThrowEntityNotFoundExceptionIfItHasIdAndIsNotPresentInDatabase() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword("password");

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userService.update(user);
        });
    }
}