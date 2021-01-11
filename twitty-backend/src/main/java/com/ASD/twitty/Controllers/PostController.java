package com.ASD.twitty.Controllers;

import com.ASD.twitty.Beans.PostRequest;
import com.ASD.twitty.Entities.Comment;
import com.ASD.twitty.Entities.Post;
import com.ASD.twitty.Entities.User;
import com.ASD.twitty.Repository.PostRepository;
import com.ASD.twitty.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public List<Post> getPosts(){ return postRepository.findAll(); }

    @PostMapping("/save")
    public ResponseEntity<?> saveOrEdit(@RequestBody PostRequest form)
    {
        boolean isNew = form.getId() == null;
        Post posts = new Post(form.getId(),
                form.getContent(),
                userRepository.findById(form.getUserId()).get());

        Post post = postRepository.save(posts);
        Map<String,Object> response = new HashMap<>();
        response.put("generatedId",post.getId());
        if(isNew)
        {
            response.put("message","Успешно добавен");
        }
        else {
            response.put("message","Успешно редактиран");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePost(@RequestParam Long id)
    {
        postRepository.findPostById(id).ifPresent(post ->postRepository.delete(post));
        return ResponseEntity.ok("Успешно изтрит");
    }

    @GetMapping("/comments")
    public ResponseEntity<?> getCommentsOfPosts(@RequestParam Long id) {

        Set<Comment> result = postRepository.findCommentsOfPosts(id);

        Map<String, Object> response = new HashMap<>();
        response.put("comments", result);
        response.put("numberOfComments", result.size());
        response.put("message", result.isEmpty() ? "No comments found" : "Found " + result.size() + " comments");

        return ResponseEntity.ok().body(response);
    }
}
