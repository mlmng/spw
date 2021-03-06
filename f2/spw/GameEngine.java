package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<EnemyBomb> bombs = new ArrayList<EnemyBomb>();
	private ArrayList<EnemyBin> bins = new ArrayList<EnemyBin>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private SpaceShip v;
	ReadScore highScore = new ReadScore();

	
	private Timer timer;
	
	private long score = 0;
	private long maxScore = 0;
	private int energy = 3;
	private double difficulty = 0.1;
	private double diffItem = 0.1;
	private boolean itemRandom = true;

	
	public GameEngine(GamePanel gp, SpaceShip v ) {
		this.gp = gp;
		this.v = v;
		this.maxScore = Long.parseLong(highScore.getScore());	
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		gp.updateGameUIStart(this);
	
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemyBowPurple(){
		EnemyBowPurple e = new EnemyBowPurple((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	private void generateEnemyBowblack(){
		EnemyBowblack e = new EnemyBowblack((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	private void generateEnemyBowpink(){
		EnemyBowpink e = new EnemyBowpink((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	private void generateEnemyMinusScore(){
		EnemyMinusScore e = new EnemyMinusScore((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	private void generateEnemyBomb(){
		EnemyBomb b = new EnemyBomb((int)(Math.random()*390), 30);
		gp.sprites.add(b);
		bombs.add(b);
	}
	private void generateEnemyBin(){
		EnemyBin c = new EnemyBin((int)(Math.random()*390), 30);
		gp.sprites.add(c);
		bins.add(c);
	}
	private void generateItemRandom(){
		ItemRandom t = new ItemRandom((int)(Math.random()*390), 30);
		gp.sprites.add(t);
		items.add(t);
	}
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemyBowblack();
			if(Math.random() < 0.3){
				generateEnemyBin();
				generateEnemyBomb();
				generateEnemyMinusScore();
			}
			else if (Math.random() > 0.6) {
				generateEnemyBowpink();
			} 
			else	
				generateEnemyBowPurple();
		}
		if(Math.random() < diffItem){
			if(itemRandom){
				generateItemRandom();
				itemRandom = false;
			// generateItemRandom();
			}
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				// score += e.getScore(); //score modify
			}
		}
		Iterator<EnemyBomb> b_iter = bombs.iterator();
		while(b_iter.hasNext()){
			Enemy b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}
		Iterator<EnemyBin> c_iter = bins.iterator();
		while(c_iter.hasNext()){
			Enemy c = c_iter.next();
			c.proceed();
			
			if(!c.isAlive()){
				c_iter.remove();
				gp.sprites.remove(c);
			}
		}
		Iterator<Item> i_iter = items.iterator();
		while(i_iter.hasNext()){
			Item i = i_iter.next();
			i.proceed();
			
			if(!i.isAlive()){
				i_iter.remove();
				gp.sprites.remove(i);
				itemRandom = true;
				// score += e.getScore(); //score modify
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();  //ชนแล้วได้คะแนน
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				score += e.getScore();
				e.die();
				return;
			}
		}
		Rectangle2D.Double r = v.getRectangle();  //โดนระเบิดแล้วตาย
		Rectangle2D.Double br;
		for(EnemyBomb b : bombs){
			br = b.getRectangle();
			if(br.intersects(r)){
				if(energy == 0){
					if(score>maxScore){
						highScore.writeScore(score);
					}
					die();
					return;
				}
				else{
					energy--;
					b.die();
				}
			}
		}
		Rectangle2D.Double i = v.getRectangle();  //รับitem
		Rectangle2D.Double bt;
		for(Item t : items){
			bt = t.getRectangle();
			if(bt.intersects(i)){
				if(Math.random()*1 > 0.5){
					if(energy < 3){
						energy++;
						t.die();
					}
					else
						t.die();
				}
				else {
					score += t.getScore();
					t.die();
				}
			}
		}
		Rectangle2D.Double sr = v.getRectangle();  //โดนถังขยะEnemyจะถูกเคลียร์
		Rectangle2D.Double cr;
		for(EnemyBin c : bins){
			cr = c.getRectangle();
			if(cr.intersects(sr)){
				clearEnemy();
				c.die();
				return;
			}
		}
	}
	private void clearEnemy(){
		for(Enemy s : enemies){
			s.die();
		}
	}
	
	public void die(){
		gp.updateGameUIStop(this);
		timer.stop();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1);
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		case KeyEvent.VK_UP:
			v.moveHeight(-1);
			break;
		case KeyEvent.VK_DOWN:
			v.moveHeight(1);
			break;
		case KeyEvent.VK_SPACE :
			start();
			break;
		}

	}


	public long getScore(){
		return score;
	}

	public long getMaxScore(){
		if(score > maxScore){
			return score;

		}
		return maxScore;
	}

	public int getEnergy(){
		return energy;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
