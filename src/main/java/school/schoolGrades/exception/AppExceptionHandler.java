package school.schoolGrades.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> notFoundHandler(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<Object> conflictHandler(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = NotAllowedValueException.class)
    public ResponseEntity<Object> notAllowedHandler(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}