package ronan_hanley.dist_sys.user_account_service.representations;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="newUser")
public class NewUser {
    @NotNull
    private UserDetails userDetails;

    @NotNull
    private String password;

    public NewUser(UserDetails userDetails, String password) {
        this.userDetails = userDetails;
        this.password = password;
    }

    public NewUser() {}

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("(id = %d, user = %s, email: %s, pass = %s)",
                getUserDetails().getUserId(),
                getUserDetails().getUserName(),
                getUserDetails().getEmail(),
                getPassword());
    }
}
