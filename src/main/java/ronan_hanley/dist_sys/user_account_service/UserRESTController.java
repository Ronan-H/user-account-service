package ronan_hanley.dist_sys.user_account_service;

import ronan_hanley.dist_sys.user_account_service.representations.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/users")
public class UserRESTController {
    private UserDB userDB;

    public UserRESTController(UserDB userDB) {
        this.userDB = userDB;
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Integer id) {
        User user = userDB.getUser(id);
        if (user != null) {
            return Response.ok(user).build();
        }
        else {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
