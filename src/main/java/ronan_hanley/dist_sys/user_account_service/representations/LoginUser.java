package ronan_hanley.dist_sys.user_account_service.representations;

import org.hibernate.validator.constraints.NotEmpty;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="loginUser")
@XmlType(propOrder={"userName", "password"})
public class LoginUser {
    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;

    public LoginUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginUser() {}

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
