package com.softserve.teachua.exception.handler;

import com.google.api.client.http.HttpResponseException;
import com.softserve.teachua.dto.exception.ExceptionResponse;
import com.softserve.teachua.exception.AlreadyExistException;
import com.softserve.teachua.exception.BadRequestException;
import com.softserve.teachua.exception.CannotDeleteFileException;
import com.softserve.teachua.exception.DatabaseRepositoryException;
import com.softserve.teachua.exception.FileUploadException;
import com.softserve.teachua.exception.IncorrectInputException;
import com.softserve.teachua.exception.JsonWriteException;
import com.softserve.teachua.exception.LogNotFoundException;
import com.softserve.teachua.exception.MatchingPasswordException;
import com.softserve.teachua.exception.MethodNotSupportedException;
import com.softserve.teachua.exception.NotExistException;
import com.softserve.teachua.exception.NotVerifiedUserException;
import com.softserve.teachua.exception.RestoreArchiveException;
import com.softserve.teachua.exception.StreamCloseException;
import com.softserve.teachua.exception.UpdatePasswordException;
import com.softserve.teachua.exception.WrongAuthenticationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Custom exception handler to handle own exceptions and handle Spring's exceptions(BadRequest, MethodNotSupported).
 * <p>
 * Use @code buildExceptionBody(Exception exception, HttpStatus status) to create own exception body.
 *
 * @author Denis Burko
 */
@AllArgsConstructor
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(WrongAuthenticationException.class)
    public final ResponseEntity<Object> handleAuthenticationException(WrongAuthenticationException exception) {
        return buildExceptionBody(exception, UNAUTHORIZED);
    }

    @ExceptionHandler(NotExistException.class)
    public final ResponseEntity<Object> handleNotExistException(NotExistException exception) {
        return buildExceptionBody(exception, NOT_FOUND);
    }

    @ExceptionHandler(IncorrectInputException.class)
    public final ResponseEntity<Object> handleIncorrectInputException(IncorrectInputException exception) {
        return buildExceptionBody(exception, BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public final ResponseEntity<Object> handleAlreadyExistException(AlreadyExistException exception) {
        return buildExceptionBody(exception, FORBIDDEN);
    }

    @ExceptionHandler(UpdatePasswordException.class)
    public final ResponseEntity<Object> handleUpdatePasswordException(UpdatePasswordException exception) {
        return buildExceptionBody(exception, BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseRepositoryException.class)
    public final ResponseEntity<Object> handleDatabaseRepositoryException(DatabaseRepositoryException exception) {
        return buildExceptionBody(exception, BAD_GATEWAY);
    }

    @ExceptionHandler(JsonWriteException.class)
    public final ResponseEntity<Object> handleJsonWriteException(JsonWriteException exception) {
        return buildExceptionBody(exception, CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleDatabaseRepositoryException(BadRequestException exception) {
        return buildExceptionBody(exception, BAD_REQUEST);
    }

    @ExceptionHandler(FileUploadException.class)
    public final ResponseEntity<Object> handleFileUploadException(JsonWriteException exception) {
        return buildExceptionBody(exception, FORBIDDEN);
    }

    @ExceptionHandler(MatchingPasswordException.class)
    public final ResponseEntity<Object> handleMatchingPasswordException(IllegalArgumentException exception) {
        return buildExceptionBody(exception, NOT_FOUND);
    }

    @ExceptionHandler(NotVerifiedUserException.class)
    public final ResponseEntity<Object> handleFileUploadException(RuntimeException exception) {
        return buildExceptionBody(exception, FORBIDDEN);
    }

    @ExceptionHandler(RestoreArchiveException.class)
    public final ResponseEntity<Object> handleRestoreArchiveException(RestoreArchiveException exception) {
        return buildExceptionBody(exception, BAD_REQUEST);
    }

    @ExceptionHandler(StreamCloseException.class)
    public final ResponseEntity<Object> handleStreamCloseException(StreamCloseException exception) {
        return buildExceptionBody(exception, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> mapMessage = new HashMap<>();
        mapMessage.put("startDate Invalid future date", "Дата не може бути у минулому");

        StringBuilder sb = new StringBuilder();
        exception.getBindingResult().getFieldErrors().forEach((error) -> {
            if ((error.getField() + " " + error.getDefaultMessage()).contains("startDate Invalid future date")) {
                sb.append(mapMessage.get("startDate Invalid future date")).append(" and ");
            } else {
                sb.append(error.getField()).append(" ").append(error.getDefaultMessage()).append(" and ");
            }
        });
        sb.setLength(sb.length() - 5);
        return buildExceptionBody(new BadRequestException(sb.toString()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildExceptionBody(new BadRequestException(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        return buildExceptionBody(new MethodNotSupportedException(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        return buildExceptionBody(new BadRequestException(exception.getMessage()), status);
    }

    private ResponseEntity<Object> buildExceptionBody(Exception exception, HttpStatus httpStatus) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder().status(httpStatus.value())
                .message(exception.getMessage()).build();
        log.debug(exception.getMessage());
        return ResponseEntity.status(httpStatus).body(exceptionResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
        return buildExceptionBody(exception, BAD_REQUEST);
    }

    @ExceptionHandler(LogNotFoundException.class)
    protected ResponseEntity<Object> handleLogNotFoundException(LogNotFoundException exception) {
        return buildExceptionBody(exception, NOT_FOUND);
    }

    @ExceptionHandler(CannotDeleteFileException.class)
    protected ResponseEntity<Object> handleCannotDeleteFileException(CannotDeleteFileException exception) {
        return buildExceptionBody(exception, CONFLICT);
    }

    @ExceptionHandler(HttpResponseException.class)
    public ResponseEntity<Object> handleHttpResponseException(HttpResponseException exception) {
        HttpStatus httpStatus = Optional.ofNullable(
            HttpStatus.resolve(exception.getStatusCode())
        ).orElse(BAD_REQUEST);
        return buildExceptionBody(exception, httpStatus);
    }
}
