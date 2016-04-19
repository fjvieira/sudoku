package com.vieira.sudoku.entity;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vieira.sudoku.exception.InvalidDimensionException;

/**
 * {@link SudokuGame} class test.
 * @author Fernando Jose Vieira
 *
 */
public class SudokuGameTests {
    
    private static Logger log = Logger.getLogger(SudokuGameTests.class.getSimpleName());

    private SudokuGame sudokuBoard;
    
    @Before
    public void constructBoard() throws InvalidDimensionException{
	int[][] boardArray = new int[9][];
	boardArray[0] = new int[]{1, 2, 0, 0, 0, 0, 0, 0, 0};
	boardArray[1] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	boardArray[2] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	boardArray[3] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	boardArray[4] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	boardArray[5] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	boardArray[6] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	boardArray[7] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	boardArray[8] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	sudokuBoard = new SudokuGame(boardArray);
    }
    
    @Test
    public void testValidateMovementOnColumn(){
	//resenting position
	Assert.assertTrue(sudokuBoard.validateMovementOnColumn(0, 0, 0));
	Assert.assertTrue(sudokuBoard.validateMovementOnColumn(3, 3, 0));
	//modifying a already filled position
	Assert.assertTrue(sudokuBoard.validateMovementOnColumn(0, 0, 1));
	//Checking if validation from a movement in another column
	Assert.assertTrue(sudokuBoard.validateMovementOnColumn(1, 1, 1));
	//Checking a invalid movement.
	Assert.assertFalse(sudokuBoard.validateMovementOnColumn(1, 0, 1));
    }
    
    @Test
    public void testValidateMovementOnRow(){
	//resenting position
	Assert.assertTrue(sudokuBoard.validateMovementOnRow(0, 0, 0));
	Assert.assertTrue(sudokuBoard.validateMovementOnRow(3, 3, 0));
	//modifying a already filled position
	Assert.assertTrue(sudokuBoard.validateMovementOnRow(0, 1, 2));
	//Checking if validation from a movement in another column
	Assert.assertTrue(sudokuBoard.validateMovementOnRow(1, 0, 2));
	//Checking a invalid movement.
	Assert.assertFalse(sudokuBoard.validateMovementOnRow(0, 2, 2));
    }

    @Test
    public void testGetSudokuBoxNumber() throws InvalidDimensionException{
	Assert.assertTrue(sudokuBoard.getSudokuBoxNumber(0, 0) == 0);
	Assert.assertTrue(sudokuBoard.getSudokuBoxNumber(2, 2) == 0);
	Assert.assertTrue(sudokuBoard.getSudokuBoxNumber(2, 3) == 1);
	Assert.assertTrue(sudokuBoard.getSudokuBoxNumber(2, 5) == 1);
	Assert.assertTrue(sudokuBoard.getSudokuBoxNumber(1, 6) == 2);
	Assert.assertTrue(sudokuBoard.getSudokuBoxNumber(3, 1) == 3);
	Assert.assertTrue(sudokuBoard.getSudokuBoxNumber(5, 5) == 4);
	Assert.assertTrue(sudokuBoard.getSudokuBoxNumber(4, 7) == 5);
	Assert.assertTrue(sudokuBoard.getSudokuBoxNumber(7, 1) == 6);
	Assert.assertTrue(sudokuBoard.getSudokuBoxNumber(7, 5) == 7);
	Assert.assertTrue(sudokuBoard.getSudokuBoxNumber(8, 8) == 8);
    }
    
    @Test
    public void testExecuteMovementOnBoard() throws InvalidDimensionException{
	int[][] boardArray = new int[9][];
	boardArray[0] = new int[]{7, 0, 0, 0, 4, 0, 5, 3, 0};
	boardArray[1] = new int[]{0, 0, 5, 0, 0, 8, 0, 1, 0};
	boardArray[2] = new int[]{0, 0, 8, 5, 0, 9, 0, 4, 0};
	boardArray[3] = new int[]{5, 3, 9, 0, 6, 0, 0, 0, 1};
	boardArray[4] = new int[]{0, 0, 0, 0, 1, 0, 0, 0, 5};
	boardArray[5] = new int[]{8, 0, 0, 7, 2, 0, 9, 0, 0};
	boardArray[6] = new int[]{9, 0, 7, 4, 0, 0, 0, 0, 0};
	boardArray[7] = new int[]{0, 0, 0, 0, 5, 7, 0, 0, 0};
	boardArray[8] = new int[]{6, 0, 0, 0, 0, 0, 0, 5, 0};
	
	sudokuBoard = new SudokuGame(boardArray);
	
	Assert.assertTrue(sudokuBoard.executeMovement(0, 0, 7).size() == 0);
	Assert.assertTrue(sudokuBoard.executeMovement(1, 1, 7).size() == 1);
	Assert.assertTrue(sudokuBoard.executeMovement(0, 1, 7).size() == 2);
	Assert.assertTrue(sudokuBoard.executeMovement(1, 0, 7).size() == 2);
	
	Assert.assertTrue(sudokuBoard.executeMovement(8, 8, 5).size() == 3);
	
	Assert.assertTrue(sudokuBoard.executeMovement(1, 1, 2).size() == 0);
	Assert.assertTrue(sudokuBoard.executeMovement(0, 1, 2).size() == 2);
	Assert.assertTrue(sudokuBoard.executeMovement(1, 0, 3).size() == 0);
    }
    
    @Test
    public void testCheckCompleteSolution() throws InvalidDimensionException {
	int[][] solutionArray = new int[9][];
	solutionArray[0] = new int[]{7, 9, 2, 1, 4, 6, 5, 3, 8};
	solutionArray[1] = new int[]{4, 6, 5, 2, 3, 8, 7, 1, 9};
	solutionArray[2] = new int[]{3, 1, 8, 5, 7, 9, 6, 4, 2};
	solutionArray[3] = new int[]{5, 3, 9, 8, 6, 4, 2, 7, 1};
	solutionArray[4] = new int[]{2, 7, 6, 9, 1, 3, 4, 8, 5};
	solutionArray[5] = new int[]{8, 4, 1, 7, 2, 5, 9, 6, 3};
	solutionArray[6] = new int[]{9, 5, 7, 4, 8, 1, 3, 2, 6};
	solutionArray[7] = new int[]{1, 2, 3, 6, 5, 7, 8, 9, 4};
	solutionArray[8] = new int[]{6, 8, 4, 3, 9, 2, 1, 5, 7};
	
	int[][] boardArray = new int[9][];
	boardArray[0] = new int[]{7, 0, 0, 0, 4, 0, 5, 3, 0};
	boardArray[1] = new int[]{0, 0, 5, 0, 0, 8, 0, 1, 0};
	boardArray[2] = new int[]{0, 0, 8, 5, 0, 9, 0, 4, 0};
	boardArray[3] = new int[]{5, 3, 9, 0, 6, 0, 0, 0, 1};
	boardArray[4] = new int[]{0, 0, 0, 0, 1, 0, 0, 0, 5};
	boardArray[5] = new int[]{8, 0, 0, 7, 2, 0, 9, 0, 0};
	boardArray[6] = new int[]{9, 0, 7, 4, 0, 0, 0, 0, 0};
	boardArray[7] = new int[]{0, 0, 0, 0, 5, 7, 0, 0, 0};
	boardArray[8] = new int[]{6, 0, 0, 0, 0, 0, 0, 5, 0};	
	sudokuBoard = new SudokuGame(boardArray);
	
	for (int i = 0; i < solutionArray.length; i++) {
	    for (int j = 0; j < solutionArray[i].length; j++) {
		Assert.assertTrue(sudokuBoard.executeMovement(i, j, solutionArray[i][j]).size() == 0);
	    }
	}
	
	Assert.assertTrue(sudokuBoard.checkCompleteSolution());
    }
}
