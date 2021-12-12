

public class Item { // La clase item nos permite trabajar con loss dos types b�sicos de items que hemos considerado, los bonus y las bombs.

	/** All items will be treated as Sprites, so we must store their ids. */
	protected int id;
	protected int x;
	protected int y;
	/** Indicates if the item has been aplicated */
	protected boolean aplicated;
	protected int type; // Hemos organizado los distintos types de items como sigue: 1 = Bonus bomba, 2 = Bonus fuego, 3 = Bonus fuego completo, 4 = Control remoto, 5 = Patines, 6 = Geta, 7 = Bomb cl�sica.

	/* Constructors */
	
	public Item () {} // Empty constructor

	public Item (int id, int x, int y, int type) { // For the bombs.
		this.id = id;
		this.x = x;
		this.y = y;
		this.type = type;

	}

	public Item (int id, int x, int y, int type, boolean aplicated) { // For the bonus.
		this.id = id;
		this.x = x;
		this.y = y;
		this.type = type;
		this.aplicated = aplicated;
	}

	/*---------------------------------------------
	---------------GETTERS AND SETTERS-------------
	-----------------------------------------------*/
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isAplicated() {
		return aplicated;
	}
	public void setAplicated(boolean aplicated) {
		this.aplicated = aplicated;
	}

}
