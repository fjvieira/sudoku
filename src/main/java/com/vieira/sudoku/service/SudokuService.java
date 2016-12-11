package com.vieira.sudoku.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vieira.sudoku.common.TimeoutCache;
import com.vieira.sudoku.exception.CacheNotEnabledException;
import com.vieira.sudoku.exception.InvalidDimensionException;
import com.vieira.sudoku.exception.SudokuAlgorithmException;
import com.vieira.sudoku.game.SudokuGame;
import com.vieira.sudoku.service.data.SudokuBoardMovement;
import com.vieira.sudoku.service.data.SudokuBoardMovementResult;
import com.vieira.sudoku.service.data.SudokuGameSession;

/**
 * Service class for the Sudoku game.
 * @author Fernando Jose Vieira
 *
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(SudokuService.URI_CONTEXT)
public class SudokuService {
    
    public static final String URI_CONTEXT = "/game";
    
    //Cache timeout in seconds
    private static final int CACHE_TIMEOUT = 3600;
    
    private static String ERROR_TYPE = "type";
    
    private static String ERROR_MESSAGE = "message";
    
    @Autowired
    @Qualifier("ExecutorServiceTimeoutCache")
    private TimeoutCache<String, SudokuGameSession> cache;
    
    /**
     * Service that return a new Sudoku Board placed in cache. 
     * @return {@link SudokuGameSession} with the board array and boardID.
     */
    @RequestMapping(method={RequestMethod.GET})
    public ResponseEntity<?> getGameBoard() {
	if(cache == null){
	    throw new CacheNotEnabledException("The application cache is not running");
	}
	
	SudokuGameSession board = SudokuGameSession.newInstance();
	cache.put(board.getId(), board, CACHE_TIMEOUT);
	
	return new ResponseEntity<SudokuGameSession>(board, HttpStatus.OK);
    }

    /**
     * @param movement {@link SudokuBoardMovement} A movement in a valid board.
     * @return {@link SudokuBoardMovementResult} for a movement in a valid board (HTTP 201) or Board Not found (HTTP 404). 
     */
    @RequestMapping(value="/{gameSessionID}", method={RequestMethod.PUT})
    public ResponseEntity<?> executeMovement(@PathVariable String gameSessionID, @RequestBody @Valid SudokuBoardMovement movement) {
	
	SudokuGameSession gameSession = cache.get(gameSessionID);
	if (gameSession == null) {

	    ModelMap map = new ModelMap();
	    map.addAttribute(ERROR_TYPE, "not_found");
	    map.addAttribute(ERROR_MESSAGE, "Game session ID not found.");

	    return new ResponseEntity<ModelMap>(map, HttpStatus.NOT_FOUND);
	    
	}
	
	SudokuBoardMovementResult result = new SudokuBoardMovementResult();	
	try {
	    SudokuGame sudokuGame = new SudokuGame(cache.get(gameSessionID).getBoard());
	    result.setErrors(sudokuGame.executeMovement(movement.getRow(), movement.getColumn(), movement.getValue()));
	    result.setCompleted(sudokuGame.checkCompleteSolution());
	    
	} catch (InvalidDimensionException e) {
	    throw new SudokuAlgorithmException("Exception executing Sudoku algorithm", e);
	}
	
	
	return new ResponseEntity<SudokuBoardMovementResult>(result, HttpStatus.OK);
    }
    
}
