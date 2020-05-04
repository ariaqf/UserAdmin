package user.business.service;

import database.mock.user.dao.UserDAOImpl;
import org.junit.Test;
import user.business.entity.User;
import user.business.service.exceptions.WrongLoginParametersException;
import user.business.service.request.CreateRequest;
import user.business.service.request.LoginRequest;
import user.business.service.response.UserResponse;
import user.dao.UserDAO;

public class LoginServiceTest {
    UserDAO dao;
    UserResponse user;

    private void Setup() {
        // Make sure we're testing clear datasets
        UserDAOImpl dao = new UserDAOImpl();
        dao.clear();
        this.dao = dao;
        CreateService service = new CreateService(dao);
        CreateRequest req = new CreateRequest();
        req.setEmail("Mail");
        req.setName("Name");
        req.setPassword("Pass");
        req.setPhones(null);
        try {
            user = service.CreateUser(req);
        } catch (Exception e) {}
    }

    @Test
    public void TestLoginExistingUser() {
        Setup();
        LoginRequest req = new LoginRequest();
        req.setEmail("Mail");
        req.setPassword("Pass");
        LoginService service = new LoginService(dao);
        try {
            service.login(req);
        } catch (WrongLoginParametersException e) {
            assert true == false;
        }
    }

    @Test
    public void TestLoginWrongPassword() {
        Setup();
        LoginRequest req = new LoginRequest();
        req.setEmail("Mail");
        req.setPassword("Wrong");
        LoginService service = new LoginService(dao);
        try {
            service.login(req);
            assert true == false;
        } catch (WrongLoginParametersException e) {
            assert true == true;
        }
    }

    @Test
    public void TestLoginWrongMail() {
        Setup();
        LoginRequest req = new LoginRequest();
        req.setEmail("Wrong");
        req.setPassword("Pass");
        LoginService service = new LoginService(dao);
        try {
            service.login(req);
            assert true == false;
        } catch (WrongLoginParametersException e) {
            assert true == true;
        }
    }
}
