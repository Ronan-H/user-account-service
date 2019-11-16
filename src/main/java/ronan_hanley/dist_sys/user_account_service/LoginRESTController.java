package ronan_hanley.dist_sys.user_account_service;

import ronan_hanley.dist_sys.user_account_service.representations.NewUser;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/login")
public class LoginRESTController {
    private UserDB userDB;

    public LoginRESTController(UserDB userDB) {
        this.userDB = userDB;
    }

    @POST
    @Produces({ "application/json", "application/xml" })
    public Response login(NewUser loginUser) {
        if (loginUser != null && userDB.isValidUser(loginUser)) {
            return Response.ok().build();
        }
        else {
            // 405 bad request
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}