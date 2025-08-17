package org.beaverbots.beavertracking;

import java.util.function.DoubleUnaryOperator;

public final class PathAxis {
    private static final double epsilon = 0.001;

    private final DoubleUnaryOperator path;
    private final double startTime;
    private final double endTime;

    public PathAxis(DoubleUnaryOperator path, double startTime, double endTime) {
        if (endTime <= startTime) {
            throw new IllegalArgumentException("End time must be greater than start time.");
        }
        this.path = path;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void checkBounds(double t) {
        if (t < startTime || t > endTime) {
            throw new IllegalArgumentException(
                    String.format("Parameter t=%.4f is out of the valid range [%.4f, %.4f]", t, startTime, endTime)
            );
        }
    }

    public double position(double t) {
        checkBounds(t);
        return path.applyAsDouble(t);
    }

    public double velocity(double t) {
        checkBounds(t);

        // At the start of the interval, we can only look forward. Use forward difference.
        if (t < startTime + epsilon) {
            return (position(t + epsilon) - position(t)) / epsilon;
        }

        // At the end of the interval, we can only look backward. Use backward difference.
        else if (t > endTime - epsilon) {
            return (position(t) - position(t - epsilon)) / epsilon;
        }

        // In the middle, the central difference is more accurate.
        else {
            return (position(t + epsilon) - position(t - epsilon)) / (2 * epsilon);
        }
    }

    public double acceleration(double t) {
        checkBounds(t);

        // At the start of the interval, we can only look forward. Use forward difference on velocity.
        if (t < startTime + epsilon) {
            return (velocity(t + epsilon) - velocity(t)) / epsilon;
        }

        // At the end of the interval, we can only look backward. Use backward difference on velocity.
        else if (t > endTime - epsilon) {
            return (velocity(t) - velocity(t - epsilon)) / epsilon;
        }

        // In the middle, the central difference is more accurate.
        else {
            return (velocity(t + epsilon) - velocity(t - epsilon)) / (2 * epsilon);
        }
    }
}