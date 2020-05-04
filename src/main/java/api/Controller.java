package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import user.business.service.CreateService;
import user.business.service.LoginService;
import user.business.service.RetrieveService;
import user.business.service.exceptions.InvalidCredentialException;
import user.business.service.exceptions.SessionTimeoutException;
import user.business.service.exceptions.UserAlreadyExistsException;
import user.business.service.exceptions.WrongLoginParametersException;
import user.business.service.request.CreateRequest;
import user.business.service.request.LoginRequest;
import user.business.service.response.UserResponse;
import user.dao.UserDAO;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    UserDAO userDAO;

    @PostMapping("/user/create")
    public ResponseEntity createUser(RequestEntity<CreateRequest> request) {
        ResponseEntity response;
        CreateService service = new CreateService(userDAO);
        try {
            UserResponse u = service.CreateUser(request.getBody());
            response = ResponseEntity.status(201).body(u);
        } catch (UserAlreadyExistsException e) {
            response = ResponseEntity.status(403).body(new ErrorResponse(e.getMessage()));
        }
        return response;
    }

    @PostMapping("/user/login")
    public ResponseEntity login(RequestEntity<LoginRequest> request) {
        ResponseEntity response;
        LoginService service = new LoginService(userDAO);
        try {
            UserResponse u = service.login(request.getBody());
            response = ResponseEntity.status(201).body(u);
        } catch (WrongLoginParametersException e) {
            response = ResponseEntity.status(401).body(new ErrorResponse(e.getMessage()));
        }
        return response;
    }

    @GetMapping("/user/<uid>")
    public ResponseEntity retrieve(@PathParam("uid") String uid, RequestEntity req) {
        ResponseEntity response;
        RetrieveService service = new RetrieveService(userDAO);
        List<String> headers = req.getHeaders().get("Authorization");
        if(headers.size() == 0) {
            response = ResponseEntity.status(401).body(new ErrorResponse("Sem token"));
        } else {
            try {
                String token = headers.get(0).split(" ")[1];
                UserResponse u = service.getUser(uid, token);
                response = ResponseEntity.status(201).body(u);
            } catch (SessionTimeoutException e) {
                response = ResponseEntity.status(401).header("WWW-Authenticate", "Token", "realm=\"Session timed out\"").body(new ErrorResponse(e.getMessage()));
            } catch (InvalidCredentialException e) {
                response = ResponseEntity.status(401).header("WWW-Authenticate", "Token", "realm=\"Credentials are invalid\"").body(new ErrorResponse(e.getMessage()));
            }
        }
        return response;
    }
}
