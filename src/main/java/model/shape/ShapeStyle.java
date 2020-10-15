package model.shape;

public final class ShapeStyle {
	public final ShapeColor primaryColor;
	public final ShapeColor secondaryColor;
	public final ShapeShadingType shadingType;

	public ShapeStyle(ShapeColor primaryColor, ShapeColor secondaryColor, ShapeShadingType shadingType) {
		super();
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.shadingType = shadingType;
	}
	
	public static ShapeStyle newInstance(ShapeStyle shapeStyle) {

		var primaryColor = shapeStyle.primaryColor;
		var secondaryColor = shapeStyle.secondaryColor;
		var shadingType = shapeStyle.shadingType;

		return new ShapeStyle(primaryColor, secondaryColor, shadingType);
	}
}
