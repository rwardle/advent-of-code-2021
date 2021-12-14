package com.richardwardle.aoc2021.day9;

import java.util.*;

class HeightMap {
    private final Map<Position, Height> heightMap = new HashMap<>();

    private static Position positionToLeft(Position position) {
        return new Position(position.x() - 1, position.y());
    }

    private static Position positionToRight(Position position) {
        return new Position(position.x() + 1, position.y());
    }

    private static Position positionAbove(Position position) {
        return new Position(position.x(), position.y() - 1);
    }

    private static Position positionBelow(Position position) {
        return new Position(position.x(), position.y() + 1);
    }

    public void addHeight(Height height) {
        heightMap.put(height.position(), height);
    }

    public Set<Height> getLowPoints() {
        var maxX = heightMap.keySet().stream().mapToInt(Position::x).max().orElseThrow();
        var maxY = heightMap.keySet().stream().mapToInt(Position::y).max().orElseThrow();

        var lowPoints = new HashSet<Height>();
        for (int y = 0; y < maxY + 1; y++) {
            for (int x = 0; x < maxX + 1; x++) {
                Height height = heightMap.get(new Position(x, y));
                if (isLowPoint(height)) {
                    lowPoints.add(height);
                }
            }
        }

        return lowPoints;
    }

    private boolean isLowPoint(Height currentHeight) {
        var currentPosition = currentHeight.position();
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

    @Override
    public String toString() {
        return "HeightMap{" +
                "data=" + heightMap +
                '}';
    }

    record Position(int x, int y) {
    }

    record Height(int value, Position position) {
    }
}
