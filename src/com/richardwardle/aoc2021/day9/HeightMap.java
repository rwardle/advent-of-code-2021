package com.richardwardle.aoc2021.day9;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

class HeightMap {
    private final Map<Position, Height> heightMap = new HashMap<>();

    static HeightMap heightMapFrom(List<String> lines) {
        var heightMap = new HeightMap();
        for (int y = 0; y < lines.size(); y++) {
            var heights = lines.get(y).split("");
            for (int x = 0; x < heights.length; x++) {
                var height = new HeightMap.Height(Integer.parseInt(heights[x]), new HeightMap.Position(x, y));
                heightMap.addHeight(height);
            }
        }
        return heightMap;
    }

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

    List<Set<Position>> getBasins() {
        var maxX = heightMap.keySet().stream().mapToInt(Position::x).max().orElseThrow();
        var maxY = heightMap.keySet().stream().mapToInt(Position::y).max().orElseThrow();
        var heightNinePositions = heightMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().value() == 9)
                .map(Map.Entry::getKey)
                .toList();

        Function<Position, Boolean> isValid = (position) -> position.x() >= 0
                && position.x() <= maxX
                && position.y() >= 0
                && position.y() <= maxY;

        var basins = new ArrayList<Set<Position>>();
        for (Height lowPoint : getLowPoints()) {
            var basinPositions = new HashSet<Position>();
            var positionsToProcess = new Stack<Position>();
            Consumer<Position> processPosition = position -> {
                if (isValid.apply(position) && !heightNinePositions.contains(position)) {
                    if (basinPositions.add(position)) {
                        positionsToProcess.push(position);
                    }
                }
            };

            positionsToProcess.push(lowPoint.position());
            while (!positionsToProcess.empty()) {
                Position next = positionsToProcess.pop();
                List.of(positionAbove(next), positionBelow(next), positionToLeft(next), positionToRight(next))
                        .forEach(processPosition);
            }

            basins.add(basinPositions);
        }

        return basins;
    }

    record Position(int x, int y) {
    }

    record Height(int value, Position position) {
    }
}
