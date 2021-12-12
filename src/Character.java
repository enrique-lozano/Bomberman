/** Class that contains the common attributes for all the characters (bomberman and enemies) */
public class Character  {

	protected int x;
	protected int y;
	protected int currentLife;
	protected int maxLife;
	protected int points;
	protected int speed;

	/** Constructor */
	public Character (int x, int y, int currentLife, int maxLife, int points, int speed) {
		this.x = x;
		this.y = y;
		this.currentLife = currentLife;
		this.maxLife = currentLife;
		this.points = points;
		this.speed = speed;
	}

	/*---------------------------------------------
	---------------GETTERS AND SETTERS-------------
	-----------------------------------------------*/

	public int getVida_actual() {
		return currentLife;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setVida_actual(int currentLife) {
		this.currentLife = currentLife;
	}
	public int getVida_maxima() {
		return maxLife;
	}
	public void setVida_maxima(int maxLife) {
		this.maxLife = maxLife;
	}
	public int getPuntos() {
		return points;
	}
	public void setPuntos(int points) {
		this.points = points;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
