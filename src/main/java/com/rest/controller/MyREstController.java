package com.rest.controller;

import com.rest.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/app")
public class MyREstController {

    private final RestTemplate restTemplate;
    private String sessionId; // Для хранения session id

    @Autowired
    public MyREstController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public User[] getUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity("http://94.198.50.185:7081/api/users", User[].class);

        // Сохранение session id из заголовка ответа
        if (responseEntity.getHeaders().containsKey(HttpHeaders.SET_COOKIE)) {
            List<String> cookies = responseEntity.getHeaders().get(HttpHeaders.SET_COOKIE);
            for (String cookie : cookies) {
                if (cookie.startsWith("JSESSIONID=")) { // Предполагаем, что session id называется JSESSIONID
                    sessionId = cookie.split(";")[0]; // Сохраняем только сам session id
                    break;
                }
            }
        }

        return responseEntity.getBody();
    }

    @PostMapping
    public String post() {
        User user = new User(3L, "James", "Brown", (byte) 30);

        // Добавление session id в заголовок
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, sessionId); // Используем сохраненный session id
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<String> createUser = restTemplate.postForEntity("http://94.198.50.185:7081/api/users", requestEntity, String.class);
        return createUser.getBody();
    }

    @GetMapping("/users")
    public User[] getUsersAfterPost() {
        // Добавление session id в заголовок
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, sessionId); // Используем сохраненный session id

        ResponseEntity<User[]> responseEntity = restTemplate.exchange("http://94.198.50.185:7081/api/users", HttpMethod.GET,
                new HttpEntity<>(headers), User[].class);

        return responseEntity.getBody();
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
        // Установим ID для обновляемого пользователя
        user.setId(id); // Устанавливаем ID из пути
        // Обновляем имя и фамилию пользователя
        user.setName("Thomas");
        user.setLastName("Shelby");
        user.setAge((byte) 30);

        // Добавление session id в заголовок
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, sessionId);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://94.198.50.185:7081/api/users" ,
                HttpMethod.PUT,
                requestEntity,
                String.class);

        return responseEntity;
    }

    @GetMapping("/users/afterpost")
    public User[] getUsersAfterAfterPost() {
        // Добавление session id в заголовок
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, sessionId); // Используем сохраненный session id

        ResponseEntity<User[]> responseEntity = restTemplate.exchange("http://94.198.50.185:7081/api/users", HttpMethod.GET,
                new HttpEntity<>(headers), User[].class);

        return responseEntity.getBody();
    }

    @DeleteMapping("/{id}") // Указываем ID для удаления
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        // Добавление session id в заголовок
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, sessionId); // Используем сохраненный session id
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Корректируем URL, чтобы включить ID
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://94.198.50.185:7081/api/users/" + id, // Используем ID в пути
                HttpMethod.DELETE,
                requestEntity,
                String.class);

        return responseEntity;
    }
}




















//@RestController
//@RequestMapping("/app")
//@AllArgsConstructor
//
//public class MyREstController {
//
//    private final RestTemplate restTemplate;
//
//    @GetMapping()
//    public User[] getUsers() {
//        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity("http://94.198.50.185:7081/api/users", User[].class);
//
//        //  if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
//        return responseEntity.getBody();
//        //} else {
//        // Обработка ошибки или возврат пустого объекта
//        //     throw new RuntimeException("Ошибка при получении пользователя");}
//    }
//
//
//    @PostMapping
//    public String post() {
//        User user = new User(3L, "James", "Brown", (byte) 25);
//        ResponseEntity<String> createUser = restTemplate.postForEntity("http://94.198.50.185:7081/api/users", user, String.class);
//        return createUser.getBody();
//    }
//
//    @PutMapping("/3") // Указываем конкретный ID в пути
//    public ResponseEntity<String> updateUser(@RequestBody User user) {
//        // Обновляем имя и фамилию пользователя
//        user.setName("Thomas");
//        user.setLastName("Shelby");
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "http://94.198.50.185:7081/api/users/3", // Используем статический ID в пути
//                HttpMethod.PUT,
//                new HttpEntity<>(user),
//                String.class);
//        return responseEntity;
//    }
//
//}


