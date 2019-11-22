package ronan_hanley.dist_sys.user_account_service.service;

import ronan_hanley.dist_sys.user_account_service.representations.NewUser;
import ronan_hanley.dist_sys.user_account_service.representations.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
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
    public Response createUser(NewUser newUser) throws URISyntaxException {
        if (newUser == null) {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad request, new user can't be null").build();
        }

        if (userManager.userExists(newUser.getUserDetails().getUserId())) {
            // 409 conflict
            return Response.status(Response.Status.CONFLICT).entity("Conflict, user with that id already exists").build();
        }

        // user isn't null and doesn't already exist, create user asynchronously
        userManager.createUserAsync(newUser);
        return Response.created(new URI("/users/" + newUser.getUserDetails().getUserId())).entity("User created successfully").build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Integer id) {
        if (!userManager.userExists(id)) {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND).entity("User not found with that id").build();
        }

        // user exists, respond with user details
        return Response.ok(userManager.getUser(id)).build();
    }

    @GET
    public Response getUsers() {
        // nothing to validate here, just respond with a list of all users
        List<User> users = userManager.getAllUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.ok(entity).build();
    }

    @PUT
    public Response updateUser(NewUser updatedUser) {
        if (updatedUser == null) {
            // 400 bad request
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad request, updated user can't be null").build();
        }

        if (!userManager.userExists(updatedUser.getUserDetails().getUserId())) {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND).entity("User not found with that id").build();
        }

        // user exists, update user
        userManager.updateUser(updatedUser);
        return Response.ok().entity("User updated successfully").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Integer id) {
        if (!userManager.userExists(id)) {
            // 404 user not found
            return Response.status(Response.Status.NOT_FOUND).entity("User not found with that id").build();
        }

        // user exists, delete user
        userManager.deleteUser(id);
        return Response.ok().entity("User deleted successfully").build();
    }
}