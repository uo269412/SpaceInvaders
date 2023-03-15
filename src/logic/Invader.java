package logic;

public class Invader extends Cell{
	boolean erased;
	
	public boolean isErased() {
		return erased;
	}
	public void setErased(boolean erased) {
		this.erased = erased;
	}
	public Invader (int position) {
	 setPosition(position);
	 System.out.println("INVADER: " + getPosition());
	 setScore(3000);
	 setErased (false);
	}
}
