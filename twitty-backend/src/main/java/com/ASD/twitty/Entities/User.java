package com.ASD.twitty.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name= "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="active")
    private boolean active;

    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name="password")
    private String password;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "followers",
        joinColumns = @JoinColumn(name = "following"),
        inverseJoinColumns = @JoinColumn(name = "followed"))
    private Set<User> following = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private Set<Post> posts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User(Long id, String username, String password,boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active=active;
    }

    public User() {
    }
}
