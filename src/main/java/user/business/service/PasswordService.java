package user.business.service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class PasswordService {

    private static final String algorithm = "PBKDF2WithHmacSHA512";

    private byte[] salt;

    public PasswordService() {
        newSalt();
    }

    public PasswordService(String salt) {
        this.salt = Base64.getDecoder().decode(salt);
    }

    public String getSalt() {
        return Base64.getEncoder().encodeToString(salt);
    }

    private void newSalt() {
        SecureRandom random = new SecureRandom();
        salt = new byte[16];
        random.nextBytes(salt);
    }

    public String createPassword(String password) {
        return Base64.getEncoder().encodeToString(createPasswordFromSalt(password));
    }

    public String createPasswordWithNewSalt(String password) {
        newSalt();
        return createPassword(password);
    }

    private byte[] createPasswordFromSalt(String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        byte[] hash = null;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
            hash = factory.generateSecret(spec).getEncoded();
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        return hash;
    }

    public boolean validatePassword(String password, String hashedPassword) {
        return Arrays.equals(Base64.getDecoder().decode(hashedPassword), createPasswordFromSalt(password));
    }

    public boolean validatePassword(String password, String hashedPassword, String salt) {
        this.salt = Base64.getDecoder().decode(salt);
        return Arrays.equals(Base64.getDecoder().decode(hashedPassword), createPasswordFromSalt(password));
    }

}
