package net.jfabricationgames.bunkers_and_badasses.game;

import java.awt.Color;

public enum UserColor {
	
	RED(new Color(225, 0, 0, UserColorManager.COLOR_ALPHA)),
	GREEN(new Color(0, 225, 0, UserColorManager.COLOR_ALPHA)),
	BLUE(new Color(0, 0, 170, UserColorManager.COLOR_ALPHA)),
	YELLOW(new Color(255, 255, 0, UserColorManager.COLOR_ALPHA)),
	ORANGE(new Color(255, 125, 0, UserColorManager.COLOR_ALPHA)),
	PINK(new Color(225, 0, 225, UserColorManager.COLOR_ALPHA)),
	PURPLE(new Color(125, 0, 255, UserColorManager.COLOR_ALPHA)),
	BLACK(new Color(0, 0, 0, UserColorManager.COLOR_ALPHA)),
	WHITE(new Color(225, 255, 225, UserColorManager.COLOR_ALPHA)),//for empty fields without troops
	GRAY(new Color(150, 150, 150, UserColorManager.COLOR_ALPHA));//for neutral troops on field
	
	private final Color color;
	
	public static final UserColor EMPTY = WHITE;
	public static final UserColor NEUTRAL = GRAY;
	
	private UserColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public static int getAvailableColors() {
		return values().length-2;//last two are for empty and neutral fields
	}
}