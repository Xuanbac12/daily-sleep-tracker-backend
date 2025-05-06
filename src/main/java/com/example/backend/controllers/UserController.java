package com.example.backend.controllers;

import com.example.backend.models.User;
import com.example.backend.services.UserService;
import com.example.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Regex kiểm tra mật khẩu mạnh (ít nhất 8 ký tự, có chữ hoa, thường, số, ký tự đặc biệt)
    private static final Pattern STRONG_PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_\\-])[A-Za-z\\d@$!%*?&_\\-]{8,}$"


    );
    //Regex kiểm tra tên usernam
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{2,20}$");

    private boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    //Hàm riêng để kiểm tra password mạnh
    private boolean isStrongPassword(String password){
        return STRONG_PASSWORD_PATTERN.matcher(password).matches();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        // ✅ Kiểm tra rỗng
        if (user.getUsername() == null || user.getUsername().isBlank() ||
                user.getPassword() == null || user.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tên đăng ký và mật khẩu không được để trống.");
        }

        // ✅ Kiểm tra định dạng username (regex: chỉ chữ, số, gạch dưới, dài 4–20 ký tự)
        if (!isValidUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tên đăng ký phải từ 2–20 ký tự, không chứa ký tự đặc biệt.");
        }


        // ✅ Kiểm tra username đã tồn tại chưa
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Tên đăng nhập đã tồn tại.");
        }

        // ✅ Kiểm tra mật khẩu đủ mạnh
        if (!isStrongPassword(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt.");
        }

        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Đăng ký thành công");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User found = userService.login(user.getUsername(), user.getPassword());

        if (found != null) {
            String token = jwtUtil.generateToken(found.getUsername());
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "username", found.getUsername(),
                    "userId", found.getId(),
                    "firstLogin", found.isFirstLogin()
            ));
        }

        //log.warn("⚠️ Đăng nhập thất bại - Tên đăng nhập hoặc mật khẩu không đúng: [{}]", user.getUsername());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Tên đăng nhập hoặc mật khẩu không đúng"));
    }

    @PutMapping("/set-first-login-false/{id}")
    public ResponseEntity<?> setFirstLoginFalse(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setFirstLogin(false);
            userService.register(u); // Lưu lại
            return ResponseEntity.ok("updated");
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/by-username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}


