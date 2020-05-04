package user.business.service.response;

import user.business.entity.Phone;
import user.business.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    private String uid;
    private String name;
    private String email;
    private String password;
    private String salt;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private List<PhoneResponse> phones;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.setUid(user.getUid());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setSalt(user.getSalt());
        this.setCreated(user.getCreated());
        this.setModified(user.getModified());
        this.setLastLogin(user.getLastLogin());
        this.setToken(user.getToken());
        this.setPhones(user.getPhones());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<PhoneResponse> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        List<PhoneResponse> phoneResponseList = new ArrayList<>();
        if(phones != null) {
            for(Phone p : phones) {
                PhoneResponse phoneModel = new PhoneResponse();
                phoneModel.setDdd(p.getDdd());
                phoneModel.setNumber(p.getNumber());
                phoneResponseList.add(phoneModel);
            }
        }
        this.phones = phoneResponseList;
    }
}
