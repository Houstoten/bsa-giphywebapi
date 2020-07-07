package com.bsa.giphyWebAPI.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Map;

@ResponseBody
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({GiphyRequestFailed.class, SearchNotFoundException.class, NotFoundException.class})
    public ResponseEntity<Object> handleGiphyRequestException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Not found element"));
    }

    @ExceptionHandler({InvalidRequestException.class, InvalidUserException.class, InvalidException.class})
    public ResponseEntity<Object> springHandleNotFound(InvalidException ex) throws IOException {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Invalid element"));
    }

    @ExceptionHandler(ServerTimeoutException.class)
    public ResponseEntity<Object> handleTimeoutException(ServerTimeoutException ex) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Too long to wait"));
    }

}


