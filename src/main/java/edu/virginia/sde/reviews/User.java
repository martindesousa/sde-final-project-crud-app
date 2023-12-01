package edu.virginia.sde.reviews;

import jakarta.persistence.*;
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean conflictsWith(User user) {
        if (user.getUsername().equals(this.username))
        {
            return true;
        }
        return false;

    }
}

