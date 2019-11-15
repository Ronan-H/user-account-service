package ronan_hanley.dist_sys.user_account_service.representations;

import javax.validation.constraints.NotNull;

public class UserDetails {
    @NotNull
    private Integer userId;

    @NotNull
    private String userName;

    @NotNull
    private String email;

    public UserDetails(Integer userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
