import edu.uc3m.game.GameBoardGUI;

/**
 * @author Mario Lozano Cortés & Enrique Lozano Cebriano. 
 * Universidad Carlos III de Madrid. 
 * Programación. 
 * Created on 2017, December.
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		int size = 14; 
		Board Board [] = new Board [15]; // 15 different levels
		GameBoardGUI gui = new GameBoardGUI(size, size);
		Bomberman Bomberman = new Bomberman (10, 10, 100, 100, 0, 2, 1, 2, true, System.currentTimeMillis());

		for (int nivel = 0; nivel < Board.length; nivel++) {
			/* The structure of the game is based on the array of Boards, each generated board will correspond to a different level. Through them we go through the array of Boards generating the specific board object in each iteration. In the Game class we have developed the game engine. */
			if(Bomberman.getCanContinue()) {
				Board[nivel] = new Board(size);	
				Game Nivel = new Game();
				Nivel.createLevel(nivel, gui, size, Bomberman, Board[nivel]); 
				Bomberman.setX(10); // At the end of the level the Bomberman returns to square 1, 1.
				Bomberman.setY(10);
			}
		}
		gui.gb_showMessageDialog("The game is over, you have obtained " + Bomberman.getPuntos() + " points."); 
	}
}