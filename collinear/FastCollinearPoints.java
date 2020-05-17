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

public class FastCollinearPoints {
    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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
        for (Point p : pointsCopy) {
            Arrays.sort(points, p.slopeOrder());

            Point firstSegmentPoint = null;
            Point minSegmentPoint = null;
            Point maxSegmentPoint = null;
            Point prevPoint = null;
            int numberOfPoints = 1;
            double currentSlope = 0;
            for (Point x : points) {
                if (firstSegmentPoint == null) {
                    firstSegmentPoint = x;
                    minSegmentPoint = x;
                    maxSegmentPoint = x;
                    prevPoint = x;
                    continue;
                }

                if (x.equals(prevPoint)) {
                    throw new IllegalArgumentException();
                }

                double xSlope = firstSegmentPoint.slopeTo(x);
                if (numberOfPoints == 1) {
                    currentSlope = xSlope;
                    numberOfPoints++;
                    minSegmentPoint = x.compareTo(minSegmentPoint) < 0 ? x : minSegmentPoint;
                    maxSegmentPoint = x.compareTo(maxSegmentPoint) > 0 ? x : maxSegmentPoint;
                }
                else if (compareSlopes(xSlope, currentSlope)) {
                    numberOfPoints++;
                    minSegmentPoint = x.compareTo(minSegmentPoint) < 0 ? x : minSegmentPoint;
                    maxSegmentPoint = x.compareTo(maxSegmentPoint) > 0 ? x : maxSegmentPoint;
                }
                else {
                    if (numberOfPoints >= 4) {
                        this.addSegment(p, minSegmentPoint, maxSegmentPoint);
                    }

                    minSegmentPoint = x.compareTo(firstSegmentPoint) < 0 ? x : firstSegmentPoint;
                    maxSegmentPoint = x.compareTo(firstSegmentPoint) > 0 ? x : firstSegmentPoint;
                    numberOfPoints = 2;
                    currentSlope = xSlope;
                }

                prevPoint = x;
            }

            if (numberOfPoints >= 4) {
                this.addSegment(p, minSegmentPoint, maxSegmentPoint);
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

    private void addSegment(Point currentMinPoint, Point min, Point max) {
        if (min.compareTo(currentMinPoint) == 0) {
            this.segments.add(new LineSegment(min, max));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
