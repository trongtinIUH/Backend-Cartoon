package flim.backendcartoon.controllers;

import flim.backendcartoon.entities.User;
import flim.backendcartoon.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserServices userService;

    @Autowired
    public UserController(UserServices userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (user.getUserName() == null || user.getPhoneNumber() == null ) {
            return ResponseEntity.badRequest().body("Thông tin người dùng không hợp lệ");
        }

        user.setUserId(UUID.randomUUID().toString());

        userService.createUser(user);
        return ResponseEntity.ok("User created successfully!");
    }

    @PostMapping("/findByPhoneNumber")
    public ResponseEntity<?> findUserByPhoneNumber(@RequestBody Map<String, String> payload) {
        String phoneNumber = payload.get("phoneNumber");
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Số điện thoại không được để trống");
        }

        User user = userService.findUserByPhoneNumber(phoneNumber);
        if (user == null) {
            System.out.println("Không tìm thấy người dùng với số điện thoại: " + phoneNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng với số điện thoại: " + phoneNumber);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 nếu danh sách rỗng
        }
        return ResponseEntity.ok(users); // HTTP 200 và trả về danh sách người dùng
    }

    @GetMapping("/searchFriend")
    public ResponseEntity<?> searchUser(@RequestParam String phoneNumber) {
        try {
            User user = userService.findUserByPhoneNumber(phoneNumber); // Xử lý tìm kiếm
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();  // In chi tiết lỗi ra log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody Map<String, String> payload) {
        try {
            String newName = payload.get("userName");


            User user = userService.findUserById_ttt(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
            }

            if (newName != null && !newName.trim().isEmpty()) {
                user.setUserName(newName);
            }
            userService.createUser(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
        }
    }

}
