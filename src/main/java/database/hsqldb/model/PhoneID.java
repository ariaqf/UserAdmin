package database.hsqldb.model;

import java.io.Serializable;
import java.util.Objects;

public class PhoneID implements Serializable {
    private Long user;
    private String number;
    private String ddd;

    public PhoneID() {
    }

    public PhoneID(Long user, String number, String ddd) {
        this.user = user;
        this.number = number;
        this.ddd = ddd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneID phoneID = (PhoneID) o;
        return Objects.equals(user, phoneID.user) &&
                Objects.equals(number, phoneID.number) &&
                Objects.equals(ddd, phoneID.ddd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, number, ddd);
    }
}
