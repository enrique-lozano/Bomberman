
import edu.uc3m.game.GameBoardGUI;

/** The Game class is the engine of the game, in it is the while loop that controls the movement of the character and the cycles of the game. */
public class Game {

	/** Number of enemies generated in each level */
	private int ballons;

	/** Constructor */
	public Game() {}

	/*---------------------------------------------
	-----------------CLASS METHODS-----------------
	-----------------------------------------------*/

	/** Generate and organize the items in the board */
	public static void generateItems (Item bonus_bomba[], int bonus_bomba_colocar, Item bonus_fuego, Item bonus_fuego_completo, Item remoteControl, Item patines, Item geta, Board Board, int size, int nivel) {
		/** Since the items are going to be represented on the board by sprites, each one must have a different id associated with it. The item ids range from 9 to whatever is needed. */
		int id = 9;
		int i, j; // Square X and Y.

		/* Bonus bomba Type 1*/
		int contador = 0;
		while(bonus_bomba_colocar != 0) { // Mientras queden items bomba que colocar, seguimos generando.
			i = (int) (Math.random() * size);
			j = (int) (Math.random() * size);
			if(Board.getBoard()[i][j].getSquareType() == 3 && !Board.getBoard()[i][j].isBonus()) { // If the square where it is generated is a brick and there is no previous bonus generated, we establish a bonus in that box.
				Board.getBoard()[i][j].setBonus(true);
				bonus_bomba[contador] = new Item(id, i, j, 1, false); // We create the corresponding bonus bomb in the position of the array.
				++contador;
				--bonus_bomba_colocar;
				++id;
			}
		}

		/* Bonus fuego Type 2 */
		do {
			i = (int) (Math.random()*size);
			j = (int) (Math.random()*size);
		} while(Board.getBoard()[i][j].getSquareType() != 3 || Board.getBoard()[i][j].isBonus()); // In order for the item to be generated, we must generate it on a brick that does not contain a previous bonus.
		++id;
		Board.getBoard()[i][j].setBonus(true);
		bonus_fuego.setId(id);
		bonus_fuego.setType(2);
		bonus_fuego.setX(i);
		bonus_fuego.setY(j);
		bonus_fuego.setAplicated(false);

		/* Bonus fuego completo Type 3 */
		if (nivel%5 == 1) { // There is one every 5 levels
			do {
				i = (int) (Math.random()*size);
				j = (int) (Math.random()*size);
			} while(Board.getBoard()[i][j].getSquareType() != 3 || Board.getBoard()[i][j].isBonus());
			++id;
			Board.getBoard()[i][j].setBonus(true);
			bonus_fuego_completo.setId(id);
			bonus_fuego_completo.setType(3);
			bonus_fuego_completo.setX(i);
			bonus_fuego_completo.setY(j);
			bonus_fuego_completo.setAplicated(false);
		}

		/* Bonus Control Remoto Type 4 */
		if(nivel%10 == 1) { // There is one every 5 levels
			do {
				i = (int) (Math.random()*size);
				j = (int) (Math.random()*size);
			} while(Board.getBoard()[i][j].getSquareType() != 3 || Board.getBoard()[i][j].isBonus());
			++id;
			Board.getBoard()[i][j].setBonus(true);
			remoteControl.setId(id);
			remoteControl.setType(3);
			remoteControl.setX(i);
			remoteControl.setY(j);
			remoteControl.setAplicated(false);
		}

		/* Bonus patines Type 5 */
		boolean fortuna1 = true;
		int fortuna2 = (int)(Math.random() * 2 + 1); // 50% of possibilities of appearing
		if (fortuna2 == 1) {
			fortuna1 = false;
		}
		if(nivel%2 == 0 && fortuna1) { // If we are at an even level and it has been randomly decided that there is a bonus, we generate it.
			do {
				i = (int) (Math.random() * size);
				j = (int) (Math.random() * size);
			} while(Board.getBoard()[i][j].getSquareType() != 3 || Board.getBoard()[i][j].isBonus());
			++id;
			Board.getBoard()[i][j].setBonus(true);
			patines.setId(id);
			patines.setType(5);
			patines.setX(i);
			patines.setY(j);
			patines.setAplicated(false);
		}

		/*Bonus Geta Type 6 */ 
		int fortuna3 = (int)(Math.random()* 5 + 1); // 1/5 probability
		if( fortuna3 == 1) { 
			do {
				i = (int) (Math.random() * size);
				j = (int) (Math.random() * size);
			} while(Board.getBoard()[i][j].getSquareType() != 3 || Board.getBoard()[i][j].isBonus());
			++id;
			Board.getBoard()[i][j].setBonus(true);
			geta.setId(id);
			geta.setType(6);
			geta.setX(i);
			geta.setY(j);
			geta.setAplicated(false);
		}
	}

	/** Depending on the time that has passed since the bomb was put in, we will execute one code or another. This method controls the performance of the bomb */
	public static void putBomb(int range, Board Board, GameBoardGUI gui, Bomb [] Bomb, int control_detonacion) {
		for(int j = 0; j < Bomb.length; j++) {

			// If more than 3 seconds have passed since the bomb was placed, we will explode and eliminate the corresponding bricks
			if(Bomb[j] != null && (System.currentTimeMillis() - Bomb[j].getStartTick()) > 3000) {
				Board.getBoard()[Bomb[j].getX()][Bomb[j].getY()].setPassable(true);	
				boolean destroyUp = true;
				boolean destroyDown = true;
				boolean destroyRight = true;
				boolean destroyLeft = true;
				if(Board.getBoard() [Bomb[j].getX()][Bomb[j].getY()].getSquareType() == 1 ) {
					for(int i = 0; i <= range; i++) {
						if( Bomb[j].getX()+i < Board.getBoard()[Bomb[j].getX()].length) {
							if (Board.getBoard()[Bomb[j].getX() + i][Bomb[j].getY()].getSquareType() == 2) {
								destroyRight = false;
							}
							if (Board.getBoard()[Bomb[j].getX() + i][Bomb[j].getY()].isDestructible() && destroyRight) {
								Board.placeEmptySquare(Bomb[j].getX() + i, Bomb[j].getY());
							}
						}
						if(Bomb[j].getX()-i > 0) {
							if (Board.getBoard()[Bomb[j].getX() - i][Bomb[j].getY()].getSquareType() == 2) {
								destroyLeft = false;
							}
							if (Board.getBoard()[Bomb[j].getX() - i][Bomb[j].getY()].isDestructible() && destroyLeft) {
								Board.placeEmptySquare(Bomb[j].getX() - i, Bomb[j].getY());
							}}
						if( Bomb[j].getY()+i < Board.getBoard()[Bomb[j].getY()].length) {
							if (Board.getBoard()[Bomb[j].getX()][Bomb[j].getY() + 1].getSquareType() == 2) {
								destroyDown = false;
							}
							if (Board.getBoard()[Bomb[j].getX()][Bomb[j].getY() + i].isDestructible() && destroyDown) {
								Board.placeEmptySquare(Bomb[j].getX(), Bomb[j].getY() + i);
							}}     
						if(Bomb[j].getY()-i > 0) {
							if (Board.getBoard()[Bomb[j].getX()][Bomb[j].getY() - 1].getSquareType() == 2) {
								destroyUp = false;
							}
							if (Board.getBoard()[Bomb[j].getX()][Bomb[j].getY() -i].isDestructible() && destroyUp) {
								Board.placeEmptySquare(Bomb[j].getX(), Bomb[j].getY() - i);
							}
						}
					}
				}
			}

			// The bomb cannot be traversed, however, as it is easy for the user to get trapped in the bomb box as soon as he puts it in, we give him 1.5 seconds to get out of this box before making it non-walkable.
			if(Bomb[j] != null && (System.currentTimeMillis() - Bomb[j].getStartTick()) < 3000 && (System.currentTimeMillis() - Bomb[j].getStartTick()) > 1500 ) {
				Board.getBoard()[Bomb[j].getX()][Bomb[j].getY()].setPassable(false);	
			}

			// If less than 3 seconds have passed since the pump was put on, it should blink, thanks to the variable that helps us to blink.
			if(Bomb[j] != null && (System.currentTimeMillis() - Bomb[j].getStartTick()) < 3000) {
				gui.gb_addSprite(Bomb[j].getId(), "bomb" + (control_detonacion%2 + 1) +  ".gif", false);
				gui.gb_moveSprite(Bomb[j].getId(), Bomb[j].getX(), Bomb[j].getY());
				gui.gb_setSpriteVisible(Bomb[j].getId(), true);
			}
		}
	}

	public static void printBoard (Board Board, GameBoardGUI gui, int size) {
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				if(Board.getBoard()[i][j].getSquareType() != 0) {
					if(Board.getBoard()[i][j].isDetonated()) {  // The detonated squares are removed from the images they may have and are repainted.
						gui.gb_setSquareImage(i, j, null);
					}
					switch(Board.getBoard()[i][j].getSquareType()) { 
					case 2:
						gui.gb_setSquareImage(i, j, "wall.gif");
						break;
					case 3:
						gui.gb_setSquareImage(i, j, "bricks.gif");
						break;

					default:
						gui.gb_setSquareColor(i, j, 50, 255, 50 );
						break;
					}
				}
			}
		}
	}

	public static void printCharacter(Bomberman Bomberman, GameBoardGUI gui, String imagen) { 
		gui.gb_addSprite(1, imagen, true); 
		gui.gb_moveSpriteCoord(1, Bomberman.getX(), Bomberman.getY());
		gui.gb_setSpriteVisible(1, true);
	}

	public static void printBomb (Bomb [] Bomb, GameBoardGUI gui, int control_detonacion, Bomberman Bomberman, Board Board) {
		for ( int i = 0; i < Bomb.length; i++) {
			if (Bomb[i] != null && (System.currentTimeMillis() - Bomb[i].getStartTick()) > 3000 && (System.currentTimeMillis() - Bomb[i].getStartTick()) < 6000) { // Si la bomba debe estar explotando, es decir, han pasado al menos 3 segundos desde que fue colocada, imprimimos la explosi�n.
				boolean destroyUp = true;
				boolean destroyDown = true;
				boolean destroyRight = true;
				boolean destroyLeft = true;
				gui.gb_setSpriteVisible(Bomb[i].getId(), false);
				gui.gb_setSquareImage(Bomb[i].getX(), Bomb[i].getY(), "explosion_C" + (control_detonacion%4 + 1) + ".gif");
				/*
				* To know in which boxes we must print the explosion, we have designed the following code structure that depends on the range of the * bomb and whether or not to find walls in the path of the explosion.
				* We have described the algorithm structure in detail in the bomberman class, Heath_check method.
				* The variable control_detonation allows us to make the bomb blink, because thanks to (control_detonation% 4 +1) we can take
				* advantage of the structure of the explosion to animate it correctly, thus making it possible to flicker.
				*/
				for (int j = 1; j <= Bomberman.getRange(); j++) {
					/* Right */
					if( Bomb[i].getX()+j < Board.getBoard()[Bomb[i].getX()].length) {
						if (Board.getBoard()[ Bomb[i].getX() + j][Bomb[i].getY()].getSquareType() == 2) {
							destroyRight = false;
						}
						if (destroyRight) {
							gui.gb_setSquareImage(Bomb[i].getX() + j, Bomb[i].getY(), "explosion_H" + (control_detonacion%4 +1) + ".gif");
						}
						if (j==Bomberman.getRange() && destroyRight){
							gui.gb_setSquareImage(Bomb[i].getX() + j, Bomb[i].getY(), "explosion_E" + (control_detonacion%4 + 1) + ".gif");
						}
					}
					/* Left */
					if(Bomb[i].getX() - j > 0) {
						if (Board.getBoard()[Bomb[i].getX() - j][Bomb[i].getY()].getSquareType() == 2) {
							destroyLeft = false;
						}
						if (destroyLeft) {
							gui.gb_setSquareImage(Bomb[i].getX() - j, Bomb[i].getY(), "explosion_H" + (control_detonacion%4 + 1) + ".gif");
						}
						if (j==Bomberman.getRange() && destroyLeft){
							gui.gb_setSquareImage(Bomb[i].getX() - j, Bomb[i].getY(), "explosion_W" + (control_detonacion%4 + 1) + ".gif");
						}
					}
					/* Down */
					if( Bomb[i].getY() + j < (Board.getBoard()[Bomb[i].getY()].length - 1)) {
						if (Board.getBoard()[Bomb[i].getX()][Bomb[i].getY() + 1].getSquareType() == 2) {
							destroyDown = false;
						}
						if (destroyDown) {
							gui.gb_setSquareImage(Bomb[i].getX(), Bomb[i].getY() + j, "explosion_V" + (control_detonacion%4 + 1) + ".gif");
						}
						if (j==Bomberman.getRange() && destroyDown){
							gui.gb_setSquareImage(Bomb[i].getX(), Bomb[i].getY() + j, "explosion_S" + (control_detonacion%4 + 1) + ".gif");
						}
					}     
					/* Up */
					if(Bomb[i].getY()-j > 0) {
						if (Board.getBoard()[Bomb[i].getX()][Bomb[i].getY() - 1].getSquareType() == 2) {
							destroyUp = false;
						}
						if (destroyUp) {
							gui.gb_setSquareImage(Bomb[i].getX(), Bomb[i].getY() -  j, "explosion_V" + (control_detonacion%4 + 1) + ".gif");
						}
						if (j==Bomberman.getRange() && destroyUp){
							gui.gb_setSquareImage(Bomb[i].getX(), Bomb[i].getY() -  j, "explosion_N" + (control_detonacion%4 + 1) + ".gif");
						}
					}
				}
			}
			
			// Dissapear of the explosion:
			if (Bomb[i] != null && (System.currentTimeMillis() - Bomb[i].getStartTick()) > 6000) { 
				/* Once the bomb has finished making the explosion (more than 6 seconds) we must make the images of the explosion disappear. The structure of the algorithm is exactly the same but we make the images disappear by setting the name to null. */
				boolean destroyUp = true;
				boolean destroyDown = true;
				boolean destroyRight = true;
				boolean destroyLeft = true;
				gui.gb_setSquareImage(Bomb[i].getX(), Bomb[i].getY(), null );
				for (int j = 1; j <= Bomberman.getRange(); j++) {
					/* Right */
					if( Bomb[i].getX()+j < Board.getBoard()[Bomb[i].getX()].length) {
						if (Board.getBoard()[ Bomb[i].getX() + j][Bomb[i].getY()].getSquareType() == 2) {
							destroyRight = false;
						}
						if (destroyRight) {
							gui.gb_setSquareImage(Bomb[i].getX() + j, Bomb[i].getY(), null);
						}
						if (j==Bomberman.getRange() && destroyRight){
							gui.gb_setSquareImage(Bomb[i].getX() + j, Bomb[i].getY(), null);
						}
					}
					/* Left */
					if(Bomb[i].getX() - j > 0) {
						if (Board.getBoard()[Bomb[i].getX() - j][Bomb[i].getY()].getSquareType() == 2) {
							destroyLeft = false;
						}
						if (destroyLeft) {
							gui.gb_setSquareImage(Bomb[i].getX() - j, Bomb[i].getY(), null);
						}
						if (j==Bomberman.getRange() && destroyLeft){
							gui.gb_setSquareImage(Bomb[i].getX() - j, Bomb[i].getY(), null);
						}
					}
					/* Down */
					if( Bomb[i].getY() + i < Board.getBoard()[Bomb[i].getY()].length) {
						if (Board.getBoard()[Bomb[i].getX()][Bomb[i].getY() + 1].getSquareType() == 2) {
							destroyDown = false;
						}
						if (destroyDown) {
							gui.gb_setSquareImage(Bomb[i].getX(), Bomb[i].getY() + j, null);
						}
						if (j==Bomberman.getRange() && destroyDown){
							gui.gb_setSquareImage(Bomb[i].getX(), Bomb[i].getY() + j, null);
						}
					}   
					/* Up */
					if(Bomb[i].getY()-j > 0) {
						if (Board.getBoard()[Bomb[i].getX()][Bomb[i].getY() - 1].getSquareType() == 2) {
							destroyUp = false;
						}
						if (destroyUp) {
							gui.gb_setSquareImage(Bomb[i].getX(), Bomb[i].getY() -  j, null );
						}
						if (j==Bomberman.getRange() && destroyUp){
							gui.gb_setSquareImage(Bomb[i].getX(), Bomb[i].getY() -  j, null);
						}
					}
				}
				gui.gb_println("La bomba " +  (i) + " ha explotado.");
				Bomb[i] = null; // The bomb has already done everything it had to do and we left the free space in the array.
			}
		}
	}

	/** Print the items whose bricks have been detonated so that the user can see them. */
	public static void printItems (Item bonus_bomba [], Item bonus_fuego , Item bonus_fuego_completo, Item remoteControl, Item patines, Item geta, GameBoardGUI gui, Board Board) {

		/* Type 1 */
		for(int i = 0; i < bonus_bomba.length; i++) {
			if(bonus_bomba[i] != null && Board.getBoard()[bonus_bomba[i].getX()][bonus_bomba[i].getY()].isDetonated() && !Board.getBoard()[bonus_bomba[i].getX()][bonus_bomba[i].getY()].isDoor() && !bonus_bomba[i].isAplicated()) { // If the box meets the following conditions, that is, the bonus has not been previously applied and the brick it was on has been detonated, then we will show the bonus so that the user can see it.

				// False as it must be positioned at the bottom so that it is below the bomberman.
				gui.gb_addSprite(bonus_bomba[i].getId(), "Bombupsprite.png", false);
				gui.gb_moveSprite(bonus_bomba[i].getId(), bonus_bomba[i].getX(), bonus_bomba[i].getY());
				gui.gb_setSpriteVisible(bonus_bomba[i].getId(), true);
			}
		}

		/* Type 2 */
		if(Board.getBoard()[bonus_fuego.getX()][bonus_fuego.getY()].isDetonated() && !Board.getBoard()[bonus_fuego.getX()][bonus_fuego.getY()].isDoor() && !bonus_fuego.isAplicated()) { // Las condiciones se repiten para todos los bonus.
			gui.gb_addSprite(bonus_fuego.getId(), "Fullfiresprite.png", false);
			gui.gb_moveSprite(bonus_fuego.getId(), bonus_fuego.getX(), bonus_fuego.getY());
			gui.gb_setSpriteVisible(bonus_fuego.getId(), true);
		}

		/* Type 3 */
		if(Board.getBoard()[bonus_fuego_completo.getX()][bonus_fuego_completo.getY()].isDetonated() && !Board.getBoard()[bonus_fuego_completo.getX()][bonus_fuego_completo.getY()].isDoor() && !bonus_fuego_completo.isAplicated()) {
			gui.gb_addSprite(bonus_fuego_completo.getId(), "Fireupsprite.png", false);
			gui.gb_moveSprite(bonus_fuego_completo.getId(), bonus_fuego_completo.getX(), bonus_fuego_completo.getY());
			gui.gb_setSpriteVisible(bonus_fuego_completo.getId(), true);
		}
		/* Type 4 */
		if(Board.getBoard()[remoteControl.getX()][remoteControl.getY()].isDetonated() && !Board.getBoard()[remoteControl.getX()][remoteControl.getY()].isDoor() && !remoteControl.isAplicated()) {
			gui.gb_addSprite(remoteControl.getId(), "Remote_Control_2.png", false);
			gui.gb_moveSprite(remoteControl.getId(), remoteControl.getX(), remoteControl.getY());
			gui.gb_setSpriteVisible(remoteControl.getId(), true);
		}

		/* Type 5 */
		if(Board.getBoard()[patines.getX()][patines.getY()].isDetonated() && !Board.getBoard()[patines.getX()][patines.getY()].isDoor() && !patines.isAplicated()) {
			gui.gb_addSprite(patines.getId(), "Skatesprite.png", false);
			gui.gb_moveSprite(patines.getId(), patines.getX(), patines.getY());
			gui.gb_setSpriteVisible(patines.getId(), true);
		}

		/* Type 6 */
		if(Board.getBoard()[geta.getX()][geta.getY()].isDetonated() && !Board.getBoard()[geta.getX()][geta.getY()].isDoor() && !geta.isAplicated()) {
			gui.gb_addSprite(geta.getId(), "Getasprite.png", false);
			gui.gb_moveSprite(geta.getId(), geta.getX(), geta.getY());
			gui.gb_setSpriteVisible(geta.getId(), true);
		}
	}

	/** Print the door, it is located in an already detonated square. */
	public static void printDoor (Board Board, GameBoardGUI gui, int size) { 
		// Door is the sprite 2.
		for(int i = 0; i < size; i++) { // Find the door in the squares
			for(int j = 0; j < size; j++) {
				if(Board.getBoard()[i][j].isDoor() && Board.getBoard()[i][j].isDetonated()) { 
					gui.gb_addSprite(2, "DoorClosed.png", false); // False as it will be below the bomberman, visually speaking.
					gui.gb_moveSprite(2, i, j);
					gui.gb_setSpriteVisible(2, true);
				}
			}
		}
	}

	/** Randomly spawn enemies on the board. */
	public static void generateEnemies (Enemy globos [], Board Board, int size) {
		int i, j;
		int id = 100; // Enemies have IDs starting at 100. They have IDs as they are going to be represented by sprites.
		for(int k = 0; k < globos.length; k++) {
			do {
				i = (int) (Math.random()*(size));
				j = (int) (Math.random()* (size));
			} while(Board.getBoard()[i][j].getSquareType() != 1); // You can only spawn a balloon in an empty space.
			
			globos[k] = new Enemy(i, j, 20, 20, 100, 1, 1, id, true, "enemy100.png", 0, 0);
			++id;
		}
	}

	public static void printEnemies (GameBoardGUI gui, Enemy globos []) {
		for(int i = 0; i < globos.length; i++) {
			if (globos[i].isAlive()) { // Print sprite of each alive balloon
				gui.gb_addSprite(globos[i].getId(), globos[i].getImagen(), true);
				gui.gb_moveSprite(globos[i].getId(), globos[i].getX(), globos[i].getY());
				gui.gb_setSpriteVisible(globos[i].getId(), true);
			}
			else {
				gui.gb_setSpriteVisible(globos[i].getId(), false);
			}
			/* Until now we worked with squares, now we do it with coordinates, so we are going to multiply by 10 so that later we can move the balloons correctly. */
			globos[i].setX(globos[i].getX()*10);
			globos[i].setY(globos[i].getY()*10);
		}
	}

	/** Animation and movement of the enemies */
	public static void moveEnemies(Board Board, GameBoardGUI gui, int size,  Enemy globos []) { // Animac�n y movimiento de los enemigos.

		int direction_movimiento_aleatorio; // Varible que tomar� valores aleatorios para lograr as� el movimiento completamante aleatorio y err�tico de los enemigos globos.
		for(int i = 0; i < globos.length; i++) { // Recorremos el array de globos.
			if (globos[i].isAlive()) { // Solo si el globo est� alive lo moveremos.
				direction_movimiento_aleatorio = (int)(Math.random() * 4 + 1); // 1 = Right, 2 = Down, 3 = Up, 4 = Left. Generamos directiones aleatorias para lograr movimiento err�tico de los globos.
				/*
				 * An�logamente al caso del bomberman debemos manejar la estructura de las im�genes del los globos para lograr animarlos, es por ello que contamos con los atributos frame y direcci�n al igual que con el bomberman. En este caso cada globo tiene uno diferente.
				 */
				/* Right */
				if(direction_movimiento_aleatorio == 1 && Board.getBoard()[globos[i].calcX(globos[i].getX() + globos[i].getSpeed(), size)][globos[i].calcY(globos[i].getY(), size)].isPassable()) {
					globos[i].setDirection(2);
					if(globos[i].getFrame()<3) {
						globos[i].setFrame(globos[i].getFrame() + 1);;
						globos[i].setImagen("enemy1" + globos[i].getDirection() + "" + globos[i].getFrame() + ".png");
						gui.gb_setSpriteImage(globos[i].getId(), globos[i].getImagen());
					}
					else {
						globos[i].setFrame(1);;
						globos[i].setImagen("enemy1" + globos[i].getDirection() + "" + globos[i].getFrame() + ".png");
						gui.gb_setSpriteImage(globos[i].getId(), globos[i].getImagen());

					}
					globos[i].setX(globos[i].getX() + globos[i].getSpeed());
					gui.gb_moveSpriteCoord(globos[i].getId(), globos[i].getX(), globos[i].getY());
				}
				/* Down */
				if(direction_movimiento_aleatorio == 2 && Board.getBoard()[globos[i].calcX(globos[i].getX(), size)][globos[i].calcY(globos[i].getY() + globos[i].getSpeed(), size)].isPassable()) {
					globos[i].setDirection(2);
					if(globos[i].getFrame()<3) {
						globos[i].setFrame(globos[i].getFrame() + 1);;
						globos[i].setImagen("enemy1" + globos[i].getDirection() + "" + globos[i].getFrame() + ".png");
						gui.gb_setSpriteImage(globos[i].getId(), globos[i].getImagen());
					}
					else {
						globos[i].setFrame(1);;
						globos[i].setImagen("enemy1" + globos[i].getDirection() + "" + globos[i].getFrame() + ".png");
						gui.gb_setSpriteImage(globos[i].getId(), globos[i].getImagen());

					}
					globos[i].setY(globos[i].getY() + globos[i].getSpeed());
					gui.gb_moveSpriteCoord(globos[i].getId(), globos[i].getX(), globos[i].getY());
				}
				/* Up */
				if(direction_movimiento_aleatorio == 3 && Board.getBoard()[globos[i].calcX(globos[i].getX(), size)][globos[i].calcY(globos[i].getY() - globos[i].getSpeed(), size)].isPassable()) {
					globos[i].setDirection(1);
					if(globos[i].getFrame()<3) {
						globos[i].setFrame(globos[i].getFrame() + 1);;
						globos[i].setImagen("enemy1" + globos[i].getDirection() + "" + globos[i].getFrame() + ".png");
						gui.gb_setSpriteImage(globos[i].getId(), globos[i].getImagen());
					}
					else {
						globos[i].setFrame(1);;
						globos[i].setImagen("enemy1" + globos[i].getDirection() + "" + globos[i].getFrame() + ".png");
						gui.gb_setSpriteImage(globos[i].getId(), globos[i].getImagen());

					}
					globos[i].setY(globos[i].getY() - globos[i].getSpeed());
					gui.gb_moveSpriteCoord(globos[i].getId(), globos[i].getX(), globos[i].getY());
				}
				/* Left */
				if(direction_movimiento_aleatorio == 4 && Board.getBoard()[globos[i].calcX(globos[i].getX() - globos[i].getSpeed(), size)][globos[i].calcY(globos[i].getY(), size)].isPassable()) {
					globos[i].setDirection(1);
					if(globos[i].getFrame()<3) {
						globos[i].setFrame(globos[i].getFrame() + 1);;
						globos[i].setImagen("enemy1" + globos[i].getDirection() + "" + globos[i].getFrame() + ".png");
						gui.gb_setSpriteImage(globos[i].getId(), globos[i].getImagen());
					}
					else {
						globos[i].setFrame(1);;
						globos[i].setImagen("enemy1" + globos[i].getDirection() + "" + globos[i].getFrame() + ".png");
						gui.gb_setSpriteImage(globos[i].getId(), globos[i].getImagen());
					}
					globos[i].setX(globos[i].getX() - globos[i].getSpeed());
					gui.gb_moveSpriteCoord(globos[i].getId(), globos[i].getX(), globos[i].getY());
				}
			}
			else {
				gui.gb_setSpriteVisible(globos[i].getId(), false);
			}
		}
	}

	/** This method handles one of the while conditions. That all the enemies are dead is one of the conditions to pass the level. */
	public boolean they_are_alive (Enemy globos []){
		boolean they_are_alive = true;
		for(int i = 0; i < globos.length; i++) {
			if(globos[i].isAlive()) {
				they_are_alive = false;
			}
		}
		return they_are_alive;	
	}

	/** To go to the next level. For any board position other than wall, we erase all its content and paint it as an empty box. */
	public static void cleanBoard (GameBoardGUI gui, Board Board) {
		gui.gb_clearCommandBar();
		gui.gb_clearConsole();
		gui.gb_clearSprites();
		for(int i = 0; i < Board.getBoard().length; i++) { // Iterate over the board
			for(int j = 0; j < Board.getBoard().length; j++) {
				if(Board.getBoard()[i][j].getSquareType() != 2) {
					gui.gb_setSquareImage(i, j, null);
					gui.gb_setSquareColor(i, j, 50, 255, 50);
				}
			}
		}
	}

	/** This method contains the main loop that contains the game cycles. */
	public void createLevel (int nivel, GameBoardGUI gui, int size, Bomberman Bomberman, Board Board) throws InterruptedException {

		/* Game data on the GUI screen */
		gui.gb_println("Bomberman");
		gui.setVisible(true);
		gui.gb_setPortraitPlayer("White_Bomberman_R.png");
		gui.gb_setTextPlayerName("Bomberman");
		gui.gb_setTextPointsUp("Points");
		gui.gb_setTextPointsDown("Bombs");
		gui.gb_setTextAbility1("Range");
		gui.gb_setTextAbility2("Speed");
		gui.gb_setValueLevel(nivel + 1);
		Bomberman.setTime(System.currentTimeMillis());

		// We create the items that will be found on this board:
		int bonus_bomba_colocar = (int)(Math.random() * 5 + 1);
		Item bonus_bomba [] = new Item [bonus_bomba_colocar]; // Organizamos los bonus bomba existentes en un array de items bonus bomba.
		Item bonus_fuego = new Item(); // Creamos todos los objetos items restantes.
		Item bonus_fuego_completo = new Item();
		Item remoteControl = new Item ();
		Item patines = new Item();
		Item geta = new Item();
		generateItems(bonus_bomba, bonus_bomba_colocar,bonus_fuego, bonus_fuego_completo, remoteControl, patines, geta, Board, size, nivel); 

		// Create the enemies:
		ballons = (int)(Math.random() * 10 + 1);
		Enemy globos [] = new Enemy [ballons];
		generateEnemies(globos, Board, size);

		// We print the elements we need to start the game
		printEnemies(gui, globos);
		printBoard(Board, gui, size);
		String image = "bomberman111.png"; // This variable allows us to perform the bomberman animation.
		printCharacter(Bomberman, gui, image); // The bomberman is sprite 1

		
		// Variables that we need to control the execution of the movement and the game:
		
		/** This variable is increased by one unit in each iteration of the loop, it takes successive values 1, 2, 3 and 4 and allows us to perform the flashing of the bomb and the explosion. When it becomes 4 it is reset to 1 with an if at the end of the loop. */
		int control_detonacion = 0; 
		/** Variable that allows us to know the number of bombs that the player has placed and that are in play. */
		int bombNumber = 0;
		/** Array to organize the bombs available to the player */
		Bomb [] Bomb = new Bomb [5]; 
		// Auxiliary variables that help us organize the animation of the bomberman's movement:
		int direction = 0;
		int frame = 1;
		int bomb_id = 3; // Las bombs tambien son sprites luego tienen su id  asociado, hemos establecido que los Ids de las Bombs vayan de 3 a 8.

		/*---- While of movement and execution of the game. Game engine ----*/
		while((Bomberman.checkLevel(Board, size) || !they_are_alive(globos)) && (Bomberman.getCanContinue())){ 
			/* Main loop. It does not stop executing until the level is passed, that is, until the bomberman is located at the door with all the enemies dead and the bomberman alive or if the bomberman dies (canContinue = false) the execution is finished of the while. */

			/* Updating the bomberman attributes on the user GUI screen. */
			gui.gb_setValuePointsUp(Bomberman.getPuntos());
			gui.gb_setValuePointsDown(Bomberman.getBombs() - bombNumber);
			gui.gb_setValueAbility1(Bomberman.getRange());
			gui.gb_setValueAbility2(Bomberman.getSpeed());
			gui.gb_setValueHealthCurrent(Bomberman.getVida_actual());
			gui.gb_setValueHealthMax(Bomberman.getVida_maxima());

			/* Move engine */
			String lastAction = gui.gb_getLastAction().trim(); // Cature the keyboard
			if (lastAction.length() > 0){
				/* Right */
				if (lastAction.equals("right") && Board.getBoard()[Bomberman.calcX(Bomberman.getX() + Bomberman.getSpeed(), size)][Bomberman.calcY(Bomberman.getY(), size)].isPassable()){ 
					direction = 3;
					if(frame<5) { // This structure allows us to move the bomberman in a natural way, taking advantage of the structure of the movement of the images, since they are ordered. And the numbers at the end organize the direction and specific frame in which the bomberman is in each iteration. The structure is repeated for each direction.
						frame++;
						image = "bomberman1" + direction + "" + frame + ".png";
						gui.gb_setSpriteImage(1, image);
					}
					else {
						frame = 1;
						image = "bomberman1" + direction + "" + frame + ".png";
						gui.gb_setSpriteImage(1, image);
					}
					Bomberman.setX(Bomberman.getX() + Bomberman.getSpeed()); // Depending on the speed the bomberman will move more or less fast
					gui.gb_moveSpriteCoord(1, Bomberman.getX(), Bomberman.getY()); // MoveSpriteCoord is a way to move the sprite, without moving from square to square, it is a progressive movement. From one box to another there are 10 subspaces.
				}
				/* Down */
				else if (lastAction.equals("down") && Board.getBoard()[Bomberman.calcX(Bomberman.getX(), size)][Bomberman.calcY(Bomberman.getY() + Bomberman.getSpeed(), size)].isPassable()){
					direction = 1;
					if(frame < 5) {
						frame++;
						image = "bomberman1" + direction + "" + frame + ".png";
						gui.gb_setSpriteImage(1, image);
					}
					else {
						frame = 1;
						image = "bomberman1" + direction + "" + frame + ".png";
						gui.gb_setSpriteImage(1, image);
					}
					Bomberman.setY(Bomberman.getY() + Bomberman.getSpeed());
					gui.gb_moveSpriteCoord(1, Bomberman.getX(), Bomberman.getY() );

				}
				/* Up */
				else if(lastAction.equals("up") && Board.getBoard()[Bomberman.calcX(Bomberman.getX(), size)][Bomberman.calcY(Bomberman.getY() - Bomberman.getSpeed(), size)].isPassable()) { 
					direction = 0;
					if(frame < 5) {
						frame++;
						image = "bomberman1" + direction + "" + frame + ".png";
						gui.gb_setSpriteImage(1, image);
					}
					else {
						frame = 1;
						image = "bomberman1" + direction + "" + frame + ".png";
						gui.gb_setSpriteImage(1, image);
					}
					Bomberman.setY(Bomberman.getY() - Bomberman.getSpeed() );
					gui.gb_moveSpriteCoord(1, Bomberman.getX(), Bomberman.getY());
				}
				/* Left */
				else if(lastAction.equals("left") && Board.getBoard()[Bomberman.calcX(Bomberman.getX() - Bomberman.getSpeed(), size)][Bomberman.calcY(Bomberman.getY(), size)].isPassable()) {
					direction = 2;
					if(frame < 5) {
						frame++;
						image = "bomberman1" + direction + "" + frame + ".png";
						gui.gb_setSpriteImage(1, image);
					}
					else {
						frame = 1;
						image = "bomberman1" + direction + "" + frame + ".png";
						gui.gb_setSpriteImage(1, image);
					}
					Bomberman.setX(Bomberman.getX() - Bomberman.getSpeed());
					gui.gb_moveSpriteCoord(1, Bomberman.getX(), Bomberman.getY());
				}
				else if(lastAction.equals("space")) { // Space to put a bomb
					if(Bomb[bombNumber] == null && bombNumber < Bomberman.getBombs()) { // If we have not exceeded the maximum number of bombs available to the bomberman at any given time, we put a bomb.
						Bomb [bombNumber] = new Bomb(bomb_id, Bomberman.calcX(Bomberman.getX(), size), Bomberman.calcY(Bomberman.getY(), size), 7,  System.currentTimeMillis(), true); // We create the specific bomb by filling in its attributes, emphasizing that we capture the specific moment of time in which the bomb is placed.
						
						++bombNumber;
						++bomb_id;
						gui.gb_println(
							"You put a bomb in the position: " + Bomberman.calcX(Bomberman.getX(), size) + 
							", " + Bomberman.calcY(Bomberman.getY(), size));
					}
				}
				else if(lastAction.equals("tab") && remoteControl.isAplicated()) { // If the Tab key has been pressed and Bomberman has collected the remote control bonus, we enter.
					for (int i = 0; i < Bomb.length; i++) {
						if(Bomb[i] != null) {
							Bomb[i].setStartTick((System.currentTimeMillis() - 3000)); // In this way, the existing bombs on the board will be exploited as they will fulfill the time conditions established in the Print Bomb method.
						}
					}
				}

				printCharacter(Bomberman, gui, image); // We only reprint the bomberman if it moves.
			}

			++control_detonacion; // The variable is incremented with each iteration to allow us to blink.
			printBoard(Board, gui, size); // We reprint the board to apply the changes that may have been made.
			putBomb(Bomberman.getRange(), Board, gui, Bomb, control_detonacion);
			printItems(bonus_bomba, bonus_fuego, bonus_fuego_completo, remoteControl, patines, geta, gui, Board); 
			printDoor(Board, gui, size); 
			printBomb(Bomb, gui, control_detonacion, Bomberman, Board); // Bomb animation management
			Bomberman.healthCheck(Bomb, Board, size, gui);
			Bomberman.bonusCheck(Board, gui, bonus_bomba, bonus_fuego, bonus_fuego_completo, remoteControl, patines, geta, size);
			moveEnemies(Board, gui, size, globos);

			/* Manejo de las variables que nos permiten saber el n�mero de bombs en juego y realizar la animaci�n de bombs, explosiones y enemigos. */
			if(control_detonacion > 3) {
				control_detonacion = 0;
			}
			if(bombNumber == 5 || bombNumber == Bomberman.getBombs()) {
				bombNumber = 0;
			}
			if(bomb_id == 8) { // Bombs id from 3 to 8.
				bomb_id = 3;
			}
			for (int i = 0; i < globos.length; i++) { 
				globos[i].healthCheck(Bomb, Board, size, gui, Bomberman);
			}
			Bomberman.enemyDamage(globos, size, gui); 
			Bomberman.checkAlive(gui);
			Thread.sleep(50L); // We wait 50 milliseconds to repeat the while loop again.
		}

		// Only if the bomberman is still alive can we indicate that the level has been completed and assign points by time.
		if(Bomberman.getVida_actual() > 0) { 
			gui.gb_showMessageDialog("Nivel Completado");
			Bomberman.timePoints(gui);
		}

		cleanBoard(gui, Board);
	}
}