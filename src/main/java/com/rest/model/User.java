package com.rest.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
 public class User {
     private Long id;
     private String name;
     private String lastName;
     private Byte age;


//     public User() {
//     }


//     public User(Long id, String name, String lastName, Byte age) {
//         this.id = id;
//         this.name = name;
//         this.lastName = lastName;
//         this.age = age;
//     }
 }

