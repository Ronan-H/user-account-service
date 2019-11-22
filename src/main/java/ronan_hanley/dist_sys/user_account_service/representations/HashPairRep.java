package ronan_hanley.dist_sys.user_account_service.representations;

import com.google.protobuf.ByteString;
import ronan_hanley.dist_sys.user_account_service.proto.HashPair;

import javax.validation.constraints.NotNull;

public class HashPairRep {
    @NotNull
    private byte[] hashedPassword;

    @NotNull
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

    public byte[] getSalt() {
        return salt;
    }
}
