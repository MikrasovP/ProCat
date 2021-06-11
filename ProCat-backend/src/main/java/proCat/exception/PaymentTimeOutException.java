package proCat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentTimeOutException extends Exception{
    public PaymentTimeOutException(String message) {
        super(message);
    }
}
