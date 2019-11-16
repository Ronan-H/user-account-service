package ronan_hanley.dist_sys.user_account_service;

import com.beust.jcommander.Parameter;

public class CLIArgs {
    @Parameter(
            names = {"--usage", "-u"},
            description = "Print usage instructions and exit"
    )
    Boolean printUsage = false;

    @Parameter(
            names = {"--port", "-p"},
            description = "Port to host the server on"
    )
    Integer port = 50051;
}
