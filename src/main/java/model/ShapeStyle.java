package model;

public final class ShapeStyle {
	public final ShapeColor primaryColor;
	public final ShapeColor secondaryColor;
	public final ShapeShadingType shadingType;

	public ShapeStyle(ShapeColor primaryColor, ShapeColor secondaryColor, ShapeShadingType shadingType) {
		//TODO:  if any of these are null throw an error
		super();
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.shadingType = shadingType;
	}
	
	public ShapeStyle clone() {
		return new ShapeStyle(primaryColor, secondaryColor, shadingType);
	}
}
