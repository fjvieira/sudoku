package com.vieira.sudoku.exception;

import org.apache.log4j.Logger;

/**
 * Runtime expection that indicates there is a problem with cache of this application.
 * @author Fernando Jose Vieira
 *
 */
public class CacheNotEnabledException extends RuntimeException {

    private static final long serialVersionUID = -3391119188036606250L;

    private static Logger log = Logger.getLogger(CacheNotEnabledException.class.getSimpleName());

    /**
     * @param message
     * @param cause
     */
    public CacheNotEnabledException(String message, Throwable cause) {
	super(message, cause);
	log.error(message, cause);
    }

    /**
     * @param message
     */
    public CacheNotEnabledException(String message) {
	super(message);
	log.error(message);
    }

    /**
     * @param cause
     */
    public CacheNotEnabledException(Throwable cause) {
	super(cause);
	log.error(cause);
    }



}
