package user.business.service;

import database.mock.user.dao.UserDAOImpl;
import org.junit.Test;
import user.business.entity.User;
import user.business.service.exceptions.UserAlreadyExistsException;
import user.business.service.request.CreateRequest;
import user.business.service.response.UserResponse;
import user.dao.UserDAO;

public class CreateServiceTest {

    UserDAO dao;

    private void Setup() {
        // Make sure we're testing clear datasets
        UserDAOImpl dao = new UserDAOImpl();
        dao.clear();
        this.dao = dao;
    }

    @Test
    public void CreateNewUser() {
        Setup();
        CreateRequest request = new CreateRequest();
        request.setEmail("email");
        request.setName("name");
        request.setPassword("pass");
        request.setPhones(null);
        CreateService service = new CreateService(dao);
        try {
            UserResponse u = service.CreateUser(request);
        } catch (UserAlreadyExistsException e) {
            assert true == false; //We don't expect to get here if there are no errors
        }
    }

    @Test
    public void CreateExistingUser() {
        Setup();
        CreateRequest request = new CreateRequest();
        request.setEmail("email");
        request.setName("name");
        request.setPassword("pass");
        request.setPhones(null);
        CreateService service = new CreateService(dao);
        try {
            UserResponse u = service.CreateUser(request);
        } catch (UserAlreadyExistsException e) {
            assert true == false; //We don't expect to get here if there are no errors
        }
        try {
            UserResponse u = service.CreateUser(request);
            assert true == false; //We don't expect to get here if there are no errors
        } catch (UserAlreadyExistsException e) {
            assert true == true;
        }
    }
}
