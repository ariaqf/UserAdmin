package user.business.service;

import user.business.entity.Phone;
import user.business.entity.User;
import user.business.service.exceptions.InvalidDataException;
import user.business.service.exceptions.UserAlreadyExistsException;
import user.business.service.request.CreateRequest;
import user.business.service.request.PhoneRequest;
import user.business.service.response.UserResponse;
import user.dao.UserDAO;
import user.dao.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateService {
    private PasswordService passwordService;
    private UserDAO dataSource;

    public CreateService(UserDAO dataSource) {
        this.dataSource = dataSource;
        passwordService = new PasswordService();
    }

    public UserResponse CreateUser(CreateRequest request) throws UserAlreadyExistsException, InvalidDataException {
        if (request.getEmail() == null || request.getPassword() == null) {
            throw new InvalidDataException("Email e password tem de estar preenchidos!");
        }
        try {
            dataSource.getUserByEmail(request.getEmail());
            throw new UserAlreadyExistsException("E-mail já existente");
        } catch (UserNotFoundException e) {

        }
        User u = new User();
        u.setEmail(request.getEmail());
        u.setName(request.getName());
        u.setSalt(passwordService.getSalt());
        u.setPassword(passwordService.createPassword(request.getPassword()));
        List<Phone> phones = new ArrayList<>();
        if(request.getPhones() != null) {
            for (PhoneRequest p : request.getPhones()) {
                Phone phone = new Phone();
                phone.setNumber(p.getNumber());
                phone.setDdd(p.getDdd());
                phones.add(phone);
            }
        }
        u.setPhones(phones);
        u.setModified(LocalDateTime.now());
        u.setCreated(LocalDateTime.now());
        UUID uuid = UUID.randomUUID();
        u.setToken(uuid.toString());
        u.setLastLogin(LocalDateTime.now());
        return new UserResponse(dataSource.saveUser(u));
    }

}
