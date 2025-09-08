package th.mfu.boot;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserRepository repo;

    // Register a new user
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Check if username exists
        if (repo.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Username already exists");
        }
        // Save user
        repo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body("User registered");
    }

    // List all users
    @GetMapping
    public ResponseEntity<List<User>> list() {
        List<User> users = repo.findAll();
        return ResponseEntity.ok(users);
    }

    // Delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("User not found");
        }
        repo.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }
}


