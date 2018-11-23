package net.teamfps.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public class Input implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
	private static final int MAX_KEYS = 65536;
	private static final int MAX_BUTTONS = 10;
	private static boolean[] keys = new boolean[MAX_KEYS];
	private static boolean[] keyState = new boolean[MAX_KEYS];
	private static boolean[] keyTyped = new boolean[MAX_KEYS];
	private static boolean[] buttons = new boolean[MAX_BUTTONS];
	private static boolean[] buttonState = new boolean[MAX_BUTTONS];
	private static boolean[] buttonClicked = new boolean[MAX_BUTTONS];

	public static int MX, MY, DX, DY;
	public static int SCROLL = 0;

	public static final int KEY_SPACE = 0x20, //
			KEY_APOSTROPHE = 0x27, //
			KEY_COMMA = 0x2C, //
			KEY_MINUS = 0x2D, //
			KEY_PERIOD = 0x2E, //
			KEY_SLASH = 0x2F, //
			KEY_0 = 0x30, //
			KEY_1 = 0x31, //
			KEY_2 = 0x32, //
			KEY_3 = 0x33, //
			KEY_4 = 0x34, //
			KEY_5 = 0x35, //
			KEY_6 = 0x36, //
			KEY_7 = 0x37, //
			KEY_8 = 0x38, //
			KEY_9 = 0x39, //
			KEY_SEMICOLON = 0x3B, //
			KEY_EQUAL = 0x3D, //
			KEY_A = 0x41, //
			KEY_B = 0x42, //
			KEY_C = 0x43, //
			KEY_D = 0x44, //
			KEY_E = 0x45, //
			KEY_F = 0x46, //
			KEY_G = 0x47, //
			KEY_H = 0x48, //
			KEY_I = 0x49, //
			KEY_J = 0x4A, //
			KEY_K = 0x4B, //
			KEY_L = 0x4C, //
			KEY_M = 0x4D, //
			KEY_N = 0x4E, //
			KEY_O = 0x4F, //
			KEY_P = 0x50, //
			KEY_Q = 0x51, //
			KEY_R = 0x52, //
			KEY_S = 0x53, //
			KEY_T = 0x54, //
			KEY_U = 0x55, //
			KEY_V = 0x56, //
			KEY_W = 0x57, //
			KEY_X = 0x58, //
			KEY_Y = 0x59, //
			KEY_Z = 0x5A, //
			KEY_Ä = 0xDE, //
			KEY_Ö = 0x87, //
			KEY_LEFT_BRACKET = 0x5B, //
			KEY_BACKSLASH = 0x5C, //
			KEY_RIGHT_BRACKET = 0x5D, //
			KEY_GRAVE_ACCENT = 0x60, //
			KEY_WORLD_1 = 0xA1, //
			KEY_WORLD_2 = 0xA2;//

	/** Function keys. */
	public static final int KEY_ESCAPE = 0x100, //
			KEY_ENTER = 10, //
			KEY_TAB = 9, //
			KEY_BACKSPACE = 8, //
			KEY_INSERT = 0x104, //
			KEY_DELETE = 0x105, //
			KEY_RIGHT = 0x106, //
			KEY_LEFT = 0x107, //
			KEY_DOWN = 0x108, //
			KEY_UP = 0x109, //
			KEY_PAGE_UP = 0x10A, //
			KEY_PAGE_DOWN = 0x10B, //
			KEY_HOME = 0x10C, //
			KEY_END = 0x10D, //
			KEY_CAPS_LOCK = 0x118, //
			KEY_SCROLL_LOCK = 0x119, //
			KEY_NUM_LOCK = 0x11A, //
			KEY_PRINT_SCREEN = 0x11B, //
			KEY_PAUSE = 0x11C, //
			KEY_F1 = 0x122, //
			KEY_F2 = 0x123, //
			KEY_F3 = 0x124, //
			KEY_F4 = 0x125, //
			KEY_F5 = 0x126, //
			KEY_F6 = 0x127, //
			KEY_F7 = 0x128, //
			KEY_F8 = 0x129, //
			KEY_F9 = 0x12A, //
			KEY_F10 = 0x12B, //
			KEY_F11 = 0x12C, //
			KEY_F12 = 0x12D, //
			KEY_F13 = 0x12E, //
			KEY_F14 = 0x12F, //
			KEY_F15 = 0x130, //
			KEY_F16 = 0x131, //
			KEY_F17 = 0x132, //
			KEY_F18 = 0x133, //
			KEY_F19 = 0x134, //
			KEY_F20 = 0x135, //
			KEY_F21 = 0x136, //
			KEY_F22 = 0x137, //
			KEY_F23 = 0x138, //
			KEY_F24 = 0x139, //
			KEY_F25 = 0x13A, //
			KEY_KP_0 = 0x140, //
			KEY_KP_1 = 0x141, //
			KEY_KP_2 = 0x142, //
			KEY_KP_3 = 0x143, //
			KEY_KP_4 = 0x144, //
			KEY_KP_5 = 0x145, //
			KEY_KP_6 = 0x146, //
			KEY_KP_7 = 0x147, //
			KEY_KP_8 = 0x148, //
			KEY_KP_9 = 0x149, //
			KEY_KP_DECIMAL = 0x14A, //
			KEY_KP_DIVIDE = 0x14B, //
			KEY_KP_MULTIPLY = 0x14C, //
			KEY_KP_SUBTRACT = 0x14D, //
			KEY_KP_ADD = 0x14E, //
			KEY_KP_ENTER = 0x14F, //
			KEY_KP_EQUAL = 0x150, //
			KEY_LEFT_SHIFT = 0x154, //
			KEY_LEFT_CONTROL = 0x155, //
			KEY_LEFT_ALT = 0x156, //
			KEY_LEFT_SUPER = 0x157, //
			KEY_RIGHT_SHIFT = 0x158, //
			KEY_RIGHT_CONTROL = 0x159, //
			KEY_RIGHT_ALT = 0x15A, //
			KEY_RIGHT_SUPER = 0x15B, //
			KEY_MENU = 0x15C, //
			KEY_LAST = KEY_MENU;//

	public void update() {
		for (int i = 0; i < MAX_KEYS; i++)
			keyTyped[i] = keys[i] && !keyState[i];
		for (int i = 0; i < MAX_KEYS; i++)
			keyState[i] = keys[i];

		for (int i = 0; i < MAX_BUTTONS; i++)
			buttonClicked[i] = buttons[i] && !buttonState[i];
		for (int i = 0; i < MAX_BUTTONS; i++)
			buttonState[i] = buttons[i];
		// Input.SCROLL = 0;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		keys[key] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		keys[key] = false;
		System.out.println("Key: " + e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		SCROLL = e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		DX = e.getX();
		DY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		MX = e.getX();
		MY = e.getY();
		DX = e.getX();
		DY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int b = e.getButton();
		buttons[b] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int b = e.getButton();
		buttons[b] = false;
	}

	public static boolean isKeyDown(int key) {
		if (key >= 0 && key <= keys.length - 1) return keys[key];
		return false;
	}

	public static boolean isKeyTyped(int key) {
		if (key >= 0 && key <= keyTyped.length - 1) return keyTyped[key];
		return false;
	}

	public static boolean isButtonDown(int button) {
		if (button >= 0 && button <= buttons.length - 1) return buttons[button];
		return false;
	}

	public static boolean isButtonClicked(int button) {
		if (button >= 0 && button <= buttonClicked.length - 1) return buttonClicked[button];
		return false;
	}

	public static boolean isMouseOver(int x, int y, int w, int h) {
		return (MX >= x && MX <= x + w && MY >= y && MY <= y + h);
	}

	public static boolean isMouseOver(int x, int y, int w, int h, int offX, int offY) {
		return (MX + offX >= x && MX + offX <= x + w && MY + offY >= y && MY + offY <= y + h);
	}

	public static boolean isDragMouseOver(int x, int y, int w, int h, int offX, int offY) {
		return (DX + offX >= x && DX + offX <= x + w && DY + offY >= y && DY + offY <= y + h);
	}
}
