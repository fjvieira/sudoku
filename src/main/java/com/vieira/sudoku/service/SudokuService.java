package com.vieira.sudoku.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vieira.sudoku.common.TimeoutCache;
import com.vieira.sudoku.exception.CacheNotEnabledException;
import com.vieira.sudoku.exception.InvalidDimensionException;
import com.vieira.sudoku.exception.SudokuAlgorithmException;
import com.vieira.sudoku.game.SudokuGame;
import com.vieira.sudoku.service.data.SudokuBoard;
import com.vieira.sudoku.service.data.SudokuBoardMovement;
import com.vieira.sudoku.service.data.SudokuBoardMovementResult;

/**
 * Service class for the Sudoku game.
 * @author Fernando Jose Vieira
 *
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/game")
public class SudokuService {
    //Cache timeout in minutes
    private static final int CACHE_TIMEOUT = 3600;
    
    private static final Logger LOG = LoggerFactory.getLogger(SudokuService.class);
    
    @Autowired
    @Qualifier("ExecutorServiceTimeoutCache")
    private TimeoutCache<String, SudokuBoard> cache;
    
    /**
     * Service that return a new Sudoku Board placed in cache. 
     * @return {@link SudokuBoard} with the board array and boardID.
     */
    @RequestMapping(method={RequestMethod.GET})
    public ResponseEntity<?> getGameBoard() {
	if(cache == null){
	    throw new CacheNotEnabledException("The application cache is not running");
	}
	
	SudokuBoard board = SudokuBoard.newInstance();
	cache.put(board.getId(), board, CACHE_TIMEOUT);
	
	return new ResponseEntity<SudokuBoard>(board, HttpStatus.OK);
    }

    /**
     * @param movement {@link SudokuBoardMovement} A movement in a valid board.
     * @return {@link SudokuBoardMovementResult} for a movement in a valid board (HTTP 201) or Board Not found (HTTP 404). 
     */
    @RequestMapping(method={RequestMethod.PUT})
    public ResponseEntity<?> executeMovement(@RequestBody SudokuBoardMovement movement) {
	
	SudokuBoard board = cache.get(movement.getId());
	if (board == null) {
	    return new ResponseEntity<String>("Board ID not found.", HttpStatus.NOT_FOUND);
	}
	
	SudokuBoardMovementResult result = new SudokuBoardMovementResult();	
	try {
	    SudokuGame sudokuGame = new SudokuGame(cache.get(movement.getId()).getBoard());
	    result.setErrors(sudokuGame.executeMovement(movement.getRow(), movement.getColumn(), movement.getValue()));
	    result.setCompleted(sudokuGame.checkCompleteSolution());
	    
	} catch (InvalidDimensionException e) {
	    throw new SudokuAlgorithmException("Exception executing Sudoku algorithm", e);
	}
	
	
	return new ResponseEntity<SudokuBoardMovementResult>(result, HttpStatus.OK);
    }
    
}
