package com.guibedan.jbank.controller.dto;

import com.guibedan.jbank.exception.StatementException;
import com.guibedan.jbank.repository.dto.StatementView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record StatementItemDto(
        String statementId,
        String type,
        String literal,
        BigDecimal value,
        LocalDateTime dateTime,
        StatementOperation operation
) {
    public static StatementItemDto fromView(StatementView view, UUID walletId) {
        StatementOperation statementOperation = null;
        String literalOfType = "";

        if (view.getType().equalsIgnoreCase("deposit")) {
            statementOperation = StatementOperation.CREDIT;
            literalOfType = "money deposit";
        }  else if (view.getType().equalsIgnoreCase("transfer") && view.getWalletSender().equalsIgnoreCase(walletId.toString())) {
            statementOperation = StatementOperation.DEBIT;
            literalOfType = "money sent to " + view.getWalletReceiver();
        } else if (view.getType().equalsIgnoreCase("transfer") && view.getWalletReceiver().equalsIgnoreCase(walletId.toString())) {
            statementOperation = StatementOperation.CREDIT;
            literalOfType = "money received from " + view.getWalletSender();
        } else {
            throw new StatementException("Unsupported view type: " + view.getType());
        }

        return new StatementItemDto(
                view.getStatementId(),
                view.getType(),
                literalOfType,
                view.getStatementValue(),
                view.getStatementDateTime(),
                statementOperation
        );
    }

}
