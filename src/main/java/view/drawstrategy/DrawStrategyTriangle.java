package view.drawstrategy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;

import model.shape.ShapeCardinality;
import model.shape.ShapeShadingType;
import model.interfaces.IShape;
import model.shape.ShapeComponent;
import model.persistence.ModelState;
import view.viewstate.ViewState;

/* Create Graphics objects and paint them to canvas.
 * Specific graphic type in class name.
 * STRATEGY PATTERN
 */
public class DrawStrategyTriangle extends DrawStrategy {
	private Stroke stroke;
	private IShape shape;
	private Graphics2D graphics;
	private Color primaryColor;
	private Color secondaryColor;
	private ShapeShadingType shadingType;
	private ShapeCardinality cardinality;
	private Graphics2D graphicsSelection;
	private DrawStrategyCommon common;

	public DrawStrategyTriangle(ShapeComponent shape)
	{
		super();
		this.graphics = ViewState.getGraphics();
		this.graphicsSelection = ViewState.getGraphics();
		this.shape = (IShape) shape;
		this.common = new DrawStrategyCommon((IShape) shape);
		setStyleParams();
	}

	// Draw shape to canvas and then determine if a selection should be drawn as well.
	@Override
	public void draw() {
		paintShapeWithShading();
		drawSelection();
	}
	
	private void setStyleParams() {
		stroke = DrawStrategyCommon.makeStroke();
		primaryColor = common.getPrimaryColor();
		secondaryColor = common.getSecondaryColor();
		shadingType = common.getShadingType();
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
		/*
		var selectedShapes = canvasController.getShapeSelectionList();
		double scale = 1.2;
		if (selectedShapes.contains(shape)) {
			g2Dselected.setColor(Color.BLACK);
			g2Dselected.setStroke(stroke);
			RawPoly rp = createTrianglePolygon(shape);
			Polygon selectionTriangle = dilateTriangle(rp.xs, rp.ys, scale);
			g2Dselected.drawPolygon(selectionTriangle);
		}*/

		double scale = 1.2;
		ModelState.getShapeComponentSelectionList().stream()
				.filter((s) -> s.equals(shape))
                .limit(1)
                .forEach((shape) ->
					{
						graphicsSelection.setColor(Color.BLACK);
						graphicsSelection.setStroke(stroke);
						RawPoly rp = createTrianglePolygon((IShape)shape);
						Polygon selectionTriangle = dilateTriangle(rp.xs, rp.ys, scale);
						graphicsSelection.drawPolygon(selectionTriangle);
					});
	}

	// Return a polygon with the same center point as a given polygon, but scaled.
	private Polygon dilateTriangle(int[] xs, int[] ys, Double scale) {
		// Citation https://stackoverflow.com/questions/8591991/algorithm-to-enlarge-scale-inflate-enbiggen-a-triangle
		int xsum = 0;
		int ysum = 0;
		for (int i=0; i<3; i++) {
			xsum+=xs[i];
			ysum+=ys[i];
		}

		double xcent = xsum / 3;
		double ycent = ysum / 3;
		
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
		IShape triangle = (IShape) shape.clone();
		Integer x = triangle.getAnchor().getX();
		Integer y = triangle.getAnchor().getY();
		Integer width = triangle.getWidth();
		Integer height = triangle.getHeight();
		Polygon trianglePoly = new Polygon();
		Integer x1 = x;
		Integer x2 = x;
		Integer x3 = x;
		Integer y1 = y;
		Integer y2 = y;
		Integer y3 = y;

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

	class RawPoly {
		Polygon poly;
		int[] xs;
		int[] ys;
	}
}
