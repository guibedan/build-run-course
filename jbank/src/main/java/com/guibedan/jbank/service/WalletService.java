package com.guibedan.jbank.service;

import com.guibedan.jbank.controller.dto.*;
import com.guibedan.jbank.entity.Wallet;
import com.guibedan.jbank.exception.WalletBalanceIsInsufficientException;
import com.guibedan.jbank.exception.WalletBalanceIsNotZeroException;
import com.guibedan.jbank.exception.WalletDataAlreadyExistsException;
import com.guibedan.jbank.exception.WalletDataNotExistsException;
import com.guibedan.jbank.repository.WalletRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final DepositService depositService;

    public WalletService(WalletRepository walletRepository, DepositService depositService) {
        this.walletRepository = walletRepository;
        this.depositService = depositService;
    }

    public UUID createWallet(CreateWalletDto walletDto) {
        var cpf = walletDto.cpf().replaceAll("[^0-9]", "");

        if (walletRepository.existsByCpfOrEmail(cpf, walletDto.email())) {
            throw new WalletDataAlreadyExistsException("CPF or Email already exists");
        }

        var wallet = Wallet.builder()
                .cpf(cpf)
                .name(walletDto.name())
                .email(walletDto.email())
                .balance(BigDecimal.ZERO)
                .build();

        return walletRepository.save(wallet).getId();
    }

    public void deleteWalletById(UUID walletId) {
        var wallet = getWalletById(walletId);

        if (wallet.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new WalletBalanceIsNotZeroException(
                    "The wallet balance need to be zero for delete. The current amount is " + wallet.getBalance());
        }

        walletRepository.delete(wallet);
    }

    @Transactional
    public void depositMoney(UUID walletId, @Valid DepositMoneyDto dto, String ipAddress) {
        var wallet = getWalletById(walletId);
        wallet.setBalance(wallet.getBalance().add(dto.depositValue()));

        depositService.createDeposit(wallet, dto.depositValue(), ipAddress);
        walletRepository.save(wallet);
    }

    @Transactional
    public Wallet debit(UUID walletId, BigDecimal value) {
        var wallet = getWalletById(walletId);

        ensureSufficientBalance(wallet, value);
        wallet.setBalance(wallet.getBalance().subtract(value));

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet credit(UUID walletId, BigDecimal value) {
        var wallet = getWalletById(walletId);
        wallet.setBalance(wallet.getBalance().add(value));
        return walletRepository.save(wallet);
    }

    public StatementDto getStatements(UUID walletId, Integer page, Integer pageSize) {
        var wallet = getWalletById(walletId);

        var pageRequest = PageRequest.of(page, pageSize,  Sort.Direction.DESC, "statement_date_time");
        var statement = walletRepository.findStatements(walletId, pageRequest)
                .map(view -> StatementItemDto.fromView(view, walletId));

        return new StatementDto(
                WalletDto.fromEntity(wallet),
                statement.getContent(),
                new PaginationDto(statement.getNumber(), statement.getSize(), statement.getTotalElements(), statement.getTotalPages())
        );
    }

    private Wallet getWalletById(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletDataNotExistsException("Wallet with id '" + walletId + "' not found"));
    }

    private void ensureSufficientBalance(Wallet wallet, BigDecimal value) {
        if (wallet.getBalance().compareTo(value) < 0) {
            throw new WalletBalanceIsInsufficientException("Insufficient balance. Current balance: " + wallet.getBalance());
        }
    }

}
