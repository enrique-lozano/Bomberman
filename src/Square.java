

public class Square { // El tablero se basa en objetos casillas con unas caracter�sticas determinadas.

	private boolean destructible;
	private boolean passable;

	private boolean bonus; // Indicates if there is a bonus below or not.
	private boolean door; // Indicates if there is a door below or not.
	
	/** There are several types of boxes according to their characteristics, to facilitate the handling of the different types they will be identified by: 1 = Empty Square, 2 = Wall and 3 = Brick. */
	private int type;
	/** Indicates if we have detonated the box previously, it is useful for exploding the bricks. */
	private boolean detonated; 

	/** Constructor */
	public Square () {} // Empty constructor
	public Square (boolean destructible, boolean bonus, boolean door, boolean passable, boolean detonated) {

		this.passable = passable;
		this.bonus = bonus;
		this.door = door;
		this.passable = passable;
		this.detonated = detonated;
	}

	/*---------------------------------------------
	---------------GETTERS AND SETTERS-------------
	-----------------------------------------------*/

	public boolean isDetonated() {
		return detonated;
	}
	public void setDetonated(boolean detonated) {
		this.detonated = detonated;
	}
	public boolean isDestructible() {
		return destructible;
	}
	public void setDestructible(boolean destructible) {
		this.destructible = destructible;
	}
	public boolean isBonus() {
		return bonus;
	}
	public void setBonus(boolean bonus) {
		this.bonus = bonus;
	}
	public boolean isDoor() {
		return door;
	}
	public void setDoor(boolean door) {
		this.door = door;
	}
	public boolean isPassable() {
		return passable;
	}
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
	public int getSquareType() {
		return type;
	}
	public void setSquareType(int type) {
		this.type = type;
	}

}
