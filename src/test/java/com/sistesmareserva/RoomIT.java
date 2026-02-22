package com.sistesmareserva;

import com.sistesmareserva.model.enums.Status;
import com.sistesmareserva.model.enums.Type;
import com.sistesmareserva.web.dto.room.CreateRoomDTO;
import com.sistesmareserva.web.dto.room.ResponseRoomDTO;
import com.sistesmareserva.web.dto.room.UpdateRoomPriceDTO;
import com.sistesmareserva.web.dto.room.UpdateRoomStatusDTO;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@Sql(scripts = "/sql/room/room-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/room/room-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RoomIT {

    @Autowired
    WebTestClient testClient;

    @Test
    @DisplayName("Create a room with valid params, should return 201.")
    public void createCase01(){

        ResponseRoomDTO response = testClient
                .post()
                .uri("/api/v1/room")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateRoomDTO(351, Type.DELUXE, BigDecimal.valueOf(75.50)))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ResponseRoomDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.id()).isNotNull();
        Assertions.assertThat(response.number()).isEqualTo(351);
        Assertions.assertThat(response.type()).isEqualTo(Type.DELUXE);
        Assertions.assertThat(response.pricePerDay()).isEqualTo(BigDecimal.valueOf(75.50));
    }

    @Test
    @DisplayName("Create room with duplicated number, should return 409.")
    public void createCase02(){

        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/room")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateRoomDTO(350, Type.DELUXE, BigDecimal.valueOf(75.50)))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(409);
    }

    @Test
    @DisplayName("Find room by valid number, should return 200.")
    public void findCase01(){

        ResponseRoomDTO response = testClient
                .get()
                .uri("/api/v1/room/250")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseRoomDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.number()).isEqualTo(250);
    }

    @Test
    @DisplayName("Find room by invalid number, should return 404.")
    public void findCase02(){

        ErrorMessage response = testClient
                .get()
                .uri("/api/v1/room/251")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    @DisplayName("Update room price, should return 204")
    public void updateCase01(){
        testClient
                .patch()
                .uri("/api/v1/room/100/price")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdateRoomPriceDTO(BigDecimal.valueOf(95.50)))
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @DisplayName("Update room status, should return 204")
    public void updateCase02(){
        testClient
                .patch()
                .uri("/api/v1/room/100/status")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdateRoomStatusDTO(Status.RESERVED))
                .exchange()
                .expectStatus().isOk();

    }

}
