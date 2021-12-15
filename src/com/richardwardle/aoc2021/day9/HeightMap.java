package com.richardwardle.aoc2021.day9;

import com.richardwardle.aoc2021.Point;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

class HeightMap {
    private final Map<Point, Height> heightMap = new HashMap<>();

    static HeightMap heightMapFrom(List<String> lines) {
        var heightMap = new HeightMap();
        for (int y = 0; y < lines.size(); y++) {
            var heights = lines.get(y).split("");
            for (int x = 0; x < heights.length; x++) {
                var height = new HeightMap.Height(Integer.parseInt(heights[x]), new Point(x, y));
                heightMap.addHeight(height);
            }
        }
        return heightMap;
    }

    private static Point positionToLeft(Point point) {
        return new Point(point.x() - 1, point.y());
    }

    private static Point positionToRight(Point point) {
        return new Point(point.x() + 1, point.y());
    }

    private static Point positionAbove(Point point) {
        return new Point(point.x(), point.y() - 1);
    }

    private static Point positionBelow(Point point) {
        return new Point(point.x(), point.y() + 1);
    }

    public void addHeight(Height height) {
        heightMap.put(height.point(), height);
    }

    public Set<Height> getLowPoints() {
        var maxX = heightMap.keySet().stream().mapToInt(Point::x).max().orElseThrow();
        var maxY = heightMap.keySet().stream().mapToInt(Point::y).max().orElseThrow();

        var lowPoints = new HashSet<Height>();
        for (int y = 0; y < maxY + 1; y++) {
            for (int x = 0; x < maxX + 1; x++) {
                Height height = heightMap.get(new Point(x, y));
                if (isLowPoint(height)) {
                    lowPoints.add(height);
                }
            }
        }

        return lowPoints;
    }

    private boolean isLowPoint(Height currentHeight) {
        var currentPosition = currentHeight.point();
        var adjacentPositions = List.of(
                positionToLeft(currentPosition),
                positionToRight(currentPosition),
                positionAbove(currentPosition),
                positionBelow(currentPosition));

        var numberOfLowerOrEqualPositions = adjacentPositions
                .stream()
                .map(heightMap::get)
                .filter(Objects::nonNull)
                .filter(h -> h.value() <= currentHeight.value())
                .count();
        return numberOfLowerOrEqualPositions == 0;
    }

    List<Set<Point>> getBasins() {
        var maxX = heightMap.keySet().stream().mapToInt(Point::x).max().orElseThrow();
        var maxY = heightMap.keySet().stream().mapToInt(Point::y).max().orElseThrow();
        var heightNinePositions = heightMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().value() == 9)
                .map(Map.Entry::getKey)
                .toList();

        Function<Point, Boolean> isValid = (point) -> point.x() >= 0
                && point.x() <= maxX
                && point.y() >= 0
                && point.y() <= maxY;

        var basins = new ArrayList<Set<Point>>();
        for (Height lowPoint : getLowPoints()) {
            var basinPositions = new HashSet<Point>();
            var positionsToProcess = new Stack<Point>();
            Consumer<Point> processPosition = point -> {
                if (isValid.apply(point) && !heightNinePositions.contains(point)) {
                    if (basinPositions.add(point)) {
                        positionsToProcess.push(point);
                    }
                }
            };

            positionsToProcess.push(lowPoint.point());
            while (!positionsToProcess.empty()) {
                Point next = positionsToProcess.pop();
                List.of(positionAbove(next), positionBelow(next), positionToLeft(next), positionToRight(next))
                        .forEach(processPosition);
            }

            basins.add(basinPositions);
        }

        return basins;
    }

    record Height(int value, Point point) {
    }
}
