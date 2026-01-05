package com.guibedan.smart.stock.controller;

import com.guibedan.smart.stock.controller.dto.StartDto;
import com.guibedan.smart.stock.service.SmartStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/start")
public class StartController {

    private final SmartStockService smartStockService;

    public StartController(SmartStockService smartStockService) {
        this.smartStockService = smartStockService;
    }

    @PostMapping
    public ResponseEntity<Void> start(@RequestBody StartDto dto) {
        CompletableFuture.runAsync(() -> {
            smartStockService.processReport(dto);
        });

        return ResponseEntity.accepted().build();
    }

}
