package user.business.service;

import user.business.entity.User;
import user.business.service.exceptions.InvalidCredentialException;
import user.business.service.exceptions.SessionTimeoutException;
import user.business.service.response.UserResponse;
import user.dao.UserDAO;
import user.dao.exceptions.UserNotFoundException;

import java.time.LocalDateTime;

public class RetrieveService {
    UserDAO dataSource;

    public RetrieveService(UserDAO userDAO) {
        dataSource = userDAO;
    }

    public UserResponse getUser(String uid, String token) throws SessionTimeoutException, InvalidCredentialException {
        User user;
        try {
            user = dataSource.getUserByUid(uid);
        } catch (UserNotFoundException e) {
            throw new InvalidCredentialException("Não autorizado");
        }
        if(!user.getToken().equals(token)) {
            throw new InvalidCredentialException("Não autorizado");
        } else {
            if(!LocalDateTime.now().isBefore(user.getLastLogin().plusMinutes(30))) {
                throw new SessionTimeoutException("Sessão inválida");
            }
        }
        return new UserResponse(user);

    }


}
