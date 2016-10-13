package com.woact.dolplads;

import com.woact.dolplads.annotations.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.inject.Model;
import java.io.Serializable;

/**
 * Created by dolplads on 02/10/16.
 * LoginCredentials
 */
@Model
public class CredentialsController implements Serializable {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
