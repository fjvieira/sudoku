package com.vieira.sudoku.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;
import com.vieira.sudoku.service.data.SudokuBoardMovement;
import com.vieira.sudoku.service.data.SudokuBoardMovementResult;
import com.vieira.sudoku.service.data.SudokuGameSession;

/**
 * Rest Service integration test.
 * 
 * @author Fernando Jose Vieira
 *
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@SpringBootTest
public class SudokuServiceTest {

    @Autowired
    private MockMvc mockMvc;

    private SudokuGameSession getGameBoard() throws Exception {
        MvcResult result = mockMvc.perform(get(SudokuService.URI_CONTEXT).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        return new Gson().fromJson(result.getResponse().getContentAsString(), SudokuGameSession.class);
    }

    /**
     * @throws Exception
     * 
     */
    @Test
    public void testGetGameBoard() throws Exception {
        mockMvc.perform(get(SudokuService.URI_CONTEXT).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("board").isArray());
    }

    @Test
    public void testExecuteMovements() throws Exception {
        SudokuGameSession gameSession = getGameBoard();

        SudokuBoardMovement movement = new SudokuBoardMovement(0, 0, 7);
        
        mockMvc.perform(put(SudokuService.URI_CONTEXT + "/{gameSessionID}", gameSession.getId())
                .content(new Gson().toJson(movement))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("completed").value(false))
                .andExpect(jsonPath("valid").value(true))
                .andExpect(jsonPath("errors").isEmpty());

        movement = new SudokuBoardMovement(1, 0, 7);

        mockMvc.perform(put(SudokuService.URI_CONTEXT + "/{gameSessionID}", gameSession.getId())
                .content(new Gson().toJson(movement))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("completed").value(false))
                .andExpect(jsonPath("valid").value(false))
                .andExpect(jsonPath("errors").isNotEmpty());

    }

    @Test
    public void testInvalidBoardId() throws Exception {
        SudokuBoardMovement movement = new SudokuBoardMovement(0, 0, 7);
        
        mockMvc.perform(put(SudokuService.URI_CONTEXT + "/{gameSessionID}", "23233")
                .content(new Gson().toJson(movement))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        
    }

    @Test
    public void testCheckCompleteSolution() throws Exception {
        int[][] solutionArray = new int[9][];
        solutionArray[0] = new int[] { 7, 9, 2, 1, 4, 6, 5, 3, 8 };
        solutionArray[1] = new int[] { 4, 6, 5, 2, 3, 8, 7, 1, 9 };
        solutionArray[2] = new int[] { 3, 1, 8, 5, 7, 9, 6, 4, 2 };
        solutionArray[3] = new int[] { 5, 3, 9, 8, 6, 4, 2, 7, 1 };
        solutionArray[4] = new int[] { 2, 7, 6, 9, 1, 3, 4, 8, 5 };
        solutionArray[5] = new int[] { 8, 4, 1, 7, 2, 5, 9, 6, 3 };
        solutionArray[6] = new int[] { 9, 5, 7, 4, 8, 1, 3, 2, 6 };
        solutionArray[7] = new int[] { 1, 2, 3, 6, 5, 7, 8, 9, 4 };
        solutionArray[8] = new int[] { 6, 8, 4, 3, 9, 2, 1, 5, 7 };

        String gameSessionId = getGameBoard().getId();

        MvcResult result = null;
        Gson gson = new Gson();
        
        for (int i = 0; i < solutionArray.length; i++) {
            for (int j = 0; j < solutionArray[i].length; j++) {
                SudokuBoardMovement movement = new SudokuBoardMovement(i, j, solutionArray[i][j]);
                
                result = mockMvc.perform(put(SudokuService.URI_CONTEXT + "/{gameSessionID}", gameSessionId)
                        .content(gson.toJson(movement))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("valid").value(true))
                        .andExpect(jsonPath("errors").isEmpty())
                        .andReturn();
                
            }
        }

        SudokuBoardMovementResult movementResult = gson.fromJson(result.getResponse().getContentAsString(), SudokuBoardMovementResult.class);
        
        Assert.assertTrue(movementResult.isCompleted());
    }

}
