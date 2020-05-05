package database.hsqldb.dao;

import database.hsqldb.model.PhoneModel;
import database.hsqldb.model.UserModel;
import database.hsqldb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import user.business.entity.Phone;
import user.business.entity.User;
import user.dao.UserDAO;
import user.dao.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {
    @Autowired
    UserRepository repository;

    @Override
    public User getUserByUid(String uid) throws UserNotFoundException {
        Optional<UserModel> optionalUser = repository.findByUid(uid);
        if(!optionalUser.isPresent()) {
            throw new UserNotFoundException();
        }
        return userFromUserModel(optionalUser.get());
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<UserModel> optionalUser = repository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            throw new UserNotFoundException();
        }
        return userFromUserModel(optionalUser.get());
    }

    @Override
    public User saveUser(User user) {
        UserModel uModel;
        try {
            uModel = userModelFromUser(getUserByEmail(user.getEmail()));
            uModel = updateUserModelFromUser(uModel, user);
        } catch (UserNotFoundException e) {
            uModel = userModelFromUser(user);
        }
        UserModel returnModel = repository.save(uModel);
        return userFromUserModel(returnModel);
    }

    private User userFromUserModel(UserModel userModel) {
        User user = new User();
        user.setId(userModel.getId());
        user.setUid(userModel.getUid());
        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        user.setPassword(userModel.getPassword());
        user.setSalt(userModel.getSalt());
        user.setCreated(userModel.getCreated());
        user.setModified(userModel.getModified());
        user.setLastLogin(userModel.getLastLogin());
        user.setToken(userModel.getToken());
        user.setPhones(phonesFromPhoneModel(userModel.getPhones()));
        return user;
    }

    private List<Phone> phonesFromPhoneModel(List<PhoneModel> phoneModels) {
        List<Phone> phoneList = new ArrayList<>();
        if(phoneModels != null) {
            for(PhoneModel p : phoneModels) {
                Phone phone = new Phone();
                phone.setDdd(p.getDdd());
                phone.setNumber(p.getNumber());
                phoneList.add(phone);
            }
        }
        return phoneList;
    }

    private UserModel userModelFromUser(User user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setUid(user.getUid());
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        userModel.setPassword(user.getPassword());
        userModel.setSalt(user.getSalt());
        userModel.setCreated(user.getCreated());
        userModel.setModified(user.getModified());
        userModel.setLastLogin(user.getLastLogin());
        userModel.setToken(user.getToken());
        userModel.setPhones(phoneModelsFromPhone(user.getPhones()));
        return userModel;
    }

    private List<PhoneModel> phoneModelsFromPhone(List<Phone> phones) {
        List<PhoneModel> phoneModelList = new ArrayList<>();
        if(phones != null) {
            for(Phone p : phones) {
                PhoneModel phoneModel = new PhoneModel();
                phoneModel.setDdd(p.getDdd());
                phoneModel.setNumber(p.getNumber());
                phoneModelList.add(phoneModel);
            }
        }
        return phoneModelList;
    }

    private UserModel updateUserModelFromUser(UserModel userModel, User user) {
        userModel.setName(user.getName());
        userModel.setPassword(user.getPassword());
        userModel.setSalt(user.getSalt());
        userModel.setModified(user.getModified());
        userModel.setLastLogin(user.getLastLogin());
        userModel.setPhones(phoneModelsFromPhone(user.getPhones()));
        userModel.setToken(user.getToken());
        return userModel;
    }
}
