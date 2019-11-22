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

    public UserDetails() {}

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return userId.equals(that.userId) &&
                userName.equals(that.userName) &&
                email.equals(that.email);
    }
}
