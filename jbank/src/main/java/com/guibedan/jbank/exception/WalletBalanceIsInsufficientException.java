package com.guibedan.jbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WalletBalanceIsInsufficientException extends JBankException {

    private final String detail;

    public WalletBalanceIsInsufficientException(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_CONTENT.value());
        pd.setTitle("Wallet balance is insufficient");
        pd.setDetail(detail);
        return pd;
    }

}
