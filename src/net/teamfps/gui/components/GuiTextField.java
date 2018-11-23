/**
 * 
 */
package net.teamfps.gui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import net.teamfps.gui.Input;
import net.teamfps.gui.math.Vec2i;

/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public class GuiTextField extends GuiComponent {
	protected int tx, ty, nx, ny;
	protected int fsize;
	protected int length;
	protected StringBuilder text;
	protected String name;
	protected Color tcolor;

	protected boolean focus = false;
	private boolean cursor = false;
	private ActionListener listener;
	private ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Entered");

	private int cursorPos = 0;
	private int timer;

	private final String[] chars = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "ä", "ö", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "+", "-", " ", "," };
	private final int[] keys = { KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_Q, KeyEvent.VK_R, KeyEvent.VK_S, KeyEvent.VK_T, KeyEvent.VK_U, KeyEvent.VK_V, KeyEvent.VK_W, KeyEvent.VK_X, KeyEvent.VK_Y, KeyEvent.VK_Z, Input.KEY_Ä, Input.KEY_Ö, KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_PERIOD, KeyEvent.VK_PLUS, KeyEvent.VK_MINUS, KeyEvent.VK_SPACE, KeyEvent.VK_COMMA };

	public GuiTextField(String text, int fsize, Color tcolor, int x, int y, int w, int h) {
		this.name = text;
		this.text = new StringBuilder();
		this.fsize = fsize;
		this.tcolor = tcolor;
		this.sx = this.x = x;
		this.sy = this.y = y;
		this.w = w;
		this.h = h;
		this.length = (int) (w / fsize);
	}

	public GuiTextField(String text, int fsize, int length, Color tcolor, int x, int y, int w, int h) {
		this.name = text;
		this.text = new StringBuilder();
		this.fsize = fsize;
		this.tcolor = tcolor;
		this.sx = this.x = x;
		this.sy = this.y = y;
		this.w = w;
		this.h = h;
		// this.length = (int) (w / fsize);
		this.length = length;
	}

	public GuiTextField(String name, String text, int fsize, Color tcolor, int x, int y, int w, int h) {
		this.name = name;
		this.text = new StringBuilder(text);
		this.fsize = fsize;
		this.tcolor = tcolor;
		this.sx = this.x = x;
		this.sy = this.y = y;
		this.w = w;
		this.h = h;
		this.length = (int) (w / fsize);
	}

	public GuiTextField(String name, String text, int fsize, int length, Color tcolor, int x, int y, int w, int h) {
		this.name = name;
		this.text = new StringBuilder(text);
		this.fsize = fsize;
		this.tcolor = tcolor;
		this.sx = this.x = x;
		this.sy = this.y = y;
		this.w = w;
		this.h = h;
		this.length = length;
	}

	public GuiTextField init(GuiScreen screen) {
		Vec2i tsize = screen.getSize(text.toString(), "Tahoma", fsize);
		this.ty = (h - tsize.getY()) / 2;
		this.tx = (w - tsize.getX()) / 2;
		Vec2i nsize = screen.getSize(name, "Tahoma", fsize);
		this.ny = (h - nsize.getY()) / 2;
		this.nx = (w - nsize.getX()) / 2;
		return this;
	}

	public void update(GuiScreen screen) {
		if (!visible) return;
		if (isPressed(1)) {
			focus = true;
			System.out.println("Focus!");
		}
		if (!isMouseOver()) {
			if (Input.isButtonClicked(1)) {
				focus = false;
			}
		}
		if (focus) {
			keyboard(screen);
			timer++;
			if (timer >= 32) {
				cursor = true;
				if (timer >= 64) {
					cursor = false;
					timer = 0;
				}
			}
		} else {
			cursor = false;
			timer = 0;
		}
	}

	private void keyboard(GuiScreen screen) {
		if (Input.isKeyTyped(KeyEvent.VK_BACK_SPACE) && text.length() > 0) {
			if (cursorPos > 0) {
				cursorPos--;
				text.deleteCharAt(cursorPos);
			}
		}
		if (Input.isKeyTyped(KeyEvent.VK_LEFT)) {
			if (cursorPos > 0) cursorPos--;

		}
		if (Input.isKeyTyped(KeyEvent.VK_RIGHT)) {
			if (cursorPos < text.length()) cursorPos++;
		}

		if (getListener() != null && Input.isKeyTyped(KeyEvent.VK_ENTER)) getListener().actionPerformed(event);
		if (getText().length() > length + 4) return;
		for (int i = 0; i < keys.length; i++) {
			if (Input.isKeyTyped(keys[i])) {
				append(chars[i]);
				return;
			}
		}
	}

	public void append(String str) {
		// text.insert
		if (Input.isKeyDown(KeyEvent.VK_SHIFT)) {
			if (str.equals(".")) str = ":";
			if (str.equals("-")) str = "_";
			text.insert(cursorPos, str.toUpperCase());// , text.length() + cursorPos, cursorPos + 1);
			cursorPos++;
		} else {
			if (Input.isKeyDown(KeyEvent.VK_ALT) && Input.isKeyDown(KeyEvent.VK_CONTROL)) {
				if (str.equals("+")) str = "\\";
				if (str.equals("4")) str = "$";
			}
			text.insert(cursorPos, str.toLowerCase());// , text.length() + cursorPos, cursorPos + 1);
			cursorPos++;
		}
	}

	public void render(GuiScreen screen) {
		if (!visible) return;
		screen.fillRect(x, y, w, h, color, false);
		screen.drawRect(x, y, w, h, 2, isMouseOver() ? focus ? Color.red : color : focus ? Color.green : Color.white, false);
		screen.drawString(name, fsize, x + nx, y + ny, color, false);
		String str = cursor ? text.toString().substring(0, cursorPos) + "|" + text.toString().substring(cursorPos, text.length()) : text.toString();
		screen.drawString(str, fsize, x + 4, y + 2, tcolor, false);
	}

	public String lastWord() {
		String[] s = getText().split(" ");
		if (s != null && s.length > 0) return s[s.length - 1];
		return getText();
	}

	public void clear() {
		text.setLength(0);
		cursorPos = 0;
	}

	public void cursorToEnd() {
		cursorPos = text.length();
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text.toString();
	}

	/**
	 * @return the focus
	 */
	public boolean isFocus() {
		return focus;
	}

	public ActionListener getListener() {
		return listener;
	}

	public void setListener(ActionListener listener) {
		this.listener = listener;
	}

	public void setText(String text) {
		this.text = new StringBuilder(text);
	}

	public void appendText(String str) {
		this.text.append(str);
	}

	public void insertText(String str) {
		this.text.insert(cursorPos, str);
	}

	public boolean remove(String str) {
		if (getText().contains(str)) {
			int start = this.text.indexOf(str);
			int end = this.text.lastIndexOf(str) + 1;
			if (getText().length() >= end) {
				this.text.delete(start, end);
				System.out.println("remove chars: " + start + " to " + end);
				return true;
			}
		}
		return false;
	}
}
