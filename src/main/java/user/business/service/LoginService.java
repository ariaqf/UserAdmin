package user.business.service;

import user.business.entity.User;
import user.business.service.exceptions.WrongLoginParametersException;
import user.business.service.request.LoginRequest;
import user.business.service.response.UserResponse;
import user.dao.UserDAO;
import user.dao.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

public class LoginService {
    private PasswordService passwordService;
    private UserDAO dataSource;

    public LoginService(UserDAO dataSource) {
        this.dataSource = dataSource;
        passwordService = new PasswordService();
    }

    public UserResponse login(LoginRequest request) throws WrongLoginParametersException {
        User u = null;
        try {
            u = dataSource.getUserByEmail(request.getEmail());
        } catch (UserNotFoundException e) {
            throw new WrongLoginParametersException("Usuário e/ou senha inválidos");
        }
        boolean passwordOk = passwordService.validatePassword(request.getPassword(),u.getPassword(),u.getSalt());
        if(!passwordOk) {
            throw new WrongLoginParametersException("Usuário e/ou senha inválidos");
        }
        UUID uuid = UUID.randomUUID();
        u.setToken(uuid.toString());
        u.setLastLogin(LocalDateTime.now());
        return new UserResponse(dataSource.saveUser(u));
    }
}
