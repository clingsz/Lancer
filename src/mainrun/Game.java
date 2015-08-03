package mainrun;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import screen.Screen;
import screen.Viewer;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private Random random = new Random();
	public static final String NAME = "Lancer";
	public static final int HEIGHT = 480;
	public static final int WIDTH = 640;
	private static final int SCALE = 1;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int tickCount = 0;

	
	InputHandler input;
	GameState gameState = new GameState(input);
	boolean running = false;	
	
	
	public void stop(){
		running = false;
	}
	
	public void start(){
		input = new InputHandler(this);
		gameState = new GameState(input);
		gameState.init(WIDTH,HEIGHT);
		input.linkGameState(gameState);
		running = true;
		new Thread(this).start();
	}
	
	public void run()	{
		long lastTime = System.nanoTime();
		double unprocessed = 0;
//		double nsPerTick = 1000000000.0 / 60;
		double nsPerTick = 1000000000.0 / 20;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
			while (unprocessed >= 1) {
				ticks++;
//				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}
			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
//				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
	}

//	public void tick() {
//		tickCount++;
//		if (!hasFocus()) {
//			input.releaseAll();
//		} else {
//			input.tick();
//			gameState.tick();
//		}		
//	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		gameState.render();

		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());

		int ww = WIDTH * SCALE;
		int hh = HEIGHT * SCALE;
		int xo = (getWidth() - ww) / 2;
		int yo = (getHeight() - hh) / 2;
		image = gameState.getImage();
		g.drawImage(image, xo, yo, ww, hh, null);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[]args)	{
		Game game = new Game();
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(Game.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
   }

	
	
}
