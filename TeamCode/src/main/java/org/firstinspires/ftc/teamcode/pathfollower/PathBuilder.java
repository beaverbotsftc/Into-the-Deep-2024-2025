package org.firstinspires.ftc.teamcode.pathfollower;

import java.util.function.Function;

public class PathBuilder {
    private double t;
    private double startingX;
    private double startingY;
    private double startingTheta;
    private Function<Double, Double> _x;
    private Function<Double, Double> _y;
    private Function<Double, Double> _theta;

    double x(double t) {
        return _x.apply(t);
    }
    double y(double t) {
        return _y.apply(t);
    }
    double theta(double t) { return _theta.apply(t); }

    public PathBuilder setStartingPose(double startingX, double startingY, double startingTheta) {
        this.startingX = startingX;
        this.startingY = startingY;
        this.startingTheta = startingTheta;
        return this;
    }

    public PathBuilder linarTo(double x, double y, double theta, double time) {
        this._x =     (Double t) -> t > this.t ? ((x     - this.x(t))     / time) * (t - this.t) + this.x(this.t)     : this.x(t);
        this._y =     (Double t) -> t > this.t ? ((y     - this.y(t))     / time) * (t - this.t) + this.y(this.t)     : this.y(t);
        this._theta = (Double t) -> t > this.t ? ((theta - this.theta(t)) / time) * (t - this.t) + this.theta(this.t) : this.theta(t);
        this.t = this.t + time;
        return this;
    }

    public PathBuilder linarTo(double x, double y, double time) {
        return this.linarTo(x, y, this.theta(this.t), time);
    }


    public PathBuilder wait(double time) {
        return this.linarTo(x(t), y(t), theta(t), time);
    }

    public Path build() {
        return new Path(_x, _y, _theta);
    }

    public PathBuilder() {
        this.t = 0;
        this.startingX = 0;
        this.startingY = 0;
    }
}