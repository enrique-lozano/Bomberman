public class Board  {

	/** Matrix of squares, will make up the board */
	private Square [][] Board; 

	/** Constructor */
	public Board (int size) {
		this.Board = new Square [size][size];
		generateWalls(size);
		generateBricks(size);
		generateDoor(size); 
	}

	/*---------------------------------------------
	-----------------CLASS METHODS-----------------
	-----------------------------------------------*/

	/** For a given box it sets the attributes of an empty box. */
	public void placeEmptySquare (int i, int j) {
		Board [i][j].setPassable(true);
		Board [i][j].setDestructible(false);
		Board [i][j].setSquareType(1);
		Board [i][j].setDetonated(true);
	}

	/** Given the size of the square array, it will generate the walls in their positions, which we know previously. */
	public void generateWalls (int size) {
		for (int i = 0; i < size; i++){ // Iterate over the board
			for (int j = 0; j < size; j++){
				if (i == 0 || j == 0 || i == (size - 1) || j == (size - 1) || (i % 2 == 0 && j % 2 == 0)){ // Casos en los que se debe establecer la casilla correspondiente del array Board como muro. Es decir en los bordes y las casillas con coordenadas pares.
					Board [i][j] = new Square (); // Creamos la casilla y establecemos sus caracter�sticas.
					Board [i][j].setPassable(false);
					Board [i][j].setBonus(false);
					Board [i][j].setDestructible(false);
					Board [i][j].setDoor(false);
					Board [i][j].setDetonated(false);
					Board [i][j].setSquareType(2);
				}
				else{ // En caso contrario introducir un espacio vacio, con sus correspondientes carracter�sticas.
					Board [i][j] = new Square ();
					Board [i][j].setPassable(true);
					Board [i][j].setBonus(false);
					Board [i][j].setDestructible(false);
					Board [i][j].setDoor(false);
					Board [i][j].setDetonated(false);
					Board [i][j].setSquareType(1);
				}
			}
		}
	}

	public void generateBricks(int size) { // Dado el tama�o del array tablero generamos los ladrillos de manera aleatoria.

		int ladrillos = (int)(Math.random()*11 + 50); // Numero aleatorio  de ladrillos a crear entre 50 y 
		while (ladrillos > 0) { 
			// We generate a random board position to place the brick.
			int x = (int)(Math.random()*size);
			int y = (int)(Math.random()*size);
			// Cases in which we could have a brick. We cannot generate bricks in the squares where the Bomberman appears after each level, nor where there is a wall
			if (Board [x][y].isPassable() && (x != 1 || y != 1) && (x != 1 || y != 2) && (x != 2 || y != 1)) {
				Board [x][y] = new Square ();
				Board [x][y].setPassable(false);
				Board [x][y].setBonus(false);
				Board [x][y].setDestructible(true);
				Board [x][y].setDoor(false);
				Board [x][y].setDetonated(false);
				Board [x][y].setSquareType(3);
				ladrillos--; 
				// Brick generated.
			}          
		}
	}

	/** Create the door in a random square, which cannot be a brick */
	public void generateDoor (int size) {

		int i, j;
		do {
			i = (int) (Math.random()*size);
			j = (int) (Math.random()*size);
		}while(Board [i][j].getSquareType() != 3);
		Board[i][j].setDoor(true);
		Board[i][j].setBonus(false); // We can no longer generate bonuses in that box.
	}

	/*---------------------------------------------
	---------------GETTERS AND SETTERS-------------
	-----------------------------------------------*/

	public Square[][] getBoard() {
		return Board;
	}
}
