package ronan_hanley.dist_sys.user_account_service;

import com.beust.jcommander.Parameter;

public class CLIArgs {
    @Parameter(
            names = {"--usage", "-u"},
            description = "Print usage instructions and exit"
    )
    Boolean printUsage = false;

    @Parameter(
            names = {"--host", "-h"},
            description = "IP address/domain of gRPC password service host"
    )
    String host = "127.0.0.1";

    @Parameter(
            names = {"--port", "-p"},
            description = "Port of gRPC password service host"
    )
    Integer port = 50051;
}
