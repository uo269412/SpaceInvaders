package logic;

public class Dice {
	
	public static int launch()
	{ 
		return ((int) (Math.random() * Game.maxShots) + 1);
	}


}
