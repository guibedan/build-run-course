package com.guibedan.jbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WalletBalanceIsNotZeroException extends JBankException {

    private final String detail;

    public WalletBalanceIsNotZeroException(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_CONTENT.value());
        pd.setTitle("Wallet balance is not zero");
        pd.setDetail(detail);
        return pd;
    }

}
