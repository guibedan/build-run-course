package com.guibedan.jbank.exception;

import com.guibedan.jbank.exception.dto.InvalidParamDto;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JBankException.class)
    public ProblemDetail handleJBankException(JBankException ex) {
        return ex.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getFieldErrors().stream()
                .map(fe -> new InvalidParamDto(fe.getField(), fe.getDefaultMessage())).toList();

        var pd = ProblemDetail.forStatus(400);
        pd.setTitle("Invalid request parameters");
        pd.setDetail("There is invalid fields on the request");
        pd.setProperty("invalid-params", fieldErrors);

        return pd;
    }

}
