package ronan_hanley.dist_sys.user_account_service.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import ronan_hanley.dist_sys.user_account_service.proto.*;
import ronan_hanley.dist_sys.user_account_service.representations.HashPairRep;
import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import java.util.concurrent.TimeUnit;

public class PasswordServiceClient {
    private final ManagedChannel channel;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub clientStub;
    private final PasswordServiceGrpc.PasswordServiceStub asyncClientStub;

    /** Construct client for accessing password service server using the existing channel. */
    public PasswordServiceClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                // no TLS encryption
                .usePlaintext()
                .build();

        // create stubs (sync and async)
        clientStub = PasswordServiceGrpc.newBlockingStub(channel);
        asyncClientStub = PasswordServiceGrpc.newStub(channel);
    }

    public void generateHashPairAsync(NewUser newUser, StreamObserver<HashResponse> responseObserver) throws StatusRuntimeException {
        // build hash request
        HashRequest hashRequest = HashRequest.newBuilder()
                .setUserId(newUser.getUserDetails().getUserId())
                .setPassword(newUser.getPassword())
        .build();

        // asynchronous gRPC call
        // (caller of generateHashPairAsync() will receive the hashResponse through the passed in StreamObserver)
        asyncClientStub.hash(hashRequest, responseObserver);
    }

    public boolean verifyPassword(String pass, HashPairRep hashPairRep) throws StatusRuntimeException {
        // build validation request
        ValidateRequest validateRequest = ValidateRequest.newBuilder()
                .setPassword(pass)
                .setHashPair(hashPairRep.toHashPairGRPC()
         ).build();

        ValidateResponse validateResponse;

        // synchronous gRPC call
        validateResponse = clientStub.validate(validateRequest);

        return validateResponse.getValid();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
