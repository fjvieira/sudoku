package com.vieira.sudoku.exception;

import org.apache.log4j.Logger;

/**
 * Runtime expection that indicates there is a problem in the game's algorithm.
 * @author Fernando Jose Vieira
 *
 */
public class SudokuAlgorithmException extends RuntimeException {

    private static final long serialVersionUID = -3391119188036606250L;

    private static Logger log = Logger.getLogger(SudokuAlgorithmException.class.getSimpleName());

    /**
     * @param message
     * @param cause
     */
    public SudokuAlgorithmException(String message, Throwable cause) {
	super(message, cause);
	log.error(message, cause);
    }

    /**
     * @param message
     */
    public SudokuAlgorithmException(String message) {
	super(message);
	log.error(message);
    }

    /**
     * @param cause
     */
    public SudokuAlgorithmException(Throwable cause) {
	super(cause);
	log.error(cause);
    }



}
