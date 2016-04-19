package com.vieira.sudoku.exception;

import org.apache.log4j.Logger;

/**
 * Exception that indicate a matrix (3x3) is not a valid Sudoku Box. 
 * @author Fernando Jose Vieira
 *
 */
public class InvalidBoxException extends Exception {

    private static final long serialVersionUID = -3391119188036606250L;

    private static Logger log = Logger.getLogger(InvalidBoxException.class.getSimpleName());

    /**
     * @param message
     * @param cause
     */
    public InvalidBoxException(String message, Throwable cause) {
	super(message, cause);
	log.error(message, cause);
    }

    /**
     * @param message
     */
    public InvalidBoxException(String message) {
	super(message);
	log.error(message);
    }

    /**
     * @param cause
     */
    public InvalidBoxException(Throwable cause) {
	super(cause);
	log.error(cause);
    }



}
