package ronan_hanley.dist_sys.user_account_service.representations;

import javax.validation.constraints.NotNull;

public class User {
    @NotNull
    private UserDetails userDetails;

    @NotNull
    private String password;

    public User(UserDetails userDetails, String password) {
        this.userDetails = userDetails;
        this.password = password;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
