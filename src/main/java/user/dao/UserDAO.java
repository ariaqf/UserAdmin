package user.dao;

import user.business.entity.User;
import user.business.service.exceptions.UserAlreadyExistsException;
import user.dao.exceptions.UserNotFoundException;

public interface UserDAO {
    public User getUserByUid(String uid) throws UserNotFoundException;
    public User getUserByEmail(String email)  throws UserNotFoundException;
    public User saveUser(User user);
}
