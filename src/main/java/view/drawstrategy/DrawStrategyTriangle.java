package view.drawstrategy;

import model.interfaces.IShape;
import model.persistence.ModelState;
import model.shape.ShapeCardinality;
import model.shape.ShapeShadingType;
import view.viewstate.ViewState;

import java.awt.*;

/* Create Graphics objects and paint them to canvas.
 * Specific graphic type in class name.
 * STRATEGY PATTERN
 */
public class DrawStrategyTriangle extends DrawStrategy {
	private Stroke stroke;
	private Graphics2D graphics;
	private Color primaryColor;
	private Color secondaryColor;
	private ShapeShadingType shadingType;
	private ShapeCardinality cardinality;
	//private Graphics2D graphicsSelection;

	public DrawStrategyTriangle(IShape shape, Graphics2D graphics)
	{
		super(shape);
		this.graphics = graphics;
		//this.graphicsSelection = graphics;
		this.shape = shape;
		setStyleParams();
	}

	// Draw shape to canvas and then determine if a selection should be drawn as well.
	@Override
	public void draw() {
		paintShapeWithShading();
		drawSelection();
	}
	
	private void setStyleParams() {
		primaryColor = this.getPrimaryColor();
		secondaryColor = this.getSecondaryColor();
		shadingType = this.getShadingType();
		cardinality = shape.getCardinality();
	}
	
	private void paintShapeWithShading() {
		switch(shadingType) {
			case FILLED_IN: 
				fillShape(primaryColor);
				break;
			case OUTLINE:
				drawShape(primaryColor);
				break;
			case OUTLINE_AND_FILLED_IN:
				fillShape(primaryColor);
				drawShape(secondaryColor);
				break;
		}
	}

	private void drawSelection() {

		double scale = 1.2f;
		ModelState.getShapeComponentSelectionList().stream()
				.filter((s) -> s.equals(shape))
                .limit(1)
                .forEach((shape) ->
					{
						graphics.setColor(Color.BLACK);
						graphics.setStroke(stroke);
						RawPoly rp = createTrianglePolygon(shape);
						Polygon selectionTriangle = dilateTriangle(rp.xs, rp.ys, scale);
						graphics.drawPolygon(selectionTriangle);
					});
	}

	// Return a polygon with the same center point as a given polygon, but scaled.
	private Polygon dilateTriangle(int[] xs, int[] ys, Double scale) {
		// Citation https://stackoverflow.com/questions/8591991/algorithm-to-enlarge-scale-inflate-enbiggen-a-triangle
		int xsum = 0;
		int ysum = 0;
		for (int i = 0; i < 3; i++) {
			xsum += xs[i];
			ysum += ys[i];
		}

		double xcent = xsum / 3.0;
		double ycent = ysum / 3.0;
		
		int[] xs_new = new int[3];
		int[] ys_new = new int[3];
		for (int i = 0; i < 3; i++) {
			xs_new[i] = (int) (xcent + (xs[i]-xcent)*scale);
			ys_new[i] = (int) (ycent + (ys[i]-ycent)*scale);
		}
		return new Polygon(xs_new,ys_new,3);
	}

	private void drawShape(Color color) {
		Polygon triangle = createTrianglePolygon(shape).poly;
		graphics.setColor(color);
		graphics.setStroke(stroke);
		graphics.drawPolygon(triangle);
	}
	
	private void fillShape(Color color) {
		Polygon triangle = createTrianglePolygon(shape).poly;
		graphics.setColor(color);
		graphics.fillPolygon(triangle);
	}

	private RawPoly createTrianglePolygon(IShape shape) {
		IShape triangle = shape.clone();
		int width = triangle.getWidth();
		int height = triangle.getHeight();
		Polygon trianglePoly = new Polygon();
		int x1 = triangle.getAnchor().getX();
		int x2 = triangle.getAnchor().getX();
		int x3 = triangle.getAnchor().getX();
		int y1 = triangle.getAnchor().getY();
		int y2 = triangle.getAnchor().getY();
		int y3 = triangle.getAnchor().getY();

		switch (cardinality) {
			case NE:
				x2 += width;
				y3 += height;
				break;
			case NW:
				x2 += width;
				x3 += width;
				y3 += height;
				break;
			case SE:
				y2 += height;
				x3 += width;
				y3 += height;
				break;
			case SW:
				x1 += width;
				y2 += height;
				x3 += width;
				y3 += height;
				break;
		}

		trianglePoly.addPoint(x1, y1);
		trianglePoly.addPoint(x2, y2);
		trianglePoly.addPoint(x3 , y3);
		RawPoly rp = new RawPoly();
		rp.poly=trianglePoly;
		int[] xr = {x1, x2, x3};
		int[] yr = {y1, y2, y3};
		rp.xs = xr; 
		rp.ys = yr;

		return rp;
	}

	static class RawPoly {
		Polygon poly;
		int[] xs;
		int[] ys;
	}
}
