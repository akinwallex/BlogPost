/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.akinwale.blogpost.controller;

import com.akinwale.blogpost.model.Post;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp 1030 g2
 */
@RestController
@RequestMapping(value = "/post")
public class PostResource {
    
    //thread-safe and efficient memory store for posts
    private static final ConcurrentMap<UUID,Post> postStore;
    
    static {
        postStore = new ConcurrentHashMap<>();
        List<Post> postList = List.of(
            new Post("Spring boot REST API",
                    "This is a Blog Post on developing REST API using Spring Boot"),
            new Post("JAX-RS REST API",
                    "This is a Blog Post on developing RESTful API using Java EE"),
            new Post("Spring boot MVC vs JAX-RS for REST API",
                    "This is a Blog Post on comparatively examines REST API development"+
                            " in JAX-RS and Spring MVC"),
            new Post("Spring DispatcherServelet for logging API calls",
                    "This is a Blog Post on extending Spring DispatcherServelet"+
                            " for logging successful and errors in REST API calls")
        );
        postList.forEach(post -> postStore.put(post.getId(), post));
    }
    
    @GetMapping(value = "/search/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable("id") UUID id){
        
        if(!postStore.containsKey(id) || postStore.get(id)==null) 
            return new ResponseEntity<>("Post not found",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(postStore.get(id),HttpStatus.OK);
    }
    
    @GetMapping(value = "/search/all")
    public ResponseEntity<Object> getAllPosts(
            @RequestParam(name="pageIndex", required=false,defaultValue="0")Integer pageIndex,
            @RequestParam(name="size", required=false, defaultValue="20")Integer size)
    {
        if(postStore.isEmpty())
            return new ResponseEntity<>("Posts not found",HttpStatus.NOT_FOUND);
        else{
            List<Post> postList = postStore.values().stream().toList();
            int postCount = postList.size();
            int pageCount;
            int currentIndex=0;
            if(size>=1){
                pageCount = (int)Math.ceil(postCount/size);// always round up the result of postCount/size
                if(pageIndex>=0 && pageCount<pageCount){
                    currentIndex = pageIndex*size;
                    postList=
                    Arrays.asList(
                            Arrays.copyOfRange(
                                    postList.toArray(new Post[postCount]), currentIndex, currentIndex+size)
                    );
                }
            }
            return new ResponseEntity<>(postList,HttpStatus.OK);
        }
        
    }
    
    @PutMapping(value="/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable("id") UUID id,@RequestBody Post post){
        Post updatedPost = postStore.replace(id, post);
        if(updatedPost == null){
            return new ResponseEntity<>("Post not found",HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>("Post updated successfully",HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Object> createPost(@RequestBody Post post){
        Post newPost = new Post(post.getTitle(),post.getContent());
        postStore.put(newPost.getId(), newPost);
        return new ResponseEntity<>(// return the uri for the newly created Post
                URI.create("/post/search/"+newPost.getId()),HttpStatus.CREATED);
    }
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable("id") UUID id){
        Post removedPost = postStore. remove(id);
        if(removedPost == null){
            return new ResponseEntity<>("Post not found",HttpStatus.NOT_FOUND);
        }
            
        return new ResponseEntity<>("Post deleted successfully",HttpStatus.OK);
    }
}
