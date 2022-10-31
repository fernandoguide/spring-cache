package br.com.fernandoguide.cache.controller.exceptions;

import br.com.fernandoguide.cache.service.exceptions.ObjectNotFoundExecpition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ResourceExeceptionHandler {

    @ExceptionHandler(ObjectNotFoundExecpition.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundExecpition e, HttpServletRequest request) {
        StandardError err = new StandardError(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), HttpStatus.NOT_FOUND.value(),
                "NOT FOUND", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

}
