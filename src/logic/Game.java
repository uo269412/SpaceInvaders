package logic;

public class Game {

	public static int maxShots = 4;
	public static final int BOARD_SIZE = 7;
	int score;
	int shots;
	private Board board;
	private boolean invaderFound;
	private boolean meteorShot;
	private boolean shipShield;

	public Board getBoard() {
		return board;
	}

	public Game() {
		initialize();
	}

	public void changeShots(int number) {
		maxShots = number;
	}
	
	public int getNumberOfShots() {
		return maxShots;
	}
	public void initialize() {
		invaderFound = false;
		meteorShot = false;
		shipShield = false;
		board = new Board();
		score = 800;
		shots = 0;
	}

	public void shoot(int i) {
		shots--;
		if (board.getCells()[i] instanceof Invader) {
			((Invader) board.getCells()[i]).setErased(true);
			invaderFound = true;
		}
		score = score + board.getCells()[i].getScore();
		if (board.getCells()[i] instanceof Meteor) {
			if (!shipShield) {
				meteorShot = true;
				score = 0;
				System.out.println("Hola");
			} else {
				shipShield = false;
			}
		}
		if (board.getCells()[i] instanceof Shield) {
			shipShield = true;
		}
	}

	public boolean isShipProtected() {
		return shipShield;
	}
	public boolean isGameOver() {
		if (invaderFound || shots == 0 || meteorShot) {
			shots = 0;
			return true;
		}
		return false;
	}

	public boolean isGameOverWin() {
		return (invaderFound);
	}

	public int getScore() {
		return score;
	}

	public void launch() {
		setShots(Dice.launch());
	}

	public int getShots() {
		return shots;
	}

	private void setShots(int shots) {
		this.shots = shots;
	}
}
