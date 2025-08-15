package jp.sayama.sns.gametalk.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.sayama.sns.gametalk.model.Post;
import jp.sayama.sns.gametalk.repository.mybatis.PostMapper;

@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    public void createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        postMapper.insert(post);
    }

    public List<Post> getAllPosts() {
        return postMapper.findAll();
    }
}
