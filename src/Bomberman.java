
import edu.uc3m.game.GameBoardGUI;

public class Bomberman extends Character { 

	/** Number of bombs avalaible */
	private int bombs;
	/** Range of the bombs */
	private int range;
	/** Boolean variable that lets us know if the game can continue. We cannot generate another new level if the Bomberman's health is less than or equal to 0. This attribute makes it easy for us to check this. */
	private boolean canContinue; 
	/** Save the moment when the bomberman enters in a level */
	private long time;

	/*Constructor*/
	public Bomberman (int x, int y, int currentLife, int maxLife, int points, int speed, int bombs, int range, boolean canContinue, long time) {

		super (x, y, currentLife, maxLife, points, speed); // Character inheritance
		super.x = 10;
		super.y = 10;
		this.range = range;
		this.bombs = bombs;
		this.canContinue = canContinue;
		this.time = time;
	}

	/*---------------------------------------------
	-----------------CLASS METHODS-----------------
	-----------------------------------------------*/

	/** Calculate the X position of the bomberman */
	public int calcX(int a, int size) { 
		return Math.min(((a + 4) / 10), size);
	}

	/** Calculate the Y position of the bomberman */
	public int calcY(int a, int size) {
		return Math.min(((a + 8) / 10), size);
	}

	/** This method kills the Bomberman if it has been hit by a bomb. It is executed in each iteration of our main While. */
	public void healthCheck(Bomb [] Bomb, Board Board, int size, GameBoardGUI gui) { 
		for (int i = 0; i < Bomb.length; i++) { // Iterates over the bombs
			if (Bomb[i] != null && 
			  (System.currentTimeMillis() - Bomb[i].getStartTick()) > 3000 && 
			  (System.currentTimeMillis() - Bomb[i].getStartTick()) < 6000) { // For every position other than Null and that is in the time interval in which it explodes, we check the following.

				// These Booleans allow us to know if we can continue with the execution of the explosion in certain directions.
				boolean destroyUp = true;
				boolean destroyDown = true;
				boolean destroyRight = true;
				boolean destroyLeft = true;

				for (int j = 1; j <= range; j++) { // For que "lleva" la explosi�n de la bomba hasta range. 
					// Right.
					if( Bomb[i].getX() + j < Board.getBoard()[Bomb[i].getX()].length) { // Condiciones de avance de la explosi�n, se explican en la clase Game, en el m�todo que immprime las explosiones.
						if (Board.getBoard()[ Bomb[i].getX() + j][Bomb[i].getY()].getSquareType() == 2) {
							destroyRight = false;
						}
						if (destroyRight && (Bomb[i].getX() + j)== calcX(x, size) && Bomb[i].getY() == calcY(y, size)) { // Si la posici�n del Bomberman mientras explota la bomba es la misma que la explosi�n de la bomba, el Bomberman perder� vida. Se repite para todas las directiones. 
							currentLife = currentLife - 10; // Bomberman pierde vida. 
							gui.gb_animateDamage(); // Animaci�n del da�o
						}
					}
					// Left
					if(Bomb[i].getX() - j > 0) {
						if (Board.getBoard()[Bomb[i].getX() - j][Bomb[i].getY()].getSquareType() == 2) {
							destroyLeft = false;
						}
						if (destroyLeft && (Bomb[i].getX() - j)== calcX(x, size) && Bomb[i].getY() == calcY(y, size)) {
							currentLife = currentLife -10;
							gui.gb_animateDamage();
						}
					}
					// Down
					if( Bomb[i].getY() + j < (Board.getBoard()[Bomb[i].getY()].length - 1)) {
						if (Board.getBoard()[Bomb[i].getX()][Bomb[i].getY() + 1].getSquareType() == 2) {
							destroyDown = false;
						}
						if (destroyDown && Bomb[i].getX()== calcX(x, size) && (Bomb[i].getY() + j) == calcY(y, size)) {
							currentLife = currentLife - 10;
							gui.gb_animateDamage();

						}

					}  
					// Up
					if(Bomb[i].getY()-j > 0) {
						if (Board.getBoard()[Bomb[i].getX()][Bomb[i].getY() - 1].getSquareType() == 2) {
							destroyUp = false;
						}
						if (destroyUp && Bomb[i].getX()== calcX(x, size) && (Bomb[i].getY() - j) == calcY(y, size)) {
							currentLife = currentLife - 10;
							gui.gb_animateDamage();
						}
					}
				}
			}
		}
	}

	/** Method that will subtract life from the Bomberman in the event that he is in a square where an enemy is found. */
	public void enemyDamage (Enemy globos [], int size, GameBoardGUI gui) {

		for (int i = 0; i < globos.length; i++) {
			if (calcX(x,size) == calcX(globos[i].getX(), size) && calcY(y, size) == calcY(globos[i].getY(), size) && globos[i].isAlive()) { 
				currentLife = currentLife - 5;
				gui.gb_animateDamage();
			}
		}
	}

	/** We introduce all the items and if the item is exposed and its position is the same as that of the Bomberman, the bomberman benefits from the effect of the bonus and it has already been applied so as not to be applied again. */
	public void bonusCheck(Board Board, GameBoardGUI gui, Item bonus_bomba [], Item bonus_fuego, Item bonus_fuego_completo, Item remoteControl, Item patines, Item geta, int size) { 

		/* Bonus bomba Tipo 1 */
		for(int i = 0; i < bonus_bomba.length; i++) { //Recorremos el array de bonus bomba.
			if(bonus_bomba[i]!= null && bonus_bomba[i].getX() == calcX(x, size) && bonus_bomba[i].getY() == calcY(y, size) && !bonus_bomba[i].isAplicated()) { // Para cada bonus bomba generado que tenga la misma posici�n del bomberman y que no haya sido aplicated lo aplicamos.
				if(bombs >= 1 && bombs < 5) { // El bomberman solo puede llegar a tener 5 Bombs.
					++bombs;
					gui.gb_println("Has logrado tener una bomba m�s en tu bolsa.");
				}
				bonus_bomba[i].setAplicated(true);
				gui.gb_setSpriteVisible(bonus_bomba[i].getId(), false); // El bonus ha sido aplicated y ya no se puede ver. 
			}

			/* Bonus fuego Tipo 2 */ // An�logo al anterior pero sin la necesidad del recorrer el array pues solo hay 1 en cada nivel como m�ximo.
			if(bonus_fuego.getX() == calcX(x, size) && bonus_fuego.getY() == calcY(y, size) && !bonus_fuego.isAplicated()) {
				if (range >= 1 && range <5) { // El range se encuentra dentro de unos l�mites.
					++range;
					gui.gb_println("El range se ha incrementado en una unidad.");
				}
				bonus_fuego.setAplicated(true);
				gui.gb_setSpriteVisible(bonus_fuego.getId(), false);
			}

			/* Bonus fuego completo Tipo 3 */
			if(bonus_fuego_completo.getX() == calcX(x, size) && bonus_fuego_completo.getY() == calcY(y, size) && !bonus_fuego_completo.isAplicated()) {
				if (range >= 1 && range <5) {
					range = 5;
					gui.gb_println("El range es m�ximo.");
				}
				bonus_fuego_completo.setAplicated(true);
				gui.gb_setSpriteVisible(bonus_fuego_completo.getId(), false);
			}

			/* Bonus control remoto Tipo 4 */
			if(remoteControl.getX() == calcX(x, size) && remoteControl.getY() == calcY(y, size) && !remoteControl.isAplicated()) {
				gui.gb_println("Has encontrado el Bonus de control remoto de bombs, prueba a pulsar la tecla 'tab' y te encontrar�s con una sorpresa.");
				remoteControl.setAplicated(true);
				gui.gb_setSpriteVisible(remoteControl.getId(), false);
			}

			/* Bonus patines Tipo 5 */
			if(patines.getX() == calcX(x, size) && patines.getY() == calcY(y, size) && !patines.isAplicated()) {
				if (speed >= 1 && speed < 10) {
					++speed;
					gui.gb_println("La speed se ha incrementado una unidad.");					
				}
				patines.setAplicated(true);
				gui.gb_setSpriteVisible(patines.getId(), false);
			}

			/* Bonus geta Tipo 6 */
			if(geta.getX() == calcX(x, size) && geta.getY() == calcY(y, size) && !geta.isAplicated()) {
				if (speed >= 1 && speed <= 10) {
					speed = 1;
					gui.gb_println("La speed se ha establecido en 1.");
				}
				geta.setAplicated(true);
				gui.gb_setSpriteVisible(geta.getId(), false);
			}
		}	
	}

	/** To exit the main While in case the condition of being located at the door is met. Also checks that there are no enemies left and the Bomberman has positive health. */
	public boolean checkLevel (Board Board, int size) {

		boolean checkLevel = true;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) { // Recorremos el tablero
				if(Board.getBoard()[i][j].isDoor() && calcX(x, size) == i && calcY(y, size) == j && currentLife > 0){
					checkLevel = false;
				}
			}
		}
		return checkLevel;
	}

	/** Fires the animation if the bomberman dies */
	public void checkAlive (GameBoardGUI gui) throws InterruptedException{

		// If the health is negative or 0 and this if has not been previously entered to perform the animation, we perform.
		if(currentLife <= 0 && canContinue) { 
			gui.gb_setSpriteImage(1, "bomberman141.png" );
			Thread.sleep(100L);
			gui.gb_setSpriteImage(1, "bomberman142.png" );
			Thread.sleep(100L);
			gui.gb_setSpriteImage(1, "bomberman143.png" );
			Thread.sleep(100L);
			gui.gb_setSpriteImage(1, "bomberman144.png" );
			Thread.sleep(100L);
			gui.gb_setSpriteImage(1, "bomberman145.png" );
			gui.gb_setSpriteVisible(1, false); // Bomberman invisible
			gui.gb_showMessageDialog("Has muerto");
			canContinue = false; // Animation doesn't trigger any more
		}
	}

	/** Depending on the time it takes to complete the level, corresponding points will be assigned. */
	public void timePoints (GameBoardGUI gui) {
		if(System.currentTimeMillis() - time < 300000) { // Only assign points if we finish in <5mins
			if(System.currentTimeMillis() - time < 180000) { // 3 Mins
				if(System.currentTimeMillis() - time < 120000) { // 2 Mins
					points = points + 500;
					gui.gb_showMessageDialog("You have received 500 points for finishing the game in less than 2 minutes. Congratulations");
				}
				else { // >2mins && <3mins
					points = points + 400;
					gui.gb_showMessageDialog("You have received 400 points for finishing the game in less than 2 minutes. Congratulations");
				}
			}
			else { // >3mins && <5mins
				points = points + 100;
				gui.gb_showMessageDialog("You have received 100 points for finishing the game in less than 2 minutes. Congratulations");
			}

		}
		else {
			gui.gb_showMessageDialog("Has tardado: " + (System.currentTimeMillis() - time)/1000 + " segundos en acabar el nivel, intenta mejorar y gracias por seguir jugando"); 
		}
	}

	/*---------------------------------------------
	---------------GETTERS AND SETTERS-------------
	-----------------------------------------------*/
	
	public int getBombs() {
		return bombs;
	}
	public void setBombs(int bombs) {
		if(bombs >= 1 && bombs <=5)
			this.bombs = bombs;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		if(range >= 2 && range <=5) {
			this.range = range;
		}
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean getCanContinue() {
		return canContinue;
	}
	public void setCanContinue(boolean canContinue) {
		this.canContinue = canContinue;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}

}
