// 代码生成时间: 2025-09-23 01:02:27
import play.mvc.*;
import play.libs.Json;
import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Model class to represent a User entity
 */
@Entity
public class User extends Model {
    @Id
    public Long id;
    public String name;
    public String email;

    // Standard getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

/**
 * Controller class for handling User API requests
 */
public class UserController extends Controller {
    
    /**
     * GET endpoint to fetch all users
     * @return A JSON response containing a list of all users
     */
    public static Result getAllUsers() {
        List<User> users = User.find.all();
        return ok(Json.toJson(users));
    }

    /**
     * POST endpoint to create a new user
     * @return A JSON response containing the created user or an error
     */
    public static Result createUser() {
        JsonNode jsonNode = request().body().asJson();
        if (jsonNode == null) {
            return badRequest("Expected JSON data");
        }
        User user = new User();
        user.name = jsonNode.get("name").textValue();
        user.email = jsonNode.get("email").textValue();
        try {
            user.save();
            return created(Json.toJson(user));
        } catch (Exception e) {
            return internalServerError("Error while creating user");
        }
    }

    /**
     * GET endpoint to fetch a user by ID
     * @param id The ID of the user to fetch
     * @return A JSON response containing the user or an error
     */
    public static Result getUser(Long id) {
        User user = User.find.byId(id);
        if (user == null) {
            return notFound("User not found");
        }
        return ok(Json.toJson(user));
    }

    /**
     * PUT endpoint to update an existing user
     * @param id The ID of the user to update
     * @return A JSON response containing the updated user or an error
     */
    public static Result updateUser(Long id) {
        JsonNode jsonNode = request().body().asJson();
        if (jsonNode == null) {
            return badRequest("Expected JSON data");
        }
        User user = User.find.byId(id);
        if (user == null) {
            return notFound("User not found");
        }
        user.name = jsonNode.get("name").textValue();
        user.email = jsonNode.get("email").textValue();
        try {
            user.update();
            return ok(Json.toJson(user));
        } catch (Exception e) {
            return internalServerError("Error while updating user");
        }
    }

    /**
     * DELETE endpoint to delete a user by ID
     * @param id The ID of the user to delete
     * @return A JSON response indicating success or failure
     */
    public static Result deleteUser(Long id) {
        User user = User.find.byId(id);
        if (user == null) {
            return notFound("User not found");
        }
        try {
            user.delete();
            return ok("User deleted");
        } catch (Exception e) {
            return internalServerError("Error while deleting user");
        }
    }
}