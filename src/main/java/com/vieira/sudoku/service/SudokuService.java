package com.vieira.sudoku.service;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vieira.sudoku.service.data.SudokuBoard;

@RestController
@EnableAutoConfiguration
@RequestMapping("/game")
public class SudokuService {

    @RequestMapping(method={RequestMethod.GET})
    public ResponseEntity<?> getGameBoard() {
	return new ResponseEntity<SudokuBoard>(SudokuBoard.newInstance(), HttpStatus.OK);
    }
    
}
