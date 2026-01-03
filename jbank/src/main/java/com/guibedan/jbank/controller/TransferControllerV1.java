package com.guibedan.jbank.controller;

import com.guibedan.jbank.controller.dto.TransferMoneyDto;
import com.guibedan.jbank.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transfers")
public class TransferControllerV1 {

    private final TransferService transferService;

    public TransferControllerV1(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferMoneyDto dto) {
        transferService.transferMoney(dto);
        return ResponseEntity.noContent().build();
    }

}
