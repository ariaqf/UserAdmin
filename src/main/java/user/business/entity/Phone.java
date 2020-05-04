package user.business.entity;

import java.util.UUID;

public class Phone {
    private Long id;
    private String uid;
    private String number;
    private String ddd;

    public Phone() {
        UUID uuid = UUID.randomUUID();
        uid = uuid.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String id) {
        this.uid = uid;
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
