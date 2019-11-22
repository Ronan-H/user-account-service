package ronan_hanley.dist_sys.user_account_service.service;

import io.grpc.StatusRuntimeException;
import ronan_hanley.dist_sys.user_account_service.representations.LoginUser;
import ronan_hanley.dist_sys.user_account_service.representations.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/login")
public class LoginRESTController {
    private UserManager userManager;

    public LoginRESTController(UserManager userManager) {
        this.userManager = userManager;
    }

    @POST
    public Response login(LoginUser loginUser) {
        if (loginUser == null) {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad request, login user can't be null").build();
        }

        User dbUser = userManager.findUserByUsername(loginUser.getUserName());

        if (dbUser == null) {
            // user not found with that userName
            // 401 unauthorized
            return Response.status(Response.Status.UNAUTHORIZED).entity("Login failed, user not found with that username").build();
        }

        try {
            if (!userManager.loginPasswordMatchesUser(loginUser, dbUser)) {
                // password incorrect
                // 401 unauthorized
                return Response.status(Response.Status.BAD_REQUEST).entity("Login failed, password incorrect").build();
            }
        } catch (StatusRuntimeException e) {
            // gRPC failed
            // 503 service unavailable
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("Failed to log in, password validation service was not available. Try again later.").build();
        }

        // user exists and login is valid
        return Response.ok().entity("Login successful").build();
    }
}
