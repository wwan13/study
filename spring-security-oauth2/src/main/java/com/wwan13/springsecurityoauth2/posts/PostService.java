package com.wwan13.springsecurityoauth2.posts;

import com.wwan13.springsecurityoauth2.accounts.Account;
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

    public Post createPost(PostDto postDto, Account account) {

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setCreatedAt(LocalDateTime.now());
        post.setManager(account);

        return this.postRepository.save(post);

    }

    public List<Post> findAllPost() {
        return this.postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
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
