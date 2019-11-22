package ronan_hanley.dist_sys.user_account_service.representations;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
public class User {
    @NotNull
    private UserDetails userDetails;

    @NotNull
    private HashPairRep hashPairRep;

    public User(UserDetails userDetails, HashPairRep hashPairRep) {
        this.userDetails = userDetails;
        this.hashPairRep = hashPairRep;
    }

    public User() {}

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public HashPairRep getHashPairRep() {
        return hashPairRep;
    }
}
