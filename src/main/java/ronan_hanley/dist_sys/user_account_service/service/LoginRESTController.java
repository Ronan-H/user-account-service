package ronan_hanley.dist_sys.user_account_service.service;

import ronan_hanley.dist_sys.user_account_service.representations.NewUser;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/login")
public class LoginRESTController {
    private UserManager userManager;

    public LoginRESTController(UserManager userManager) {
        this.userManager = userManager;
    }

    @POST
    public Response login(NewUser loginUser) {
        if (loginUser == null) {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad request, user can't be null").build();
        }

        if (!userManager.isValidUser(loginUser)) {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Login failed, user with those exact details does not exist (wrong password?)").build();
        }

        // user exists and login is valid
        return Response.ok().entity("Login successful").build();
    }
}
