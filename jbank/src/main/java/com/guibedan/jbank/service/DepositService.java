package com.guibedan.jbank.service;

import com.guibedan.jbank.entity.Deposit;
import com.guibedan.jbank.entity.Wallet;
import com.guibedan.jbank.repository.DepositRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DepositService {

    private final DepositRepository depositRepository;

    public DepositService(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    public void createDeposit(Wallet wallet, BigDecimal depositValue, String ipAddress) {
        var deposit = Deposit.builder()
                .wallet(wallet)
                .depositValue(depositValue)
                .depositDateTime(LocalDateTime.now())
                .ipAddress(ipAddress)
                .build();

        depositRepository.save(deposit);
    }

}
