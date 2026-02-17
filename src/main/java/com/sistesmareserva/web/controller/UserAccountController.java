package com.sistesmareserva.web.controller;

import com.sistesmareserva.web.dto.user.CreateUserAccountDTO;
import com.sistesmareserva.web.dto.user.ResponseUserAccountDTO;
import com.sistesmareserva.web.dto.user.UserPasswordDTO;
import com.sistesmareserva.model.UserAccount;
import com.sistesmareserva.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userService;

    @PostMapping
    public ResponseEntity<ResponseUserAccountDTO> create(@Valid @RequestBody CreateUserAccountDTO dto){
        return ResponseEntity.status(201).body(userService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody  UserPasswordDTO dto){
        UserAccount user = userService.updatePassword(id, dto.currentPassword(), dto.newPassword(), dto.confirmPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserAccountDTO> findById(@PathVariable Long id){
        var user = userService.findByIdDto(id);
        return ResponseEntity.ok(user);
    }
}
