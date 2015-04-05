package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpaceShip extends Sprite{

	int step = 8;
	Image spaceshipPic;

	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		try {
			spaceshipPic = ImageIO.read(new File("./f2/spw/image/hellokitty.png"));  //hellokitty
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(spaceshipPic,x,y,width,height,null);
		//g.setColor(Color.PINK); //Color modify
		//g.fillRect(x, y, width, height);
		
	}

	public void move(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}
	public void moveHeight(int direction){
		y += (step * direction);
		if(y < 0)
			y = 0;
		if(y > 650 - height)
			y = 650 - height;
	}
}
