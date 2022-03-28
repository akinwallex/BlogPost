/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.akinwale.blogpost.model;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author hp 1030 g2
 */
public class Post implements Serializable {
    @Getter private UUID id;
    @Getter @Setter private String title;
    @Getter @Setter private String content;
    public Post(){
        id = UUID.randomUUID();
    }
    public Post(String title,String content){
        id = UUID.randomUUID();
        setTitle(title);
        setContent(content);
    }
}
