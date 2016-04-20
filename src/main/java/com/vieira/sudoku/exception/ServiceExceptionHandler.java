package com.vieira.sudoku.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Runtime error handling to avoid exposure of server error details.
 * @author Fernando Jose Vieira
 *
 */
@ControllerAdvice
public class ServiceExceptionHandler {
    
    private static String ERROR_TYPE = "type";
    
    private static String ERROR_MESSAGE = "message";
    
    private static String ERROR_FIELDS = "fields";

    /**
     * @param error
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<?> handleRuntimeException(RuntimeException error) {

        ModelMap map = new ModelMap();
        map.addAttribute(ERROR_TYPE, "internal_server_error");
        map.addAttribute(ERROR_MESSAGE, "Please contact the administrator.");
        
        return new ResponseEntity<ModelMap>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * @param error
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
        List<FieldError> fieldErrorList = error.getBindingResult().getFieldErrors();
        ModelMap map = new ModelMap();
        map.addAttribute(ERROR_TYPE, "validation_error");
        map.addAttribute(ERROR_MESSAGE, "The message contain errors.");
        
        ModelMap errorMap = new ModelMap();
        for (FieldError fieldError : fieldErrorList) {
            errorMap.addAttribute(fieldError.getField(), fieldError.getDefaultMessage());
        }
        map.addAttribute(ERROR_FIELDS, errorMap);
        
        return new ResponseEntity<ModelMap>(map, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
}
