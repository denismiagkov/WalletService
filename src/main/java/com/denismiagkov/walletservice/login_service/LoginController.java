package com.denismiagkov.walletservice.login_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/")
public class LoginController extends HttpServlet {
    Person person = new Person("Ivan", "Petrov", 27);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

         String json = new ObjectMapper().writeValueAsString(person);
        resp.getWriter().write(json);
    }

    // private final AuthService authService;

//    //public LoginController() {
//        authService = new AuthService();
//    }

//    @PostMapping("login")
//    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
//        final JwtResponse token = authService.login(authRequest);
//        return ResponseEntity.ok(token);
//    }
//
//    @PostMapping("token")
//    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
//        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
//        return ResponseEntity.ok(token);
//    }
//
//    @PostMapping("refresh")
//    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
//        final JwtResponse token = authService.refresh(request.getRefreshToken());
//        return ResponseEntity.ok(token);
//    }

}

class Person{
   public String name;
    public String surname;
   public int age;

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }


}

class Main {
    public static void main(String[] args) throws JsonProcessingException {
        Person person = new Person("John", "Doe", 30);
        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(person);
        System.out.println(json);
    }
}
