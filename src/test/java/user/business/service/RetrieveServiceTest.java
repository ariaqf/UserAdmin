package user.business.service;

import database.mock.user.dao.UserDAOImpl;
import org.junit.Test;
import user.business.entity.User;
import user.business.service.exceptions.InvalidCredentialException;
import user.business.service.exceptions.SessionTimeoutException;
import user.business.service.exceptions.WrongLoginParametersException;
import user.business.service.request.CreateRequest;
import user.business.service.request.LoginRequest;
import user.business.service.response.UserResponse;
import user.dao.UserDAO;
import user.dao.exceptions.UserNotFoundException;

import java.time.LocalDateTime;

public class RetrieveServiceTest {
    UserDAO dao;
    UserResponse user;
    UserResponse user2;

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
        req.setEmail("Mail2");
        try {
            user2 = service.CreateUser(req);
        } catch (Exception e) {}
    }

    @Test
    public void TestRetrieveExistingUser() {
        Setup();
        RetrieveService service = new RetrieveService(dao);
        try {
            service.getUser(user.getUid(), user.getToken());
        } catch (SessionTimeoutException e) {
            assert true == false;
        } catch (InvalidCredentialException e) {
            assert true == false;
        }
    }

    @Test
    public void TestRetrieveWrongUidUser() {
        Setup();
        RetrieveService service = new RetrieveService(dao);
        try {
            service.getUser("Wrong", user.getToken());
            assert true == false;
        } catch (SessionTimeoutException e) {
            assert true == false;
        } catch (InvalidCredentialException e) {

        }
    }

    @Test
    public void TestRetrieveExistingUserWrongToken() {
        Setup();
        RetrieveService service = new RetrieveService(dao);
        try {
            service.getUser(user.getUid(), "Wrong");
            assert true == false;
        } catch (SessionTimeoutException e) {
            assert true == false;
        } catch (InvalidCredentialException e) {
            
        }
    }

    @Test
    public void TestRetrieveUserTokenMismatch() {
        Setup();
        RetrieveService service = new RetrieveService(dao);
        try {
            service.getUser(user.getUid(), user2.getToken());
            assert true == false;
        } catch (SessionTimeoutException e) {
            assert true == false;
        } catch (InvalidCredentialException e) {
            
        }
    }

    @Test
    public void TestRetrieveUserTimeout() {
        Setup();
        RetrieveService service = new RetrieveService(dao);
        try {
            User u = dao.getUserByUid(user.getUid());
            u.setLastLogin(LocalDateTime.now().minusMinutes(40));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        try {
            service.getUser(user.getUid(), user.getToken());
            assert true == false;
        } catch (SessionTimeoutException e) {

        } catch (InvalidCredentialException e) {
            assert true == false;
        }
    }
}
