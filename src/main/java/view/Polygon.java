package view;

import model.shape.ShapeCardinality;

public class Polygon
{
    public static java.awt.Polygon createTrianglePolygon(int x, int y, int w, int h, ShapeCardinality cardinality)
    {
        var points = getPoints(x, y, w, h, cardinality);

        var trianglePoly = new java.awt.Polygon();
        trianglePoly.addPoint(points[0], points[3]);
        trianglePoly.addPoint(points[1], points[4]);
        trianglePoly.addPoint(points[2], points[5]);

        return trianglePoly;
    }

    private static int[] getPoints(int x, int y, int w, int h, ShapeCardinality cardinality)
    {
        int[] xs = { x, x, x };
        int[] ys = { y, y, y };

        switch (cardinality) {
            case NE:
                xs[1] += w;
                ys[2] += h;
                break;
            case NW:
                xs[1] += w;
                xs[2] += w;
                ys[2] += h;

                break;
            case SE:
                ys[1] += h;
                xs[2] += w;
                ys[2] += h;
                break;
            case SW:
                xs[0] += w;
                ys[1] += h;
                xs[2] += w;
                ys[2] += h;
                break;
        }

        return new int[]{ xs[0], xs[1], xs[2], ys[0], ys[1], ys[2] };

    }

    public static java.awt.Polygon dilateTriangle(int x, int y, int w, int h, ShapeCardinality cardinality, Double scale) {
        // Citation https://stackoverflow.com/questions/8591991/algorithm-to-enlarge-scale-inflate-enbiggen-a-triangle

        var points = getPoints(x, y, w, h, cardinality);

        int xsum = 0;
        int ysum = 0;
        for (int i = 0, j = 3; i < 3; i++, j++) {
            xsum += points[i];
            ysum += points[j];
        }

        double xcent = xsum / 3.0;
        double ycent = ysum / 3.0;

        int[] xs_new = new int[3];
        int[] ys_new = new int[3];
        for (int i = 0, j = 3; i < 3; i++, j++) {
            xs_new[i] = (int) (xcent + (points[i] - xcent) * scale);
            ys_new[i] = (int) (ycent + (points[j] - ycent) * scale);
        }
        return new java.awt.Polygon(xs_new,ys_new,3);
    }
}
