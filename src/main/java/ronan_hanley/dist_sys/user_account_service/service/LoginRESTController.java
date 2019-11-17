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
        if (loginUser != null && userManager.isValidUser(loginUser)) {
            // user exists and login is valid
            return Response.ok().build();
        }
        else {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
