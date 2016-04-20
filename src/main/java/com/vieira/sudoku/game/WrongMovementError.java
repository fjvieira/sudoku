package com.vieira.sudoku.game;

/**
 * Object that represents a error caught by the game algorithm.
 * @author Fernando Jose Vieira
 *
 */
public class WrongMovementError {
    
    public static final int BOX_ERROR_TYPE = 0;
    
    public static final int ROW_ERROR_TYPE = 1;
    
    public static final int COLUMN_ERROR_TYPE = 2;
    
    private int type;
    
    private String description;
        
    private int detail;

    /**
     * Object constructor.
     * @param type Type of error, Box=0, Row=1, Column=2.
     * @param description Optional description of this error.
     * @param detail Box, Row or Column number.
     */
    public WrongMovementError(int type, String description, int detail) {
	this.type = type;
	this.description = description;
	this.detail = detail;
    }

    /**
     * Default constructor.
     */
    public WrongMovementError(){
	
    }
    
    /**
     * @return the boxErrorType
     */
    public static int getBoxErrorType() {
        return BOX_ERROR_TYPE;
    }

    /**
     * @return the rowErrorType
     */
    public static int getRowErrorType() {
        return ROW_ERROR_TYPE;
    }

    /**
     * @return the columnErrorType
     */
    public static int getColumnErrorType() {
        return COLUMN_ERROR_TYPE;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the detail
     */
    public int getDetail() {
        return detail;
    }
    
}
