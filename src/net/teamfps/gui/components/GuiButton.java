/**
 * 
 */
package net.teamfps.gui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.teamfps.gui.Input;
import net.teamfps.gui.math.Vec2i;

/**
 * @author Zekye
 *
 */
public class GuiButton extends GuiComponent {
	protected int tx, ty;
	protected int fsize;
	private int key = 1;
	protected String text;
	protected Color tcolor;
	protected boolean locked = false;
	protected ActionListener listener;
	private ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Clicked");

	public GuiButton(String text, int fsize, Color tcolor, int x, int y, int w, int h) {
		this.text = text;
		this.fsize = fsize;
		this.tcolor = tcolor;
		this.sx = this.x = x;
		this.sy = this.y = y;
		this.w = w;
		this.h = h;
		this.tx = 4;
		this.ty = 2;
	}

	public GuiButton(String text, int x, int y, int w, int h) {
		this(text, 12, Color.white, x, y, w, h);
	}

	public GuiButton init(GuiScreen screen) {
		Vec2i size = screen.getSize(text,"Tahoma", fsize);
		this.ty = (h - size.getY()) / 2;
		this.tx = (w - size.getX()) / 2;
		return this;
	}

	public GuiButton setListener(ActionListener listener) {
		this.listener = listener;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.teamfps.ws.gfx.Border#update(net.teamfps.ws.gfx.Screen)
	 */
	@Override
	public void update(GuiScreen screen) {
		if (!visible || locked) return;
		if (isPressed(1)) action();
	}

	public void render(GuiScreen screen) {
		if (!visible) return;
		screen.fillRect(x, y, w, h, color, false);
		screen.drawRect(x, y, w, h, 2, locked ? Color.red : (isMouseOver() && Input.isButtonDown(key)) ? Color.green : isMouseOver() ? color : Color.white, false);
		// screen.drawString(text, fsize, (x + tx), (y + ty), tcolor, false);
		screen.drawCenterStr(text, fsize, tcolor, x, y, w, h);
	}

	public void action() {
		if (listener != null) listener.actionPerformed(event);
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLocked() {
		return locked;
	}

	/**
	 * @param object
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return
	 */
	public String getText() {
		return text;
	}
}
