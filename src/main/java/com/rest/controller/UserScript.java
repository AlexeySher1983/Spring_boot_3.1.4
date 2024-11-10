package com.rest.controller;
import com.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Component
public class UserScript implements CommandLineRunner {

    private final MyREstController myrestcontroller;

    @Autowired
    public UserScript(MyREstController myrestcontroller)  {
        this.myrestcontroller = myrestcontroller;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Получаем всех пользователей
        User[] users = myrestcontroller.getUsers();
        System.out.println("Полученные пользователи:");
        for (User user : users) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getLastName());
        }

        // 2. Создаем нового пользователя
        String createUserResponse = myrestcontroller.post();
        System.out.println("Ответ на создание пользователя: " + createUserResponse);

User[] usersAfterPost = myrestcontroller.getUsersAfterPost();
        System.out.println("Полученные пользователи:");
        for (User user : usersAfterPost) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getLastName());
        }

        // 3. Обновляем пользователя с ID 3
        User userToUpdate = new User(); // Создаем новый объект User
        ResponseEntity<String> updateUserResponse = myrestcontroller.updateUserById(3L, userToUpdate); // Передаем ID и объект
        System.out.println("Ответ на обновление пользователя: " + updateUserResponse.getBody());

        User[] usersAfterAfterPost = myrestcontroller.getUsersAfterPost();
        System.out.println("Полученные пользователи:");
        for (User user : usersAfterAfterPost) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getLastName());
        }

        // 4. Удаляем пользователя с ID 3
        ResponseEntity<String> deleteUserResponse = myrestcontroller.deleteUserById(3L); // Передаем ID();
        System.out.println("Ответ на удаление пользователя: " + deleteUserResponse.getBody());


        System.out.println("Итоговый ответ: " + createUserResponse+updateUserResponse.getBody()+deleteUserResponse.getBody());
   }
}