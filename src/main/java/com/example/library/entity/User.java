package com.example.library.entity;

import lombok.Data;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class User {
    @MongoId
    private String userId;
    private String username;
    private String passwordSalt;
    private String passwordHash;
    private Role role;

    public User() {
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.role = role;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
    }

    public boolean chceckPassword(String password)
    {
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
