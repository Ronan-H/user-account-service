package ronan_hanley.dist_sys.user_account_service;

import com.codahale.metrics.health.HealthCheck;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import ronan_hanley.dist_sys.user_account_service.proto.*;

public class GRPCHealthCheck extends HealthCheck {
    private String host;
    private int port;

    public GRPCHealthCheck(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    protected Result check() throws Exception {
        PasswordServiceGrpc.PasswordServiceBlockingStub clientStub;
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                // no TLS encryption
                .usePlaintext()
                .build();

        clientStub = PasswordServiceGrpc.newBlockingStub(channel);

        String pass = "password123";
        // build hash request
        HashRequest hashRequest = HashRequest.newBuilder()
                .setUserId(0)
                .setPassword(pass)
                .build();

        HashResponse hashResponse;
        try {
            // get response
            hashResponse = clientStub.hash(hashRequest);
        } catch (StatusRuntimeException e) {
            return Result.unhealthy("gRPC hash test failed");
        }

        ByteString hash = hashResponse.getHashPair().getHash();
        ByteString salt = hashResponse.getHashPair().getSalt();

        // build validation request
        ValidateRequest validateRequest = ValidateRequest.newBuilder()
                .setPassword(pass)
                .setHashPair(HashPair.newBuilder()
                        .setHash(hash)
                        .setSalt(salt)
                ).build();

        ValidateResponse validateResponse;
        try {
            validateResponse = clientStub.validate(validateRequest);
        } catch (StatusRuntimeException e) {
            return Result.unhealthy("gRPC validate test failed");
        }

        if (!validateResponse.getValid()) {
            return Result.unhealthy("Hash validation was incorrect");
        }

        return Result.healthy();
    }
}