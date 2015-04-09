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
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemyFlower(){
		EnemyFlower e = new EnemyFlower((int)(Math.random()*390), 30);
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
	
	private void process(){
		if(Math.random() < difficulty){
			if(Math.random() < 0.3){
				generateEnemyBin();
				generateEnemyBomb();
			}
			else if (Math.random() > 0.6) {
				generateEnemyBowpink();
			}
			else if (Math.random() > 0.9) {
				generateEnemyBowblack();
			}  
			else	
				generateEnemyFlower();
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
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();  //ชนแล้วได้คะแนน
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				score += e.getScore();
				return;
			}
		}
		Rectangle2D.Double r = v.getRectangle();  //โดนระเบิดแล้วตาย
		Rectangle2D.Double br;
		for(EnemyBomb b : bombs){
			br = b.getRectangle();
			if(br.intersects(r)){
				die();
				return;
			}
		}
		Rectangle2D.Double sr = v.getRectangle();  //โดนถังขยะEnemyจะถูกเคลียร์
		Rectangle2D.Double cr;
		for(EnemyBin c : bins){
			cr = c.getRectangle();
			if(cr.intersects(sr)){
				clearEnemy();
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
		}
	}


	public long getScore(){
		return score;
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
