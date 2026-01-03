package com.guibedan.jbank.controller;

import com.guibedan.jbank.constants.HeadersConstants;
import com.guibedan.jbank.controller.dto.CreateWalletDto;
import com.guibedan.jbank.controller.dto.DepositMoneyDto;
import com.guibedan.jbank.controller.dto.StatementDto;
import com.guibedan.jbank.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/wallets")
public class WalletControllerV1 {

    private final WalletService walletService;

    public WalletControllerV1(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public String getWallet() {
        return "Hello World!";
    }

    @PostMapping
    public ResponseEntity<Void> createWallet(@Valid @RequestBody CreateWalletDto walletDto) {
        var walletId = walletService.createWallet(walletDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(walletId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable("walletId") UUID walletId) {
        walletService.deleteWalletById(walletId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<Void> depositMoney(@PathVariable("walletId") UUID walletId,
                                             @Valid @RequestBody DepositMoneyDto dto,
                                             @RequestAttribute(HeadersConstants.USER_IP_HEADER) String ipAddress) {
        walletService.depositMoney(walletId, dto, ipAddress);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{walletId}/statements")
    public ResponseEntity<StatementDto> getStatements(@PathVariable("walletId") UUID walletId,
                                                      @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        var statements = walletService.getStatements(walletId, page, pageSize);
        return ResponseEntity.ok().body(statements);
    }

}
