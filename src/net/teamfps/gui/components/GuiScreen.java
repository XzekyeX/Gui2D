package net.teamfps.gui.components;

import java.awt.Color;

import net.teamfps.gui.Sprite;
import net.teamfps.gui.math.Vec2i;

/**
 * 
 * @author Zekye
 *
 */
public interface GuiScreen {

	Vec2i getSize(String text, String font, int fsize);

	int getWidth(String str, String font, int fsize);

	void fillRect(int x, int y, int w, int h, Color color, boolean offset);

	void drawRect(int x, int y, int w, int h, int b, Color color, boolean offset);

	void drawString(String text, String font, int fsize, int x, int y, Color color, boolean offset);

	void drawString(String text, int fsize, int x, int y, Color color, boolean offset);

	void Rect(int x, int y, int w, int h, Color in, Color out, boolean b);

	void drawSprite(Sprite sprite, int x, int y, int w, int h);

	void drawLine(int x1, int y1, int x2, int y2, Color color);

	void drawOval(int x, int y, int w, int h, Color color, boolean offset);

	void drawCenterStr(String str, String font, int fsize, Color color, int x, int y, int w, int h);

	void drawCenterStr(String str, int fsize, Color color, int x, int y, int w, int h);

	void StrRect(String str, int fsize, Color text, int x, int y, int w, int h, Color in, Color out);

	void StrRect(String str, String font, int fsize, Color text, int x, int y, int w, int h, Color in, Color out);

	int getOffsetX();

	int getOffsetY();

	int getWidth();

	int getHeight();

	boolean isInBounds(int x, int y, int w, int h);
}
