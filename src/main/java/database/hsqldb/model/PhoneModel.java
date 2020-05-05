package database.hsqldb.model;

import javax.persistence.*;

@Entity
@Table(name= "phones")
@IdClass(PhoneID.class)
public class PhoneModel {
    @Id
    private Long user;
    @Id
    private String number;
    @Id
    private String ddd;

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }
}
