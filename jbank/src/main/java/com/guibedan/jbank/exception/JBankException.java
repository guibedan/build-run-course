package com.guibedan.jbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public abstract class JBankException extends RuntimeException {

    public JBankException(String message) {
        super(message);
    }

    public JBankException(Throwable cause) {
        super(cause);
    }

    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        pd.setTitle("JBank Internal Server Error");
        pd.setDetail("Contact JBank Support");
        return pd;
    }

}
