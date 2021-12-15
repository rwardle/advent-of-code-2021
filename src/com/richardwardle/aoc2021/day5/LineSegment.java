package com.richardwardle.aoc2021.day5;

import com.richardwardle.aoc2021.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public record LineSegment(Point point1, Point point2) {

    public boolean isVertical() {
        return point1.x() == point2.x();
    }

    public boolean isHorizontal() {
        return point1.y() == point2.y();
    }

    public List<Point> pointsOnLine() {
        var points = new ArrayList<Point>();

        if (isHorizontal()) {
            horizontalRange().forEach(i -> points.add(new Point(i, point1.y())));
        } else if (isVertical()) {
            verticalRange().forEach(i -> points.add(new Point(point1.x(), i)));
        } else { // Diagonal
            IntFunction<Integer> increment = x -> x + 1;
            IntFunction<Integer> decrement = x -> x - 1;
            BiPredicate<Integer, Integer> lessThan = (x, y) -> x <= y;
            BiPredicate<Integer, Integer> greaterThan = (x, y) -> x >= y;

            // Determine whether to increment/decrement the x/y values
            var xOperator = point2.x() - point1.x() > 0 ? increment : decrement;
            var yOperator = point2.y() - point1.y() > 0 ? increment : decrement;
            var xPredicate = point2.x() - point1.x() > 0 ? lessThan : greaterThan;

            var x = point1.x();
            var y = point1.y();
            while (xPredicate.test(x, point2.x())) {
                points.add(new Point(x, y));
                x = xOperator.apply(x);
                y = yOperator.apply(y);
            }
        }

        return points;
    }

    private IntStream horizontalRange() {
        return sortedRange(new int[]{point1.x(), point2.x()});
    }

    private IntStream verticalRange() {
        return sortedRange(new int[]{point1.y(), point2.y()});
    }

    private IntStream sortedRange(int[] range) {
        Arrays.sort(range);
        return IntStream.rangeClosed(range[0], range[1]);
    }
}
