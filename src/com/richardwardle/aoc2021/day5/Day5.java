package com.richardwardle.aoc2021.day5;

import com.richardwardle.aoc2021.Point;
import com.richardwardle.aoc2021.input.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) {
        new Day5().execute();
    }

    private void execute() {
        var points = parseInput()
                .stream()
//                .filter(s -> s.isHorizontal() || s.isVertical())
                .mapMulti((segment, mapper) -> segment.pointsOnLine().forEach(mapper))
                .collect(Collectors.groupingBy(point -> point, Collectors.counting()));
        System.out.println(points.values().stream().filter(count -> count > 1).count());
    }

    private List<LineSegment> parseInput() {
        var lineSegments = new ArrayList<LineSegment>();

        try (Scanner scanner = InputUtils.day5Data()) {
            while (scanner.hasNext()) {
                lineSegments.add(parseLineSegment(scanner.nextLine()));
            }
        }

        return lineSegments;
    }

    private LineSegment parseLineSegment(String line) {
        var tokens = line.split(" -> ");
        if (tokens.length != 2) {
            throw new RuntimeException("Line segment should have 2 points, but actually had: " + tokens.length);
        }

        return new LineSegment(parsePoint(tokens[0]), parsePoint(tokens[1]));
    }

    private Point parsePoint(String coordinates) {
        var tokens = coordinates.split(",");
        if (tokens.length != 2) {
            throw new RuntimeException("Coordinates should have 2 numbers, but actually had: " + tokens.length);
        }

        return new Point(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
    }
}
