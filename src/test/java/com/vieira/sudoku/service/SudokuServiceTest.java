package com.vieira.sudoku.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.vieira.sudoku.SudokuApplication;
import com.vieira.sudoku.service.data.SudokuBoard;

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

}
