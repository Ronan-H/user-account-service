package ronan_hanley.dist_sys.user_account_service.representations;

import com.google.protobuf.ByteString;
import org.hibernate.validator.constraints.NotEmpty;
import ronan_hanley.dist_sys.user_account_service.proto.HashPair;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"hashedPassword", "salt"})
public class HashPairRep {
    @NotEmpty
    private byte[] hashedPassword;

    @NotEmpty
    private byte[] salt;

    public HashPairRep(byte[] hashedPassword, byte[] salt) {
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public HashPairRep(HashPair grpcHashPair) {
        this(grpcHashPair.getHash().toByteArray(), grpcHashPair.getSalt().toByteArray());
    }

    public HashPairRep() {}

    public HashPair toHashPairGRPC() {
        return HashPair.newBuilder()
                .setHash(ByteString.copyFrom(hashedPassword))
                .setSalt(ByteString.copyFrom(salt))
        .build();
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
