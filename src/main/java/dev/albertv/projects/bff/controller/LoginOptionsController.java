package dev.albertv.projects.bff.controller;

import dev.albertv.projects.bff.dto.LoginOptionDTO;
import dev.albertv.projects.bff.service.LoginOptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/login/options")
@RequiredArgsConstructor
public class LoginOptionsController {

    private final LoginOptionsService service;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LoginOptionDTO>> findLoginOptions() {
        return ResponseEntity.ok(service.getLoginOptions());
    }

}
