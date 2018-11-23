package net.teamfps.gui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public enum Location {
	TOP_LEFT, TOP_CENTER, TOP_RIGHT, CENTER_LEFT, CENTER, CENTER_RIGHT, BOT_LEFT, BOT_CENTER, BOT_RIGHT, BOT_CENTER_TB, BOT_RIGHT_TB;

	public static Point getPoint(Location location, int width, int height) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle win = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int tbh = (int) (screen.getHeight() - win.getHeight());
		switch (location) {
			case TOP_LEFT:
				return new Point(0, 0);
			case TOP_CENTER:
				return new Point((int) ((screen.getWidth() - width) / 2), 0);
			case TOP_RIGHT:
				return new Point((int) ((screen.getWidth() - width)), 0);
			case CENTER_LEFT:
				return new Point(0, (int) ((screen.getHeight() - height) / 2));
			case CENTER:
				return new Point((int) ((screen.getWidth() - width) / 2), (int) ((screen.getHeight() - height) / 2));
			case CENTER_RIGHT:
				return new Point((int) ((screen.getWidth() - width)), (int) ((screen.getHeight() - height) / 2));
			case BOT_LEFT:
				return new Point(0, (int) ((screen.getHeight() - height)));
			case BOT_CENTER:
				return new Point((int) ((screen.getWidth() - width) / 2), (int) ((screen.getHeight() - height)));
			case BOT_RIGHT:
				return new Point((int) ((screen.getWidth() - width)), (int) ((screen.getHeight() - height)));
			case BOT_CENTER_TB:
				return new Point((int) ((screen.getWidth() - width) / 2), (int) ((screen.getHeight() - height) - tbh));
			case BOT_RIGHT_TB:
				return new Point((int) ((screen.getWidth() - width)), (int) ((screen.getHeight() - height) - tbh));
			default:
				break;
		}
		return new Point(0, 0);
	}

}
