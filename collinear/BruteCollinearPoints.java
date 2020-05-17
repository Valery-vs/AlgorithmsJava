/* *****************************************************************************
 *  Name: Valery Smirnov
 *  Date: 05/17/2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        this.segments = new ArrayList<LineSegment>();
        this.findSegments(points);
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[this.segments.size()];
        this.segments.toArray(result);

        return result;
    }

    private void findSegments(Point[] points) {
        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pointsCopy[i] = points[i];
        }
        Arrays.sort(pointsCopy);
        for (int p1 = 0; p1 < pointsCopy.length; p1++) {
            Point point1 = pointsCopy[p1];
            this.checkPointNull(point1);

            for (int p2 = p1 + 1; p2 < pointsCopy.length; p2++) {
                Point point2 = pointsCopy[p2];
                this.checkPointNull(point2);
                this.checkPointsEqual(point1, point2);

                boolean found = false;
                double slope12 = point1.slopeTo(point2);

                for (int p3 = p2 + 1; p3 < pointsCopy.length; p3++) {
                    Point point3 = pointsCopy[p3];
                    this.checkPointNull(point3);
                    this.checkPointsEqual(point1, point3);

                    double slope13 = point1.slopeTo(point3);
                    if (!compareSlopes(slope12, slope13)) {
                        continue;
                    }

                    for (int p4 = p3 + 1; p4 < pointsCopy.length; p4++) {
                        Point point4 = pointsCopy[p4];
                        this.checkPointNull(point4);
                        this.checkPointsEqual(point1, point4);

                        double slope14 = point1.slopeTo(point4);
                        if (compareSlopes(slope12, slope14)) {
                            segments.add(new LineSegment(point1, point4));
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        break;
                    }
                }

                if (found) {
                    break;
                }
            }
        }
    }

    private boolean compareSlopes(double slope1, double slope2) {
        return Math.abs(slope1 - slope2) <= 0.000001 ||
                (slope1 == Double.POSITIVE_INFINITY
                        && slope2 == Double.POSITIVE_INFINITY) ||
                (slope1 == Double.NEGATIVE_INFINITY
                        && slope2 == Double.NEGATIVE_INFINITY);
    }

    private void checkPointNull(Point p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkPointsEqual(Point p1, Point p2) {
        if (p1.equals(p2)) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
