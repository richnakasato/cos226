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

public class BruteCollinearPoints {

    private static final int HEAD = 0;
    private static final int TAIL = 3;

    private int numSegments;
    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        numSegments = 0;
        segments = new ArrayList<>();
        Arrays.sort(points);
        for (int p = 0; p < points.length; ++p) {
            for (int q = p + 1; q < points.length; ++q) {
                for (int r = q + 1; r < points.length; ++r) {
                    for (int s = r + 1; s < points.length; ++s) {
                        if (points[p].slopeTo(points[q])
                                == points[p].slopeTo(points[r])
                                && points[p].slopeTo(points[q])
                                == points[p].slopeTo(points[s])) {
                            Point[] collinear = {
                                    points[p],
                                    points[q],
                                    points[r],
                                    points[s]
                            };
                            Arrays.sort(collinear);
                            segments.add(new LineSegment(collinear[HEAD],
                                                         collinear[TAIL]));
                            ++numSegments;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {                 // the number of line segments
        return numSegments;
    }

    public LineSegment[] segments() {               // the line segments
        LineSegment[] ret = new LineSegment[numSegments];
        for (int i = 0; i < numSegments; ++i) {
            ret[i] = segments.get(i);
        }
        return ret;
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
