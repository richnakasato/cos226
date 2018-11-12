/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private static final int BREAKPOINT = 2;

    private ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {    // finds all line 4 pt segments
        segments = new ArrayList<>();
        if (points == null) {
            throw new IllegalArgumentException();
        }
        Arrays.sort(points);
        for (int i = 1; i < points.length; ++i) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        ArrayList<Point> heads = new ArrayList<>();
        ArrayList<Point> tails = new ArrayList<>();
        for (int p = 0; p < points.length - BREAKPOINT; ++p) {
            Point head = points[p];
            System.out.println(head);
            ArrayList<Point> otherPointsList = new ArrayList<>();
            for (int q = 0; q < points.length; ++q) {
                if (q != p) otherPointsList.add(points[q]);
            }
            System.out.println(otherPointsList.size());
            Point[] otherPoints = new Point[otherPointsList.size()];
            otherPoints = otherPointsList.toArray(otherPoints);
            Arrays.sort(otherPoints, head.slopeOrder());

            ArrayList<Point[]> linesPoints = new ArrayList<>();
            ArrayList<Point> possibleLine = new ArrayList<>();
            double currSlope = +0.0;
            for (Point point : otherPoints) {
                if (possibleLine.isEmpty()) {
                    currSlope = head.slopeTo(point);
                    possibleLine.add(head);
                    possibleLine.add(point);
                }
                else {
                    if (currSlope == head.slopeTo(point)) {
                        possibleLine.add(point);
                    }
                    else {
                        if (possibleLine.size() > BREAKPOINT) {
                            Point[] linePoints = new Point[possibleLine.size()];
                            linePoints = possibleLine.toArray(linePoints);
                            linesPoints.add(linePoints);
                        }
                        currSlope = head.slopeTo(point);
                        possibleLine.clear();
                        possibleLine.add(head);
                        possibleLine.add(point);
                    }
                }
            }

            for (int i = 0; i < linesPoints.size(); ++i) {
                Arrays.sort(linesPoints.get(i));
                heads.add(linesPoints.get(i)[0]);
                tails.add(linesPoints.get(i)[linesPoints.get(i).length - 1]);
            }
        }

        // TODO: FIX THIS PART!!!!
        segments.add(new LineSegment(heads.get(0), tails.get(0)));
        for (int i = 1; i < heads.size(); ++i) {
            if (heads.get(i).compareTo(heads.get(i - 1)) != 0
                    || tails.get(i).compareTo(tails.get(i - 1)) != 0) {
                segments.add(new LineSegment(heads.get(i), tails.get(i)));
            }
        }
    }

    public int numberOfSegments() {                 // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() {               // the line segments
        LineSegment[] ret = new LineSegment[segments.size()];
        return segments.toArray(ret);
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
        System.out.println(collinear.numberOfSegments());
        StdDraw.show();
    }
}
