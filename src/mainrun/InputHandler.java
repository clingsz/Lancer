package mainrun;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import screen.Viewer;

public class InputHandler implements KeyListener, MouseListener{
	public Viewer viewer;
	
	public class Key {
		public int presses, absorbs;
		public boolean down, clicked;

		public Key() {
			keys.add(this);
		}

//		public void toggle(boolean pressed) {
//			if (pressed != down) {
//				down = pressed;
//			}
//			if (pressed) {
//				presses++;
//			}
//		}
		public void toggle(boolean b){
			clicked = b;
		}
		public void tick() {
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}
	}
	
	public List<Key> keys = new ArrayList<Key>();

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key next = new Key();
	public Key home = new Key();
	public Key select = new Key();
	public Key enter = new Key();
	public Key cancel = new Key();
	public Key tab = new Key();
	public Key mouse = new Key();
	
	public InputHandler(Game game){
		game.addKeyListener(this);
		game.addMouseListener(this);
	}
	
	public void linkViewer(Viewer viewer){
		 this.viewer = viewer;

	}
	
	
	public boolean directionKeyClicked;
	
	public void keyPressed(KeyEvent ke) {
		toggle(ke,true);
		viewer.keyPress();
	}
	public void keyReleased(KeyEvent ke) {
		toggle(ke,false);
	}
	
	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
		if (up.clicked || down.clicked || left.clicked || right.clicked){
			directionKeyClicked = true;
		}
		else
		{
			directionKeyClicked = false;
		}
	}
	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).clicked = false;
		}
	}
	
	public void toggle(KeyEvent ke, boolean pressed){
		if (ke.getKeyCode() == KeyEvent.VK_W) up.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_S) down.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_A) left.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_D) right.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_UP) up.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_DOWN) down.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_LEFT) left.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT) right.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_N) next.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_H) home.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_J) select.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_K) cancel.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) enter.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_SPACE) select.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) cancel.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_L) {
			tab.toggle(pressed);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public int clickX = -1;
	public int clickY = -1;
	public boolean mouseClicked = false;
	
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON1){
			viewer.click(arg0.getX(),arg0.getY());
		}
	}
	

	public void releaseMouseClicked(){
		mouseClicked = false;
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (arg0.getButton() == 1){
			mouse.toggle(true);
			clickX = arg0.getX();
			clickY = arg0.getY();
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (arg0.getButton() == 1){
			mouse.toggle(false);
		}
	}
}
