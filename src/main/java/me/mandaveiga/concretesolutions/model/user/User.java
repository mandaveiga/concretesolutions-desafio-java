package me.mandaveiga.concretesolutions.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.mandaveiga.concretesolutions.model.AbstractModel;
import me.mandaveiga.concretesolutions.model.user.phone.Phone;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends AbstractModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String token;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private Set<Phone> phones;

    @Column(nullable = false)
    private Date lastLogin;

    public User() {
        this.phones = new HashSet<>();
        this.lastLogin = new Date();
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = new HashSet<>();
        this.lastLogin = new Date();
    }
}