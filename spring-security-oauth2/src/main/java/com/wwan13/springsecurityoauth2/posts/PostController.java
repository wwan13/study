package com.wwan13.springsecurityoauth2.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDto postDto) {
        Post post = this.postService.createPost(postDto);
        URI createdUri = linkTo(methodOn(PostController.class).createPost(postDto)).slash(post.getId()).toUri();

        return ResponseEntity.created(createdUri).body(post);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getPost(@PathVariable Integer id) {
        Post post = this.postService.findPostById(id);

        return ResponseEntity.ok().body(post);
    }

    @GetMapping
    public ResponseEntity getAllPosts() {
        List<Post> posts = this.postService.findAllPost();

        return ResponseEntity.ok().body(posts);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletePost(@PathVariable Integer id) {
        this.postService.deletePostById(id);

        return ResponseEntity.ok().body("Delete Complete");
    }

}
