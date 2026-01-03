package com.guibedan.jbank.service;

import com.guibedan.jbank.controller.dto.TransferMoneyDto;
import com.guibedan.jbank.entity.Transfer;
import com.guibedan.jbank.repository.TransferRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final WalletService walletService;

    public TransferService(TransferRepository transferRepository, WalletService walletService) {
        this.transferRepository = transferRepository;
        this.walletService = walletService;
    }

    @Transactional
    public void transferMoney(@Valid TransferMoneyDto dto) {
        var walletSender = walletService.debit(dto.sender(), dto.value());
        var walletReceiver = walletService.credit(dto.receiver(), dto.value());

        var transfer = Transfer.builder()
                .transferValue(dto.value())
                .sender(walletSender)
                .receiver(walletReceiver)
                .transferDateTime(LocalDateTime.now())
                .build();

        transferRepository.save(transfer);
    }

}
