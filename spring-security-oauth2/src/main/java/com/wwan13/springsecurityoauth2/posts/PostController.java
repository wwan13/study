package com.wwan13.springsecurityoauth2.posts;

import com.wwan13.springsecurityoauth2.accounts.Account;
import com.wwan13.springsecurityoauth2.accounts.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity createPost(@RequestBody PostDto postDto, @CurrentUser Account currentUser) {
        Post post = this.postService.createPost(postDto, currentUser);
        URI createdUri = linkTo(methodOn(PostController.class).createPost(postDto, currentUser)).slash(post.getId()).toUri();

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

    @PutMapping(value = "/{id}")
    public ResponseEntity updatePost(@PathVariable Integer id, @RequestBody PostDto postDto ,@CurrentUser Account account) {

        if (this.postService.getPostManager(id).equals(account)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Post updatedPost = this.postService.updatePost(id, postDto);

        return ResponseEntity.ok().body(updatedPost);
    }

}
