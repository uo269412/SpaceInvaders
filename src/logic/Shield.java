package logic;

public class Shield extends Cell{
	
	public Shield(int position) {
		 setPosition (position);
		 System.out.println("SHIELD: " + getPosition());
		 setScore(500);
		}


}
