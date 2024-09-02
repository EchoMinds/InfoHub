package ru.echominds.infohub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class test {
    @PostMapping("/community")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createCommunity(@RequestParam String name) {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/community/{communityId}/post")
    // Must have CommunityRoleType.MODERATOR role
    public ResponseEntity<?> createPost(@PathVariable Long communityId, @RequestParam String name) {
        return ResponseEntity.ok("hello");
    }

    @PutMapping("/post/{postId}")
    // Must have PostRoleType.EDITOR
    public void updatePost(@PathVariable Long postId, @RequestParam String name) {
        ResponseEntity.ok("hello");
    }

    @GetMapping("/post/{postId}")
    // Must have PostRoleType.VIEWER role
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok("hello");
    }
}
