package org.firstinspires.ftc.teamcode.pathfollower2;

import java.util.HashMap;
import java.util.function.Function;

public class PathSegment {
  public HashMap<DOFs.DOF, Function<Double, Double>> f;
  public Function<Double, Boolean> isFinished;
  public Runnable onInit;
  public Runnable onIteration;
  public Runnable onInitBlocking;

  public PathSegment(HashMap<DOFs.DOF, Function<Double, Double>> f, Function<Double, Boolean> isFinished, Runnable onInit, Runnable onIteration, Runnable onInitBlocking) {
    this.f = f;
    this.isFinished = isFinished;
    this.onInit = onInit;
    this.onIteration = onIteration;
    this.onInitBlocking = onInitBlocking;
  }
}