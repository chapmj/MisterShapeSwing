package controller;

import model.PointInt;
import model.shape.ShapeComponent;
import model.shape.ShapeColor;
import model.shape.ShapePosition;

import java.awt.*;
import java.util.List;

public class CanvasUtils
{
	// Returns true if two rectangle boundaries overlap
	public static boolean compareRects(ShapePosition pos1, ShapePosition pos2) {
	
		int r1TopY = pos1.getLeft().getY();
		int r1BotY = pos1.getRight().getY();
		int r2TopY = pos2.getLeft().getY();
		int r2BotY = pos2.getRight().getY();
		
		int r1LeftX = pos1.getLeft().getX();
		int r1RightX = pos1.getRight().getX();
		int r2LeftX = pos2.getLeft().getX();
		int r2RightX = pos2.getRight().getX();
	
		return !((r1LeftX > r2RightX) 
		      || (r1RightX < r2LeftX) 
		      || (r1TopY > r2BotY) 
		      || (r1BotY < r2TopY));
	}	

	// Translates a shape component to a new position	
	public static void moveShape(ShapeComponent shape, PointInt translation)
	{
		shape.setAnchor(
			new PointInt(
				shape.getAnchor().getX() + translation.getX(),
				shape.getAnchor().getY() + translation.getY())); 
	}

	// Translates a set of shapes components to a new position
	//public static void moveShapes(List<ShapeComponent> components, PointInt translation)
	//{
	//	components.forEach(c -> moveShape(c,translation));
	//}

	public static Color toColor(ShapeColor color)
	{
		switch (color) {
			case BLACK:
				return Color.BLACK;

			case BLUE:
				return Color.BLUE;

			case CYAN:
				return Color.CYAN;

			case DARK_GRAY:
				return Color.DARK_GRAY;
				
			case GRAY:
				return Color.GRAY;
				
			case GREEN:
				return Color.GREEN;

			case LIGHT_GRAY:
				return Color.LIGHT_GRAY;
				
			case MAGENTA:
				return Color.MAGENTA;
				
			case ORANGE:
				return Color.ORANGE;
				
			case PINK:
				return Color.PINK;

			case RED:
				return Color.RED;

			case WHITE:
				return Color.WHITE;

			case YELLOW:
				return Color.YELLOW;
		
			default:
				return Color.BLACK;
		}
	}
}
