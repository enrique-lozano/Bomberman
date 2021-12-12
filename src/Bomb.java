public class Bomb extends Item{  // Consideramos la bomba como un Item y establecemos una herencia desde la clase.

	protected long startTick; // Almacena el momento exacto en el que se pone la bomba para ser capaces de controlar sus tiempos.
	protected boolean active; // Nos indica si la bomba est� active.

	/** Constructor */
	public Bomb(int id, int x, int y, int tipo, long startTick, boolean active) {
		super (id, x, y, tipo);
		super.type = 7; // El tipo de la bomba sabemos que es 7.
		this.startTick = startTick;
		this.active = active;
	}

	/*---------------------------------------------
	---------------GETTERS AND SETTERS-------------
	-----------------------------------------------*/
	
	public long getStartTick() {
		return startTick;
	}
	public void setStartTick(long startTick) {
		this.startTick = startTick;
	}
}
