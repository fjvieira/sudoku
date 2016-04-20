# Sudoku API

Sudoku API is Sudoku game algorithm app constructed to enable multiple clients to interact simultaneously with the server. 
Main functionalities:

  - Sudoku board (9x9 board) and board ID generation;
  - Sudoku board Cache (in-memory, with 60 minutes timeout);
  - Sudoku movement (one step by each request) and progress validation;

## Version
**1.0.0**

## Premises

In order to implement the Sudoku algorithm, it was assumed:

  - Sudoku board will be not generated ( it will be one board version in the first version);
  - The client will always send valid values of row, colum and value;
  - Value is a number between 1 and 9. Zero means empty.
  - The value 0 means a empty position in Sudoku table;
  - It is possible to setting a column as empty (sending 0 as a value);
  - Row is a zero based index;
  - Column is a zero based index;

## Services

The services are avaliable thought the /game relative URL.

##### GET

Send a GET request will result in a response with a new Sudoku boards that contains the array representing the game and the board ID.

**Response message example**

    {
        "id":"8e0e0839-5f61-481e-8a7f-c28da6be55ea",
        "board":[[7,0,0,0,4,0,5,3,0],[0,0,5,0,0,8,0,1,0],[0,0,8,5,0,9,0,4,0],[5,3,9,0,6,0,0,0,1],[0,0,0,0,1,0,0,0,5],[8,0,0,7,2,0,9,0,0],[9,0,7,4,0,0,0,0,0],[0,0,0,0,5,7,0,0,0],[6,0,0,0,0,0,0,5,0]]
    }

##### PUT

Send a PUT request will be understood as a movement. A movement consists of a position (row and column), a value and a valid Board ID.

**Request message Example**

    {
        "id":"8e0e0839-5f61-481e-8a7f-c28da6be55ea",
        "row": 1,
        "column": 1,
        "value" : 1
    }

Where: 
- id is the Board ID;
- row is the row position (zero based index);
- column is the column position (zero based index);

**Response message Example**

***For an invalid board ID (HTTP 400 Status)***

It is a premise to receive just valid values from clients. 
Just in case, it was added a default validation with a custom message.

	{
		"type": "validation_error",
		"message": "The message contain errors.",
		"fields": {
			"value": "must be lower or equal to 9"
		}
	}

***For an invalid board ID (HTTP 404 Status)***

	{
		"type": "not_found",
		"message": "Board ID not found."
	}


***For a valid board ID (HTTP 200 Status)***

    {
        "completed": false,
        "valid": false,
        "errors": [
            {
                "type": 1,
                "description": "Error validating movement in the row",
                "detail": 1
            }
        ]
    }

Where: 
- Completed is a flag indicating if the board is completed and corrected filled;
- Valid is a flag indicating if the movement was valid;
- Error is a array of movement validation errors. An error object can be understood in the following table.

    | Type | Meaning | Detail |
    |------|--------|---|
    | 0 | Error in the 3x3 Box | Box number (zero based index) |
    | 1 | Error in the row | Row number |
    | 2 | Error in the column | Column number |


## Tech

It was used the following technologies:

* Java 1.8
* Spring Boot
* Spring Framework
* Apache Maven
* JUnit


### Bulding and packging

You will need Maven to build this project. You can build the package using the following command on the project root directory:

    mvn clean package

### Executing

There are two ways of running this application.

The first one is for running it through Maven:

    mvn spring-boot:run

The second one is for running the jar package (located in the target directory after a packging) directly:

    java -jar sudoku-1.0.0.jar

### License
MIT






