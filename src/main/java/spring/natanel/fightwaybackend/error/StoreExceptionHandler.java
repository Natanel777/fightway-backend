package spring.natanel.fightwaybackend.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class StoreExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException e){
        System.out.println();
        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        problemDetails.setProperty("timestamp", LocalDateTime.now());
        problemDetails.setProperty("resourceName", e.getResourceName());
        problemDetails.setProperty("resourceValue", e.getValue());
        return problemDetails;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException e){
        System.out.println("Handling BadRequestException: " + e.getMessage());
        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

        problemDetails.setProperty("timestamp", LocalDateTime.now());
        problemDetails.setProperty("resourceName", e.getResourceName());
        return problemDetails;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Access Denied");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("message", "You do not have permission to access this resource.");
        return problemDetail;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail handleAuthenticationException(AuthenticationException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Authentication Failed");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("message", "Authentication failed. Please check your credentials.");
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        //new ProblemDetail Object
        var problemDetail =
                ProblemDetail.
                        forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");

        //for Each field errors in the exception
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            problemDetail.setProperty("FailedProperty", fieldError.getField());
            //problemDetail.setProperty("objectName", fieldError.getObjectName());
            problemDetail.setProperty("message", fieldError.getDefaultMessage());
            problemDetail.setProperty("rejectedValue", fieldError.getRejectedValue());

        });

        //add details about the exception
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }

    //e-> cause ->
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e) {

        var problemDetail = ProblemDetail.
                forStatusAndDetail(HttpStatus.BAD_REQUEST, "Database saved Failed");

        if (e.getCause() instanceof ConstraintViolationException cve){
            problemDetail.setProperty("cause", "Constraint Violation");
        }
        //add details about the exception
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(StoreException.class)
    public ProblemDetail handleBlogException(StoreException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
}
