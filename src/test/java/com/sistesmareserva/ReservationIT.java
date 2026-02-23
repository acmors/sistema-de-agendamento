package com.sistesmareserva;
import com.sistesmareserva.model.enums.ReservationStatus;
import com.sistesmareserva.web.dto.reservation.CreateReservationDTO;
import com.sistesmareserva.web.dto.reservation.ResponseReservationDTO;
import com.sistesmareserva.web.dto.reservation.UpdateReservationDTO;
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

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@Sql(scripts = "/sql/reservation/reservation-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/reservation/reservation-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ReservationIT {

    @Autowired
    WebTestClient testClient;

    @Test
    @DisplayName("Create reservation with valid params, should return 201.")
    public void createCase01(){
        ResponseReservationDTO response = testClient
                .post()
                .uri("/api/v1/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateReservationDTO("86225140050", 255, LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 10)))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ResponseReservationDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.clientName()).isEqualTo("jhon hernandes");
        Assertions.assertThat(response.totalValue()).isEqualByComparingTo(BigDecimal.valueOf(409.50));
    }

    @Test
    @DisplayName("Create reservation with checkout before check-in, should return 409.")
    public void createCase02(){
        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateReservationDTO("86225140050", 350, LocalDate.of(2026, 3, 2), LocalDate.of(2026, 3, 1)))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(409);
    }

    @Test
    @DisplayName("Create reservation with check-in in the past, should return 409.")
    public void createCase03(){
        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateReservationDTO("86225140050", 350, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 3, 1)))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(409);
    }

    @Test
    @DisplayName("Create reservation with room status equals reserved, should return 409.")
    public void createCase04(){
        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateReservationDTO("86225140050", 450, LocalDate.of(2026, 3, 2), LocalDate.of(2026, 3, 9)))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(409);
    }

    @Test
    @DisplayName("Find reservation by a valid id, should return 200.")
    public void findCase01(){
        ResponseReservationDTO response = testClient
                .get()
                .uri("/api/v1/reservation/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseReservationDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.clientName()).isEqualTo("pietro augusto");
        Assertions.assertThat(response.totalValue()).isEqualByComparingTo(BigDecimal.valueOf(364.0));
        Assertions.assertThat(response.roomNumber()).isEqualTo(250);
    }

    @Test
    @DisplayName("Find reservation by an invalid id, should return 200.")
    public void findCase02(){
        ErrorMessage response = testClient
                .get()
                .uri("/api/v1/reservation/101")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(404);

    }

    @Test
    @DisplayName("Update reservation with valid params, should return 200")
    public void updateCase01(){

        ResponseReservationDTO response = testClient
                .put()
                .uri("/api/v1/reservation/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdateReservationDTO(LocalDate.of(2026, 3, 15), ReservationStatus.APPROVED))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseReservationDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Update reservation with reservation status equals to CANCELED, should return 409")
    public void updateCase02(){

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/reservation/110")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdateReservationDTO(LocalDate.of(2026, 3, 15), ReservationStatus.APPROVED))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(409);
    }

    @Test
    @DisplayName("Update reservation with checkout before check-in, should return 409")
    public void updateCase03(){

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/reservation/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdateReservationDTO(LocalDate.of(2026, 2, 15), ReservationStatus.APPROVED))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(409);
    }

}
