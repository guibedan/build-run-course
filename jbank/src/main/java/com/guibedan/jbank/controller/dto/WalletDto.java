package com.guibedan.jbank.controller.dto;

import com.guibedan.jbank.entity.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletDto(
        UUID walletId,
        String cpf,
        String name,
        String email,
        BigDecimal balance
) {

    public static WalletDto fromEntity(Wallet wallet) {
        return new WalletDto(wallet.getId(), wallet.getCpf(), wallet.getName(), wallet.getEmail(), wallet.getBalance());
    }

}
