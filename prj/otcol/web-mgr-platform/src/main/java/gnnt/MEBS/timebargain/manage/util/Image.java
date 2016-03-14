package gnnt.MEBS.timebargain.manage.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Image {
	public String sRand = "";

	public Color getRandColor(int paramInt1, int paramInt2) {
		Random localRandom = new Random();
		if (paramInt1 > 255) {
			paramInt1 = 255;
		}
		if (paramInt2 > 255) {
			paramInt2 = 255;
		}
		int i = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
		int j = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
		int k = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
		return new Color(i, j, k);
	}

	public BufferedImage creatImage() {
		int i = 60;
		int j = 20;
		BufferedImage localBufferedImage = new BufferedImage(i, j, 1);
		Graphics localGraphics = localBufferedImage.getGraphics();
		Random localRandom = new Random();
		localGraphics.setColor(getRandColor(200, 250));
		localGraphics.fillRect(0, 0, i, j);
		localGraphics.setFont(new Font("Times New Roman", 0, 18));
		localGraphics.setColor(getRandColor(160, 200));
		for (int k = 0; k < 155; k++) {
			int m = localRandom.nextInt(i);
			int n = localRandom.nextInt(j);
			int i1 = localRandom.nextInt(12);
			int i2 = localRandom.nextInt(12);
			localGraphics.drawLine(m, n, m + i1, n + i2);
		}
		this.sRand = "";
		for (int k = 0; k < 4; k++) {
			String str = String.valueOf(localRandom.nextInt(10));
			this.sRand += str;
			localGraphics.setColor(new Color(20 + localRandom.nextInt(110), 20 + localRandom.nextInt(110), 20 + localRandom.nextInt(110)));
			localGraphics.drawString(str, 13 * k + 6, 16);
		}
		localGraphics.dispose();
		return localBufferedImage;
	}

	public String getRand() {
		this.sRand = "";
		Random localRandom = new Random();
		for (int i = 0; i < 4; i++) {
			String str = String.valueOf(localRandom.nextInt(10));
			this.sRand += str;
		}
		return this.sRand;
	}
}
