package com.vieira.sudoku.service.data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representation of a movement in an active board.
 * 
 * @author Fernando Jose Vieira
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SudokuBoardMovement {

    @NotNull
    @Min(0)
    @Max(8)
    private int row;

    @NotNull
    @Min(0)
    @Max(8)
    private int column;

    @NotNull
    @Min(0)
    @Max(9)
    private int value;

}
