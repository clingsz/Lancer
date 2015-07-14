package element;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class TerrianGen {
	private static final Random random = new Random();
	public double[] values;
	private int w, h;

	public TerrianGen(int w, int h, int featureSize) {
		this.w = w;
		this.h = h;

		values = new double[w * h];

		for (int y = 0; y < w; y += featureSize) {
			for (int x = 0; x < w; x += featureSize) {
				setSample(x, y, random.nextFloat() * 2 - 1);
			}
		}

		int stepSize = featureSize;
		double scale = 1.0 / w;
		double scaleMod = 1;
		do {
			int halfStep = stepSize / 2;
			for (int y = 0; y < w; y += stepSize) {
				for (int x = 0; x < w; x += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + stepSize, y + stepSize);

					double e = (a + b + c + d) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale;
					setSample(x + halfStep, y + halfStep, e);
				}
			}
			for (int y = 0; y < w; y += stepSize) {
				for (int x = 0; x < w; x += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + halfStep, y + halfStep);
					double e = sample(x + halfStep, y - halfStep);
					double f = sample(x - halfStep, y + halfStep);

					double H = (a + b + d + e) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
					double g = (a + c + d + f) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
					setSample(x + halfStep, y, H);
					setSample(x, y + halfStep, g);
				}
			}
			stepSize /= 2;
			scale *= (scaleMod + 0.8);
			scaleMod *= 0.3;
		} while (stepSize > 1);
	}

	private double sample(int x, int y) {
		return values[(x & (w - 1)) + (y & (h - 1)) * w];
	}

	private void setSample(int x, int y, double value) {
		values[(x & (w - 1)) + (y & (h - 1)) * w] = value;
	}

	public static byte[][] createAndValidateTopMap(int w, int h) {
		int attempt = 0;
		do {
			byte[][] result = createTopMap(w, h);

			int[] count = new int[256];

			for (int i = 0; i < w * h; i++) {
				count[result[0][i] & 0xff]++;
			}
			if (count[Terrian.hill.id] < 50) continue;
			if (count[Terrian.sand.id] < 50) continue;
			if (count[Terrian.grass.id] < 100) continue;
			if (count[Terrian.forest.id] < 50) continue;
			
			return result;

		} while (true);
	}
	
	private static byte[][] createTopMap(int w, int h) {
		TerrianGen mnoise1 = new TerrianGen(w, h, 16);
		TerrianGen mnoise2 = new TerrianGen(w, h, 16);
		TerrianGen mnoise3 = new TerrianGen(w, h, 16);

		TerrianGen noise1 = new TerrianGen(w, h, 32);
		TerrianGen noise2 = new TerrianGen(w, h, 32);

		byte[] map = new byte[w * h];
		byte[] data = new byte[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int i = x + y * w;

				double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;
				double mval = Math.abs(mnoise1.values[i] - mnoise2.values[i]);
				mval = Math.abs(mval - mnoise3.values[i]) * 3 - 2;

				double xd = x / (w - 1.0) * 2 - 1;
				double yd = y / (h - 1.0) * 2 - 1;
				if (xd < 0) xd = -xd;
				if (yd < 0) yd = -yd;
				double dist = xd >= yd ? xd : yd;
				dist = dist * dist * dist * dist;
				dist = dist * dist * dist * dist;
				val = val + 1 - dist * 20;

				if (val < -0.5) {
					map[i] = (byte) Terrian.water.id;
				} else if (val > 0.5 && mval < -1.5) {
					map[i] = (byte) Terrian.hill.id;
				} else {
					map[i] = (byte) Terrian.grass.id;
				}
			}
		}

		for (int i = 0; i < w * h / 2800; i++) {
			int xs = random.nextInt(w);
			int ys = random.nextInt(h);
			for (int k = 0; k < 10; k++) {
				int x = xs + random.nextInt(21) - 10;
				int y = ys + random.nextInt(21) - 10;
				for (int j = 0; j < 100; j++) {
					int xo = x + random.nextInt(5) - random.nextInt(5);
					int yo = y + random.nextInt(5) - random.nextInt(5);
					for (int yy = yo - 1; yy <= yo + 1; yy++)
						for (int xx = xo - 1; xx <= xo + 1; xx++)
							if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
								if (map[xx + yy * w] == (byte) Terrian.grass.id) {
									map[xx + yy * w] = (byte) Terrian.sand.id;
								}
							}
				}
			}
		}

		for (int i = 0; i < w * h / 400; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			for (int j = 0; j < 200; j++) {
				int xx = x + random.nextInt(15) - random.nextInt(15);
				int yy = y + random.nextInt(15) - random.nextInt(15);
				if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
					if (map[xx + yy * w] ==  (byte) Terrian.grass.id) {
						map[xx + yy * w] = (byte) Terrian.forest.id;
					}
				}
			}
		}
		
		return new byte[][] { map, data };
	}

	
	public static void main(String[] args) {
		int d = 0;
		while (true) {
			int w = 64;
			int h = 64;

			byte[] map = TerrianGen.createAndValidateTopMap(w, h)[0];
			// byte[] map = LevelGen.createAndValidateUndergroundMap(w, h, (d++ % 3) + 1)[0];
			// byte[] map = LevelGen.createAndValidateSkyMap(w, h)[0];

			BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			int[] pixels = new int[w * h];
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int i = x + y * w;
					if (map[i] == (byte) Terrian.water.id) pixels[i] = 0x000080;
					if (map[i] == (byte) Terrian.grass.id) pixels[i] = 0x208020;
					if (map[i] == (byte) Terrian.hill.id) pixels[i] = 0xa0a0a0;
					if (map[i] == (byte) Terrian.sand.id) pixels[i] = 0xa0a040;
					if (map[i] == (byte) Terrian.forest.id) pixels[i] = 0x003000;
				}
			}
			img.setRGB(0, 0, w, h, pixels, 0, w);
			JOptionPane.showMessageDialog(null, null, "Another", JOptionPane.YES_NO_OPTION, new ImageIcon(img.getScaledInstance(w * 4, h * 4, Image.SCALE_AREA_AVERAGING)));
		}
	}
}