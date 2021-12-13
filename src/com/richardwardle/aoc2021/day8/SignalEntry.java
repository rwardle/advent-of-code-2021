package com.richardwardle.aoc2021.day8;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class SignalEntry {

    private static final List<String> SEGMENTS = List.of("a", "b", "c", "d", "e", "f", "g");
    private static final Map<Integer, Integer> SEGMENT_COUNTS_TO_UNIQUE_DIGIT = Map.ofEntries(
            Map.entry(2, 1),
            Map.entry(4, 4),
            Map.entry(3, 7),
            Map.entry(7, 8)
    );
    private final Map<Integer, List<String>> signalPatterns;
    private final List<String> outPutValues;

    SignalEntry(Map<Integer, List<String>> signalPatterns, List<String> outPutValues) {
        this.signalPatterns = signalPatterns;
        this.outPutValues = outPutValues;
    }

    private static List<String> getDisabledSegments(String pattern) {
        var disabledSegments = new ArrayList<>(SEGMENTS);
        disabledSegments.removeAll(Arrays.stream(pattern.split("")).toList());
        return disabledSegments;
    }

    long calculateOutputValue() {
        var patternsToDigit = processSignalPatterns();
        var outputValueString = outPutValues.stream()
                .map(Day8Part2::sortString)
                .map(patternsToDigit::get)
                .map(String::valueOf)
                .collect(Collectors.joining());
        return Long.parseLong(outputValueString);
    }

    private Map<String, Integer> processSignalPatterns() {
        // Map from pattern to digit for digits that have unique pattern
        var patternsToDigit = signalPatterns.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() == 1)
                .collect(Collectors.toMap(
                        e -> e.getValue().get(0),
                        e -> SEGMENT_COUNTS_TO_UNIQUE_DIGIT.get(e.getKey())));

        // Process non-unique patterns
        patternsToDigit.putAll(processSignalPatterns(signalPatterns.get(5), this::processFiveSegmentPatterns));
        patternsToDigit.putAll(processSignalPatterns(signalPatterns.get(6), this::processSixSegmentPatterns));

        return patternsToDigit;
    }

    private Map<String, Integer> processSignalPatterns(List<String> patterns, Function<List<String>, Integer> processor) {
        var result = new HashMap<String, Integer>();
        for (String pattern : patterns) {
            result.put(pattern, processor.apply(getDisabledSegments(pattern)));
        }
        return result;
    }

    private Integer processFiveSegmentPatterns(List<String> segments) {
        if (patternHasSegmentsEnabled(4, segments)) {
            return 2;
        } else if (patternHasSegmentsDisabled(2, segments)) {
            return 3;
        } else {
            return 5;
        }
    }

    private Integer processSixSegmentPatterns(List<String> segments) {
        if (!patternHasSegmentsEnabled(2, segments)
                && !patternHasSegmentsEnabled(4, segments)
                && !patternHasSegmentsEnabled(3, segments)) {
            return 9;
        } else if (!patternHasSegmentsEnabled(2, segments)) {
            return 0;
        } else {
            return 6;
        }
    }

    private boolean patternHasSegmentsEnabled(int segmentLength, List<String> segments) {
        boolean result = true;
        for (String wire : segments) {
            result &= patternHasSegmentEnabled(segmentLength, wire);
        }
        return result;
    }

    private boolean patternHasSegmentsDisabled(int segmentLength, List<String> segments) {
        boolean result = true;
        for (String wire : segments) {
            result &= !patternHasSegmentEnabled(segmentLength, wire);
        }
        return result;
    }

    private boolean patternHasSegmentEnabled(int segmentLength, String segment) {
        List<String> patterns = signalPatterns.get(segmentLength);
        if (patterns.size() != 1) {
            throw new RuntimeException("Expected pattern size of 1");
        }
        return patterns.get(0).contains(segment);
    }
}
