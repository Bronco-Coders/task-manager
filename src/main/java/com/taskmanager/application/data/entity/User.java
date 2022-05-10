package com.taskmanager.application.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taskmanager.application.data.Role;

import java.io.Serializable;
import java.util.Set;
public class User extends AbstractEntity implements Serializable {

    private String username;
    private String name;
    private String password;
    @JsonIgnore
    private String hashedPassword;
    // @Enumerated(EnumType.STRING)
    // @ElementCollection(fetch = FetchType.EAGER)
    // private Set<Role> roles;
    // @Lob
    // private String profilePictureUrl;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return hashedPassword;
    }
    public void setPassword(String password) {
        this.hashedPassword = password;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    // public Set<Role> getRoles() {
    //     return roles;
    // }
    // public void setRoles(Set<Role> roles) {
    //     this.roles = roles;
    // }
    // public String getProfilePictureUrl() {
    //     return profilePictureUrl;
    // }
    // public void setProfilePictureUrl(String profilePictureUrl) {
    //     this.profilePictureUrl = profilePictureUrl;
    // }

}
