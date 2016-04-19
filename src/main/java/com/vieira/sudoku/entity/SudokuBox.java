package com.vieira.sudoku.entity;

import com.vieira.sudoku.exception.InvalidDimensionException;
import com.vieira.sudoku.exception.InvalidBoxException;

/**
 * This class represents a Box, (also called "block", "region", or "sub-square") and contains all of the digits from 1 to 9.
 * The default value for a "empty" position is zero.
 * @author Fernando Jose Vieira
 *
 */
public class SudokuBox {

    private int[][] boxArray;

    /**
     * Constructs a Box representation from a matrix (array).
     * @param boxArray A valid Sudoku box.
     * @throws InvalidDimensionException Must be a 3x3 Array.
     */
    public SudokuBox(int[][] boxArray) throws InvalidDimensionException, InvalidBoxException {
	if(boxArray.length != 3 && boxArray[0].length != 3){
	    throw new InvalidDimensionException("Sudoku Box size must be exactly 3x3");
	}
//	if (this.checkCompleteCorrectness(boxArray)){
	    this.boxArray = boxArray;	    
//	} else {
//	    throw new InvalidBoxException("The matrix provided is a not valid Sudoku Box");   
//	}

    }


    /**
     * It executes a movement. A valid movement inside a box must include a number in a position that does not exist in any other position.
     * Or a zero (reseting a position).
     * @param positionRow Relative row position.
     * @param positionColumn Relative column position.
     * @param value a valid value (between 0 and 9).
     * @return Boolean that represents if it was valid or not.
     */
    public boolean executeMovement(int positionRow, int positionColumn, int value) {
	if (value != 0) {
	    for (int i = 0; i < boxArray.length; i++) {
		for (int j = 0; j < boxArray[i].length; j++) {

		    if(boxArray[i][j] == value && (i != positionRow | j != positionColumn)){
			return false;
		    }
		}
	    }
	}
	boxArray[positionRow][positionColumn] = value;
	return true;
    }

    /**
     * It Validates the entire box.
     * @return Boolean that represents if it was valid or not.
     */
    public boolean checkCompleteCorrectness(int[][] boxArray){
	int[] checkArray = new int[10];
	for (int i = 0; i < checkArray.length; i++) {
	    checkArray[i] = 0;
	}

	for (int i = 0; i < boxArray.length; i++) {
	    for (int j = 0; j < boxArray[i].length; j++) {
		int positionValue = boxArray[i][j]; 
		if(positionValue != 0 && (positionValue < 0 || positionValue > 9 || checkArray[positionValue] == 1)){
		    return false;
		} else {
		    checkArray[positionValue] = 1;
		}

	    }
	}
	return true;
    }

}
