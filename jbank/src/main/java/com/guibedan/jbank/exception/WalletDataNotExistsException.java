package com.guibedan.jbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WalletDataNotExistsException extends JBankException {

    private final String detail;

    public WalletDataNotExistsException(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND.value());
        pd.setTitle("Wallet Data Not Exists");
        pd.setDetail(detail);
        return pd;
    }

}
