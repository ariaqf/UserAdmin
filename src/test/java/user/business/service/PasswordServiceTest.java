package user.business.service;

import org.junit.Test;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class PasswordServiceTest {

    @Test
    public void Create() {
        String password = "Pass123";
        PasswordService passService = new PasswordService();
        String salt = passService.getSalt();
        assert salt != null;
        String hash =  passService.createPassword(password);
        assert hash != null;
    }

    @Test
    public void CreateWithSalt() {
        String password = "Pass123";
        SecureRandom sec = new SecureRandom();
        byte[] saltBytes =  new byte[16];
        sec.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        PasswordService passService = new PasswordService(salt);
        boolean saltEquals = passService.getSalt().equals(salt);
        assert saltEquals == true;
        String hash =  passService.createPassword(password);
        assert hash != null;
    }

    @Test
    public void PasswordValidate() {
        String password = "Pass123";
        PasswordService passService = new PasswordService();
        String hash = passService.createPassword(password);
        String salt = passService.getSalt();
        PasswordService validateService = new PasswordService(salt);
        boolean hashEquals = validateService.createPassword(password).equals(hash);
        assert validateService.validatePassword(password, hash) == true;
        assert validateService.validatePassword(password, hash, salt) == true;
        assert hashEquals == true;
    }
}
