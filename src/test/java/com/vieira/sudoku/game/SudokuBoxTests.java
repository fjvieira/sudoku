package com.vieira.sudoku.game;

import org.junit.Assert;
import org.junit.Test;

import com.vieira.sudoku.exception.InvalidBoxException;
import com.vieira.sudoku.exception.InvalidDimensionException;

/**
 * {@link SudokuBox} class test.
 * @author Fernando Jose Vieira
 *
 */
public class SudokuBoxTests {

    @Test
    public void testCheckBoxCompleteCorrectness() throws InvalidBoxException, InvalidDimensionException {
	int [][] boxArray = new int[3][];
	boxArray[0] = new int[] {1,2,3};
	boxArray[1] = new int[] {4,5,6};
	boxArray[2] = new int[] {7,8,9};

	SudokuBox box = new SudokuBox(boxArray);
	//test a correct and complete box.
	Assert.assertTrue(box.checkCompleteCorrectness(boxArray));
	boxArray[2] = new int[] {1,2,3};
	boxArray[0] = new int[] {7,8,9};
	Assert.assertTrue(box.checkCompleteCorrectness(boxArray));
	//test a incorrect but complete box.
	boxArray[0] = new int[] {1,9,3};
	Assert.assertFalse(box.checkCompleteCorrectness(boxArray));
	//test a incomplete box.
	boxArray[0] = new int[] {0,9,3};
	Assert.assertFalse(box.checkCompleteCorrectness(boxArray));
	boxArray[0] = new int[] {0,0,3};
	Assert.assertFalse(box.checkCompleteCorrectness(boxArray));
	boxArray[0] = new int[] {0,2,0};
	boxArray[1] = new int[] {4,0,6};
	boxArray[2] = new int[] {0,8,0};
	Assert.assertTrue(box.checkCompleteCorrectness(boxArray));
    }
    
    @Test
    public void testMoviments() throws InvalidBoxException, InvalidDimensionException {

	int [][] boxArray = new int[3][];
	boxArray[0] = new int[] {0,2,0};
	boxArray[1] = new int[] {4,0,6};
	boxArray[2] = new int[] {0,8,0};

	SudokuBox box = new SudokuBox(boxArray);
	//testing 1
	Assert.assertTrue(box.executeMovement(0, 0, 1));
	Assert.assertTrue(box.executeMovement(0, 0, 1));
	
	Assert.assertFalse(box.executeMovement(0, 1, 1));
	Assert.assertFalse(box.executeMovement(1, 0, 1));
	
	Assert.assertFalse(box.executeMovement(2, 2, 1));
	//testing 2
	Assert.assertTrue(box.executeMovement(0, 1, 2));
	
	Assert.assertFalse(box.executeMovement(0, 0, 2));
	Assert.assertFalse(box.executeMovement(2, 0, 2));
	
	Assert.assertFalse(box.executeMovement(2, 2, 2));
	//testing position resets.
	Assert.assertTrue(box.executeMovement(0, 0, 0));
	Assert.assertTrue(box.executeMovement(0, 1, 0));
	Assert.assertTrue(box.executeMovement(1, 1, 0));
    
    }
	
}
