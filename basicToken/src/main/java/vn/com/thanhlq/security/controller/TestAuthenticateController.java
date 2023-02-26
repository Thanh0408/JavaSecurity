package vn.com.thanhlq.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAuthenticateController {

    @GetMapping("/admin/myAdminPage")
    public ResponseEntity<String> getAdminPage() {
        return ResponseEntity.ok("AdminPage");
    }

    @GetMapping("/api/v1/general")
    public ResponseEntity<String> getUserPage() {
        return ResponseEntity.ok("UserPage");
    }

    @GetMapping("/public/gate")
    public ResponseEntity<String> getPublicPage() {
        return ResponseEntity.ok("PublicPage");
    }

}
