package database.mock.user.dao;

import org.springframework.stereotype.Component;
import user.business.entity.User;
import user.dao.UserDAO;
import user.dao.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final List<User> users = new ArrayList<>();

    @Override
    public User getUserByUid(String uid) throws UserNotFoundException {
        User[] results = users.stream().filter(x -> x.getUid().equals(uid)).toArray(User[]::new);
        if(results.length == 0) {
            throw  new UserNotFoundException();
        }
        return results[0];
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        User[] results = users.stream().filter(x -> x.getEmail().equals(email)).toArray(User[]::new);
        if(results.length == 0) {
            throw  new UserNotFoundException();
        }
        return results[0];
    }

    @Override
    public User saveUser(User user) {
        User oldUser = null;
        try {
            if (user.getId() != null) {
                oldUser = getUserByUid(user.getUid());
            }
            if (user.getEmail() != null) {
                oldUser = getUserByEmail(user.getEmail());
            }
        } catch (UserNotFoundException e) {
            //pass
        }
        if (oldUser != null) {
            oldUser.setModified(LocalDateTime.now());
            oldUser.setLastLogin(user.getLastLogin());
            oldUser.setToken(user.getToken());
            user = oldUser;
        } else {
            user.setCreated(LocalDateTime.now());
            user.setModified(LocalDateTime.now());
            user.setLastLogin(LocalDateTime.now());
            users.add(user);
        }
        return user;
    }

    public void clear() {
        users.clear();
    }
}
