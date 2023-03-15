package gui;

import javax.swing.ImageIcon;

import logic.Cell;
import logic.Space;
import logic.Invader;
import logic.Meteor;
import logic.Shield;

public class ImageFactory {

	private static final String IMAGEN_INVADER = "/img/invader.jpg";
	private static final String IMAGEN_SPACE = "/img/space.jpg";
	private static final String IMAGEN_SHOOT = "/img/shoot.png";
	private static final String IMAGEN_METEOR = "/img/meteor.png";
	private static final String IMAGEN_SHIELD = "/img/shield.png";

	public static ImageIcon getImagen(Cell cell) {
		if (cell instanceof Invader)
			return loadImage(IMAGEN_INVADER);
		else if (cell instanceof Space)
			return loadImage(IMAGEN_SPACE);
		else if (cell instanceof Meteor)
			return loadImage(IMAGEN_METEOR);
		else if (cell instanceof Shield)
			return loadImage(IMAGEN_SHIELD);
		return null;
	}

	public static ImageIcon getImage()
	{
		return loadImage(IMAGEN_SHOOT);
	}
	
	private static ImageIcon loadImage(String fqImageName) {
		return new ImageIcon(ImageFactory.class.getResource(fqImageName));
	}
}
