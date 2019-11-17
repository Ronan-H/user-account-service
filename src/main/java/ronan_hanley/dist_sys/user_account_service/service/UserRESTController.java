package ronan_hanley.dist_sys.user_account_service.service;

import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.representations.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/users")
@Produces({ "application/json", "application/xml" })
@Consumes({ "application/json", "application/xml" })
public class UserRESTController {
    private UserManager userManager;

    public UserRESTController(UserManager userManager) {
        this.userManager = userManager;
    }

    @POST
    public Response createUser(NewUser newUser) {
        if (newUser != null && !userManager.userExists(newUser.getUserDetails().getUserId())) {
            userManager.createUserAsync(newUser);
            return Response.ok().build();
        }
        else {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Integer id) {
        if (userManager.userExists(id)) {
            return Response.ok(userManager.getUser(id)).build();
        }
        else {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response getUsers() {
        List<User> users = userManager.getAllUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.ok(entity).build();
    }

    @PUT
    public Response updateUser(NewUser updatedUser) {
        if (updatedUser == null) {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (userManager.userExists(updatedUser.getUserDetails().getUserId())) {
            userManager.updateUser(updatedUser);
            return Response.ok().build();
        }
        else {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Integer id) {
        if (userManager.userExists(id)) {
            userManager.deleteUser(id);
            return Response.ok().build();
        }
        else {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
