package com.vieira.sudoku.service.data;

/**
 * Representation of a movement in an active board.
 * @author Fernando Jose Vieira
 *
 */
public class SudokuBoardMovement {

    private String id;
    
    private int row;
    
    private int column;
    
    private int value;
    
    /**
     * Constuctor.
     * @param id Board id.
     * @param row Row.
     * @param column Column.
     * @param value Value.
     */
    public SudokuBoardMovement(String id, int row, int column, int value) {
	super();
	this.id = id;
	this.row = row;
	this.column = column;
	this.value = value;
    }

    /**
     * Default constructor.
     */
    public SudokuBoardMovement(){
	
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
    
}
