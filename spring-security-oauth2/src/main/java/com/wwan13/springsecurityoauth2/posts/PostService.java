package com.wwan13.springsecurityoauth2.posts;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public Post createPost(PostDto postDto) {

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setCreatedAt(LocalDateTime.now());

        // user

        return this.postRepository.save(post);

    }

    public List<Post> findAllPost() {
        return this.postRepository.findAll(Sort.by(Sort.Direction.DESC, "created_at"));
    }

    public Post findPostById(Integer id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new NullPointerException());

        return post;
    }

    public void deletePostById(Integer id) {
        this.postRepository.deleteById(id);
    }

    public void deleteAllPosts() {
        this.postRepository.deleteAll();
    }

    public void update(Post post) {
        post.setCreatedAt(LocalDateTime.now());
    }

}
