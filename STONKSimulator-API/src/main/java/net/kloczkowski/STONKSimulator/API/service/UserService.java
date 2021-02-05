package net.kloczkowski.STONKSimulator.API.service;

import net.kloczkowski.STONKSimulator.API.model.User;
import net.kloczkowski.STONKSimulator.API.model.Wallet;
import net.kloczkowski.STONKSimulator.API.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static net.kloczkowski.STONKSimulator.API.utils.Utils.fallbackIfNull;

@Service
public class UserService extends CrudService<User> {

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User update(User user) {

        Optional<User> userInDb = repository.findById(user.getId());

        if (userInDb.isPresent()) {
            var dbEntity = userInDb.get();

            if (((UserRepository) repository).findByUsername(user.getUsername()).isEmpty()) {
                dbEntity.setUsername(fallbackIfNull(user.getUsername(), dbEntity.getUsername()));
            }
            dbEntity.setPassword(passwordEncoder.encode(
                    fallbackIfNull(user.getPassword(), dbEntity.getPassword()))
            );
            dbEntity.setAuthorities(fallbackIfNull(user.getAuthorities(), dbEntity.getAuthorities()));

            return repository.save(dbEntity);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public User getByUsername(String username) {
        return ((UserRepository) repository).findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public User register(String username, String password) {
        if (((UserRepository) repository).findByUsername(username).isEmpty()) {
            User newUser = new User();

            if (username.length() > 3) {
                newUser.setUsername(username);
            } else {
                throw new RuntimeException("Username too short");
            }

            if (password != null && password.length() >= 8) {
                newUser.setPassword(passwordEncoder.encode(password));
            } else {
                throw new RuntimeException("Password too short");
            }

            newUser.setAuthorities("ROLE_USER");

            Wallet newWallet = new Wallet();
            newWallet.setBalance(100_000);

            newUser.setWallet(newWallet);

            return repository.save(newUser);
        } else {
            throw new EntityNotFoundException();
        }
    }

}
