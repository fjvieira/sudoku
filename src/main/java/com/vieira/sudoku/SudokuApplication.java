package com.vieira.sudoku;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Application Initializer.
 * @author Fernando Jose Vieira
 *
 */
@SpringBootApplication
public class SudokuApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(
	    SpringApplicationBuilder application) {

	return application.sources(SudokuApplication.class);
    }

    public static void main(String[] args) {
	new SudokuApplication().configure(
		new SpringApplicationBuilder(SudokuApplication.class)).run(args);

    }
}