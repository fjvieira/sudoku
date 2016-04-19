package com.vieira.sudoku.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vieira.sudoku.exception.InvalidBoxException;
import com.vieira.sudoku.exception.InvalidDimensionException;
import com.vieira.sudoku.exception.SudokuAlgorithmException;

/**
 * A representation of a valid Sudoku board (i.e. a board that contains a 9x9 matrix that is abstract divided in 9 3x3 boxes).
 * @author Fernando Jose Vieira
 *
 */
public class SudokuGame {

    private int[][] boardArray;

    /**
     * Board constructor.
     * @param boardArray A valid Sudoku board.
     * @throws InvalidDimensionException Must be a 9x9 Array.
     */
    public SudokuGame(int[][] boardArray) throws InvalidDimensionException {
	if(boardArray.length != 9 && boardArray[0].length != 9){
	    throw new InvalidDimensionException("Sudoku Board size must be exactly 9x9");
	}

	this.boardArray = boardArray;
    }

    /**
     * Validate and execute the movement. If it there are errors, it will be returned in a List.
     * @param positionRow Relative row position.
     * @param positionColumn Relative column position.
     * @param value a valid value (between 0 and 9).
     * @return A list with possible errors.
     */
    public List<WrongMovementError> executeMovement(int positionRow, int positionColumn, int value){
	int boxNumber = getSudokuBoxNumber(positionRow, positionColumn);

	List<WrongMovementError> errorList = new ArrayList<>();

	//Validate if the movement is valid on the Box.
	SudokuBox box = getSudokuBox(boxNumber);
	int relativePositionRow = positionRow % 3;
	int relativePositionColumn = positionColumn % 3;

	if(positionRow == 1 && positionColumn == 4){
	    System.out.println("ssss");
	}
	if (!box.executeMovement(relativePositionRow, relativePositionColumn, value)){
	    errorList.add(new WrongMovementError(WrongMovementError.BOX_ERROR_TYPE, "Error validating movement in the box", boxNumber));
	}
	//Validate if the movement is valid on the row.
	if (!validateMovementOnRow(positionRow, positionColumn, value)){
	    errorList.add(new WrongMovementError(WrongMovementError.ROW_ERROR_TYPE, "Error validating movement in the row", positionRow));
	}
	//Validate if the movement is valid on the column.
	if (!validateMovementOnColumn(positionRow, positionColumn, value)){
	    errorList.add(new WrongMovementError(WrongMovementError.COLUMN_ERROR_TYPE, "Error validating movement in the column", positionColumn));
	}

	if(errorList.size() == 0) {
	    boardArray[positionRow][positionColumn] = value;
	}


	return errorList;
    }


    /**
     * Check if the solution is complete.
     * @return True if the solution is complete, otherwise false.
     */
    public boolean checkCompleteSolution(){
	for (int i = 0; i < boardArray.length; i++) {
	    if (Arrays.binarySearch(boardArray[i], 0) > -1){
		return false;
	    }
	}
	return true;
    }

    /**
     * Validates the movements on the column. 
     * @param positionRow Relative row position.
     * @param positionColumn Relative column position.
     * @param value a valid value (between 0 and 9).
     * @return Boolean that represents if it was valid or not.
     */
    public boolean validateMovementOnColumn(int positionRow, int positionColumn, int value){
	if (value != 0) {
	    for (int j = 0; j < boardArray[positionColumn].length; j++) {
		if(boardArray[j][positionColumn] == value && j != positionRow){
		    return false;
		}
	    }
	}

	return true;
    }

    /**
     * Validates the movements on the row.
     * @param positionRow Relative row position.
     * @param positionColumn Relative column position.
     * @param value a valid value (between 0 and 9).
     * @return Boolean that represents if it was valid or not.
     */
    public boolean validateMovementOnRow(int positionRow, int positionColumn, int value){
	if (value != 0) {
	    for (int i = 0; i < boardArray.length; i++) {
		if(boardArray[positionRow][i] == value && i != positionColumn){
		    return false;
		}
	    }
	}

	return true;
    }

    /**
     * Calculates the number of corresponding box of position.
     * @param positionRow Relative row position.
     * @param positionColumn Relative column position.
     * @return The number of corresponding box.
     */
    public int getSudokuBoxNumber (int positionRow, int positionColumn){
	return 3 * (positionRow / 3) + (positionColumn / 3); 

    }

    /**
     * Return the Box representation according to the index.
     * @param boxNumber A zero based index (0-8).
     * @return A {@link SudokuBox} object representing the box requested.
     */
    public SudokuBox getSudokuBox (int boxNumber){
	int[][] boxArray = new int[3][3];

	int positionRow = 3 * (boxNumber / 3);
	int positionColumn = 3 * (boxNumber % 3);

	for (int i = 0; i < boxArray.length; i++) {
	    for (int j = 0; j < boxArray.length; j++) {
		boxArray[i][j] = boardArray[positionRow + i][positionColumn + j];
	    }
	}

	SudokuBox box = null;
	try {
	    box = new SudokuBox(boxArray);
	} catch (InvalidDimensionException | InvalidBoxException e) {
	    throw new SudokuAlgorithmException("Error trying to get a Sudoku Box", e);
	}

	return box;
    }

}