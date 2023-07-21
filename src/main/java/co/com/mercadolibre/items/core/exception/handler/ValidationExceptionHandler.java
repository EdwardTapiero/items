package co.com.mercadolibre.items.core.exception.handler;

import co.com.mercadolibre.items.core.exception.ItemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<ErrorResponse> itemException(ItemException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ex.getCode().toString());
        errorResponse.setMessage(ex.getMessage());

        log.error(String.valueOf(ex));
        return ResponseEntity.status(ex.getCode()).body(errorResponse);
    }

    @ExceptionHandler(UncategorizedMongoDbException.class)
    public ResponseEntity<ErrorResponse> mongoException(UncategorizedMongoDbException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorResponse.setMessage("Error when obtaining data from BD");
        errorResponse.setCause(ex.getMessage());

        log.error(String.valueOf(ex.getCause()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorResponse.setMessage("Field validation error");
        errorResponse.setValidations(errors);

        log.error(String.valueOf(ex.getCause()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
