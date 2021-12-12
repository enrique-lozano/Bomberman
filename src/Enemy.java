
import edu.uc3m.game.GameBoardGUI;

public class Enemy extends Character {

	private int tipo; // 1 = Globo y 2 = Gota, al existir la posibilidad de desarrollar m�s enemigos debemos distinguir de qu� tipo son.
	private int id;  // Dado que vamos a generar los enemigos como Sprites debemos generar ids independientes para cada uno.
	private boolean alive;
	private String imagen; // La im�gen de Sprite debe ser almacena para poder gestionar el movimineto.
	private int frame; // Frame y direcci�n son variables que gestionan la animaci�n del movimiento del enemigo. 
	private int direction;

	/** Constructor */
	public Enemy (int x, int y, int currentLife, int maxLife, int points, int speed, int tipo, int id, boolean alive, String imagen, int frame, int direction) {

		super (x, y, currentLife, maxLife, points, speed); //Herencia
		this.tipo = tipo;
		this.id = id;
		this.alive = alive;
		this.imagen = imagen;
		this.direction = direction;
		this.frame = frame;

	}

	/*---------------------------------------------
	-----------------CLASS METHODS-----------------
	-----------------------------------------------*/

	/** Calculate the X position of an enemy */
	public int calcX(int a, int size) {
		return Math.min(((a + 4) / 10), size);
	}

	/** Calculate the Y position of an enemy */
	public int calcY(int a, int size) { 
		return Math.min(((a + 8) / 10), size);
	}

	/** Like the Bomberman, if the enemy has been hit by a bomb blast, their health must be lowered. The basic structure of the method is analyzed in Bomberman. */
	public void healthCheck(Bomb [] Bomb, Board Board, int size, GameBoardGUI gui, Bomberman Bomberman) {

		for ( int i = 0; i < Bomb.length; i++) { // Ver estructura del funcionamiento en la clase Bomberman Health Check
			if (Bomb[i] != null && (System.currentTimeMillis() - Bomb[i].getStartTick()) > 3000 && (System.currentTimeMillis() - Bomb[i].getStartTick()) < 6000) { // Bomb active
				boolean destruir_up = true;
				boolean destruir_down = true;
				boolean destruir_right = true;
				boolean destruir_left = true;

				for (int j = 1; j <= Bomberman.getRange(); j++) {
					if(alive) { // Solo podemos quitar vida a un enemigo si est� alive.
						/* Right */
						if( Bomb[i].getX()+j < Board.getBoard()[Bomb[i].getX()].length) {
							if (Board.getBoard()[ Bomb[i].getX() + j][Bomb[i].getY()].getSquareType() == 2) {
								destruir_right = false;
							}
							if (destruir_right && (Bomb[i].getX() + j)== calcX(x, size) && Bomb[i].getY() == calcY(y, size)) {
								currentLife = currentLife - 20;
								alive = false;
								Bomberman.setPuntos(Bomberman.getPuntos() + 100); // Por cada enemigo muerto el Bomberman recibe points.
								gui.gb_println("Has ganado 100 points");
							}
						}
						/* Left */
						if(Bomb[i].getX() - j > 0) {
							if (Board.getBoard()[Bomb[i].getX() - j][Bomb[i].getY()].getSquareType() == 2) {
								destruir_left = false;
							}
							if (destruir_left && (Bomb[i].getX() - j)== calcX(x, size) && Bomb[i].getY() == calcY(y, size)) {
								currentLife = currentLife -20;
								alive = false;
								Bomberman.setPuntos(Bomberman.getPuntos() + 100);
								gui.gb_println("Has ganado 100 points");
							}
						}
						/* Down */
						if( Bomb[i].getY() + j < Board.getBoard()[Bomb[i].getY()].length) {
							if (Board.getBoard()[Bomb[i].getX()][Bomb[i].getY() + 1].getSquareType() == 2) {
								destruir_down = false;
							}
							if (destruir_down && Bomb[i].getX()== calcX(x, size) && (Bomb[i].getY() + j) == calcY(y, size)) {
								currentLife = currentLife - 20;
								alive = false;
								Bomberman.setPuntos(Bomberman.getPuntos() + 100);
								gui.gb_println("Has ganado 100 points");
							}
						}   
						/* Up */
						if(Bomb[i].getY()-j > 0) {
							if (Board.getBoard()[Bomb[i].getX()][Bomb[i].getY() - 1].getSquareType() == 2) {
								destruir_up = false;
							}
							if (destruir_up && Bomb[i].getX()== calcX(x, size) && (Bomb[i].getY() - j) == calcY(y, size)) {
								currentLife = currentLife - 20;
								alive = false;
								Bomberman.setPuntos(Bomberman.getPuntos() + 100);
								gui.gb_println("Has ganado 100 points");
							}
						}
					}
				}
			}
		}
	}

	/*---------------------------------------------
	---------------GETTERS AND SETTERS-------------
	-----------------------------------------------*/
	
	public int getFrame() {
		return frame;
	}
	public void setFrame(int frame) {
		this.frame = frame;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return tipo;
	}
	public void setType(int tipo) {
		this.tipo = tipo;
	}

}
