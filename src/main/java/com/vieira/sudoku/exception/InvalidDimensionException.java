package com.vieira.sudoku.exception;

import org.apache.log4j.Logger;

/**
 * Exception tha represents an incorrect dimension of a Sudoku board ou box. 
 * @author Fernando Jose Vieira
 *
 */
public class InvalidDimensionException extends Exception {

    private static final long serialVersionUID = -3391119188036606250L;

    private static Logger log = Logger.getLogger(InvalidDimensionException.class.getSimpleName());

    /**
     * @param message
     * @param cause
     */
    public InvalidDimensionException(String message, Throwable cause) {
	super(message, cause);
	log.error(message, cause);
    }

    /**
     * @param message
     */
    public InvalidDimensionException(String message) {
	super(message);
	log.error(message);
    }

    /**
     * @param cause
     */
    public InvalidDimensionException(Throwable cause) {
	super(cause);
	log.error(cause);
    }



}
