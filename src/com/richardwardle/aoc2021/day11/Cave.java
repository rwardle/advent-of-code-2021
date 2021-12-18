package com.richardwardle.aoc2021.day11;

import com.richardwardle.aoc2021.Point;

import java.util.*;
import java.util.stream.Collectors;

public class Cave {
    private final Map<Point, Octopus> octopuses = new HashMap<>();

    public void addOctopus(Octopus octopus) {
        octopuses.put(octopus.point(), octopus);
    }

    public int step() {
        var flashedTotal = new HashSet<Point>();

        List<Point> workingSet = new ArrayList<>(octopuses.keySet());
        while (!workingSet.isEmpty()) {
            incrementEnergy(workingSet);

            var flashedCurrent = getFlashed(workingSet);
            flashedTotal.addAll(flashedCurrent);

            if (flashedCurrent.isEmpty()) {
                workingSet = Collections.emptyList();
            } else {
                // Working set is now adjacent minus already flashed
                workingSet = getAdjacent(flashedCurrent).stream()
                        .filter(point -> !flashedTotal.contains(point))
                        .collect(Collectors.toList());
            }
        }

        // Reset flashed energy level
        flashedTotal.forEach(point -> octopuses.put(point, new Octopus(point, 0)));

        return flashedTotal.size();
    }

    private void incrementEnergy(List<Point> points) {
        points.stream()
                .map(octopuses::get)
                .filter(Objects::nonNull)
                .forEach(octopus -> octopuses.put(
                        octopus.point(),
                        new Octopus(octopus.point(), octopus.energy() + 1)));
    }

    private Set<Point> getFlashed(List<Point> points) {
        return points.stream()
                .distinct()
                .map(octopuses::get)
                .filter(Objects::nonNull)
                .filter(octopus -> octopus.energy() > 9)
                .map(Octopus::point)
                .collect(Collectors.toSet());
    }

    private List<Point> getAdjacent(Set<Point> points) {
        return points.stream()
                .<Point>mapMulti((point, mapper) -> point.adjacentPoints().forEach(mapper))
                .toList();
    }

    @Override
    public String toString() {
        return "Cave{" +
                "octopuses=" +
                octopuses.values().stream()
                        .sorted(Comparator.comparing(octopus -> octopus.point().y())).toList() +
                '}';
    }
}
