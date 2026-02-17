package com.sistesmareserva;

import com.sistesmareserva.web.dto.user.CreateUserAccountDTO;
import com.sistesmareserva.web.dto.user.ResponseUserAccountDTO;
import com.sistesmareserva.web.dto.user.UserPasswordDTO;
import com.sistesmareserva.web.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserAccountIT {

    @Autowired
    WebTestClient testClient;

    @Test
    @DisplayName("Create user with valid params, should return 201")
    public void createCase01(){

        ResponseUserAccountDTO responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUserAccountDTO("breno@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ResponseUserAccountDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.id()).isNotNull();
        Assertions.assertThat(responseDTO.email()).isEqualTo("breno@gmail.com");

    }

    @Test
    @DisplayName("Create user with invalid email, should return 422.")
    public void createCase02(){

        ErrorMessage responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUserAccountDTO("", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getStatus()).isEqualTo(422);


        responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUserAccountDTO("breno@", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


    responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUserAccountDTO("breno@gmail", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
    }

    @Test
    @DisplayName("Create user with invalid password, should return 422.")
    public void createCase03(){

        ErrorMessage responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUserAccountDTO("breno@gmail.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getStatus()).isEqualTo(422);


        responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUserAccountDTO("breno@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


        responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUserAccountDTO("breno@gmail.com", "1234567890123456789123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
    }

    @Test
    @DisplayName("Create user with duplicate email, should return 409")
    public void createCase04(){
        ErrorMessage responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUserAccountDTO("jhon@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getStatus()).isEqualTo(409);
    }

    @Test
    @DisplayName("Find a user by a valid id, should return 200.")
    public void findCase01(){
        ResponseUserAccountDTO responseDTO = testClient
                .get()
                .uri("/api/v1/users/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseUserAccountDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.id()).isEqualTo(100);
    }

    @Test
    @DisplayName("Find a user by an  invalid id, should return 404.")
    public void findCase02(){
        ErrorMessage responseDTO = testClient
                .get()
                .uri("/api/v1/users/101")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getStatus()).isEqualTo(404);
    }

    @Test
    @DisplayName("Update password with valid params, should return 204")
    public void updatePasswordCase01(){
        testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("12345678", "123456789", "123456789"))
                .exchange()
                .expectStatus().isNoContent();

        testClient
                .patch()
                .uri("/api/v1/users/200")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("12345678", "123456789", "123456789"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("Update password with invalid params, should return 422")
    public void updatePasswordCase02(){
       ErrorMessage responseDTO =  testClient
                .patch()
                .uri("/api/v1/users/200")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("", "", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

       Assertions.assertThat(responseDTO).isNotNull();
       Assertions.assertThat(responseDTO.getStatus()).isEqualTo(422);

       responseDTO =  testClient
                .patch()
                .uri("/api/v1/users/200")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("12345678", "1234567891234564567898", "1234567891234564567898"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getStatus()).isEqualTo(422);

        responseDTO =  testClient
                .patch()
                .uri("/api/v1/users/200")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("12345678", "123456789", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getStatus()).isEqualTo(400);

    }



}
