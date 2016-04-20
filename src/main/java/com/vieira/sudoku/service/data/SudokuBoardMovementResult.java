package com.vieira.sudoku.service.data;

import java.util.List;

import com.vieira.sudoku.game.WrongMovementError;

/**
 * Represents the result of a movement in a valid Board.
 * @author Fernando Jose Vieira
 *
 */
public class SudokuBoardMovementResult {

    private boolean completed;
    
    private boolean valid;
    
    private List<WrongMovementError> errors;

    
    /**
     * Constructor.
     * @param completed Indicates if the movement completed the solution.
     * @param valid Indicates if the movement was valid.
     * @param errors List of errors when the movement is not valid.
     */
    public SudokuBoardMovementResult(boolean completed, boolean valid, List<WrongMovementError> errors) {
	super();
	this.completed = completed;
	this.valid = valid;
	this.errors = errors;
    }

    /**
     * Default constructor.
     */
    public SudokuBoardMovementResult(){
	
    }
    
    /**
     * @return the completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * @return the valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * @return the errors
     */
    public List<WrongMovementError> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(List<WrongMovementError> errors) {
        this.errors = errors;
        this.valid = errors.size() == 0; 
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "SudokuBoardMovementResult [completed=" + completed + ", valid=" + valid + ", errors=" + errors + "]";
    }
    
    
    
}
