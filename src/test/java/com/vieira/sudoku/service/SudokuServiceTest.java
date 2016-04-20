package com.vieira.sudoku.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.vieira.sudoku.SudokuApplication;
import com.vieira.sudoku.exception.InvalidDimensionException;
import com.vieira.sudoku.game.SudokuGame;
import com.vieira.sudoku.service.data.SudokuBoard;
import com.vieira.sudoku.service.data.SudokuBoardMovement;
import com.vieira.sudoku.service.data.SudokuBoardMovementResult;

/**
 * Rest Service integration test.
 * @author Fernando Jose Vieira
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SudokuApplication.class)
@WebIntegrationTest(randomPort = true)
public class SudokuServiceTest {
    
    @Value("${local.server.port}")
    private int port;
    
    private RestTemplate restTemplate = new TestRestTemplate();

    /**
     * @param isJerseyAPI
     * @return
     */
    private String getURL(){
	StringBuilder url = new StringBuilder("http://localhost:");
	url.append(port).append("/game/");
	return url.toString();
    }
    
    private ResponseEntity<SudokuBoard> getGameBoard() {
	return restTemplate.exchange(getURL(),HttpMethod.GET, null, SudokuBoard.class);
    }
    
    /**
     * 
     */
    @Test
    public void testGetGameBoard() {
	ResponseEntity<SudokuBoard> response = getGameBoard();
	
	Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	Assert.assertNotNull(response.getBody().getId());
	Assert.assertTrue(response.getBody().getBoard() instanceof int[][]);
    }

    @Test
    public void testExecuteMovements() {
	ResponseEntity<SudokuBoard> response = getGameBoard();

	String boardId = response.getBody().getId();
	int[][] boardArray = response.getBody().getBoard();
	
	
	HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	
	HttpEntity<SudokuBoardMovement> request = null;
	
	SudokuBoardMovement movement = new SudokuBoardMovement(boardId, 0, 0, 7);
	request = new HttpEntity<SudokuBoardMovement>(movement, httpHeaders);
	ResponseEntity<SudokuBoardMovementResult> movementResponse = 
		restTemplate.exchange(getURL(),HttpMethod.PUT, request, SudokuBoardMovementResult.class);
	Assert.assertTrue(movementResponse.getBody().getErrors().isEmpty());
	
	movement = new SudokuBoardMovement(boardId, 1, 0, 7);
	request = new HttpEntity<SudokuBoardMovement>(movement, httpHeaders);
	movementResponse = 
		restTemplate.exchange(getURL(),HttpMethod.PUT, request, SudokuBoardMovementResult.class);
	Assert.assertFalse(movementResponse.getBody().getErrors().isEmpty());

    }

    @Test
    public void testInvalidBoardId() {
	HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);	
	SudokuBoardMovement movement = new SudokuBoardMovement("12121212", 0, 0, 7);
	HttpEntity<SudokuBoardMovement> request = new HttpEntity<SudokuBoardMovement>(movement, httpHeaders);
	ResponseEntity<String> movementResponse = 
		restTemplate.exchange(getURL(),HttpMethod.PUT, request, String.class);
	Assert.assertEquals(movementResponse.getStatusCode(), HttpStatus.NOT_FOUND);
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
	
	ResponseEntity<SudokuBoard> response = getGameBoard();

	String boardId = response.getBody().getId();
	int[][] boardArray = response.getBody().getBoard();
	
	
	HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	
	HttpEntity<SudokuBoardMovement> request = null;
	ResponseEntity<SudokuBoardMovementResult> movementResponse =  null;
	
	for (int i = 0; i < solutionArray.length; i++) {
	    for (int j = 0; j < solutionArray[i].length; j++) {
		SudokuBoardMovement movement = new SudokuBoardMovement(boardId, i, j, solutionArray[i][j]);
		request = new HttpEntity<SudokuBoardMovement>(movement, httpHeaders);
		movementResponse = restTemplate
			.exchange(getURL(),HttpMethod.PUT, request, SudokuBoardMovementResult.class);
		Assert.assertTrue(movementResponse.getBody().getErrors().isEmpty());

	    }
	}
	
	Assert.assertTrue(movementResponse.getBody().isCompleted());
    }
    
}
