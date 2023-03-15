package logic;

public class Board {

	public static final int DIM = 8;
	Cell[] cells;

	public Board() {
		cells = new Cell[DIM];
		for (int i = 0; i < DIM; i++) {
			cells[i] = new Space(i);
		}
		int invaderPosition = (int) (Math.random() * DIM);
		int meteorPosition;
		int shieldPosition;
		do {
			meteorPosition = (int) (Math.random() * DIM);
		} while (meteorPosition == invaderPosition);
		do {
			shieldPosition = (int) (Math.random() * DIM);
		} while (shieldPosition == invaderPosition || shieldPosition == meteorPosition);

		cells[invaderPosition] = new Invader(invaderPosition);
		cells[meteorPosition] = new Meteor(meteorPosition);
		cells[shieldPosition] = new Shield(shieldPosition);
		
	}

	public Cell[] getCells() {
		return cells;
	}

	public void setCells(Cell[] cells) {
		this.cells = cells;
	}

}
