package com.sistesmareserva;

import com.sistesmareserva.web.dto.client.ClientResponseDTO;
import com.sistesmareserva.web.dto.client.CreateClientDTO;
import com.sistesmareserva.web.dto.user.CreateUserAccountDTO;
import com.sistesmareserva.web.dto.user.ResponseUserAccountDTO;
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
@Sql(scripts = "/sql/client/clients-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/client/clients-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientIT {

    @Autowired
    WebTestClient testClient;

    @Test
    @DisplayName("Create client with valid params, should return 201.")
    public void createCase01(){
        ClientResponseDTO response = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateClientDTO("matheus", "56702389078", "matheus@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClientResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.id()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.name()).isEqualTo("matheus");
        org.assertj.core.api.Assertions.assertThat(response.cpf()).isEqualTo("56702389078");
        org.assertj.core.api.Assertions.assertThat(response.email()).isEqualTo("matheus@gmail.com");
    }

    @Test
    @DisplayName("Create user with invalid email, should return 422.")
    public void createCase02(){

        ErrorMessage responseDTO = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateClientDTO("marcos","98126444070","", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getStatus()).isEqualTo(422);


        responseDTO = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateClientDTO("marcos","98126444070","pedro@", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


        responseDTO = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateClientDTO("marcos","98126444070","pedro@gmail", "12345678"))
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
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateClientDTO("breno", "76717836091", "breno@gmail.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getStatus()).isEqualTo(422);


        responseDTO = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateClientDTO("breno", "76717836091", "breno@gmail.com", "1234"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


        responseDTO = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateClientDTO("breno", "76717836091", "breno@gmail.com", "123456789784512326598159"))
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
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateClientDTO("jhon", "76717836091", "jhon@gmail.com", "123456789"))
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
        ClientResponseDTO responseDTO = testClient
                .get()
                .uri("/api/v1/clients/10")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.id()).isEqualTo(10);
    }

    @Test
    @DisplayName("Find a user by an  invalid id, should return 404.")
    public void findCase02(){
        ErrorMessage responseDTO = testClient
                .get()
                .uri("/api/v1/clients/101")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getStatus()).isEqualTo(404);
    }

    @Test
    @DisplayName("Find a user by a valid CPF, should return 200.")
    public void findCase03(){
        ClientResponseDTO responseDTO = testClient
                .get()
                .uri("/api/v1/clients/cpf/86225140050")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.cpf()).isEqualTo("86225140050");
    }

    @Test
    @DisplayName("Find a user by an invalid CPF, should return 404.")
    public void findCase04(){
        ErrorMessage responseDTO = testClient
                .get()
                .uri("/api/v1/clients/cpf/86225140058")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getStatus()).isEqualTo(404);
    }
}
