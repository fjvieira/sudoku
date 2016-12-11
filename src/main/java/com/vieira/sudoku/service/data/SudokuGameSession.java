package com.vieira.sudoku.service.data;

import java.util.UUID;

/**
 * Represents an unique instance of a sudoku game identified by a unique id. 
 * @author Fernando Jose Vieira
 *
 */
public class SudokuGameSession {

    private String id;
    
    private int [][] board;

    private SudokuGameSession(){
	
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
     * @return the board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(int[][] board) {
        this.board = board;
    }
    
    public static SudokuGameSession newInstance(){
	SudokuGameSession sudokuGameSession = new SudokuGameSession();
	sudokuGameSession.setId(UUID.randomUUID().toString());
	
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
	sudokuGameSession.setBoard(boardArray);
	
	return sudokuGameSession;
    }
    
}
