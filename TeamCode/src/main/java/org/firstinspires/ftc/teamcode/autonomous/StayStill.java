package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.collections.Motors;
import org.firstinspires.ftc.teamcode.collections.Sensors;
import org.firstinspires.ftc.teamcode.pathfollower2.DOFs;
import org.firstinspires.ftc.teamcode.pathfollower2.PID;
import org.firstinspires.ftc.teamcode.pathfollower2.Path;
import org.firstinspires.ftc.teamcode.pathfollower2.PathBuilder;
import org.firstinspires.ftc.teamcode.pathfollower2.PathFollower;

import java.util.HashMap;

@Autonomous(name = "StayStill") // Max X : 24 Max Y : 48
public class StayStill extends LinearOpMode {
    @Override
    public void runOpMode() {
        Motors motors = new Motors();
        Sensors sensors = new Sensors();
        motors.init(hardwareMap);
        sensors.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.speak("I have been initialized!");
        telemetry.update();
        waitForStart();

        Path path = new PathBuilder()
                .isFinished((Double _) -> false)
                .buildSegment()
                .build();

        PathFollower pathFollower = new PathFollower(path, new DOFs(sensors.odometry, motors), new HashMap<DOFs.DOF, PID.K>() {{
            put(DOFs.DOF.X, new PID.K(0.2, 0.1, 0.02));
            put(DOFs.DOF.Y, new PID.K(0.2, 0.1, 0.02));
            put(DOFs.DOF.THETA, new PID.K(0.25, 0.05, 0.0025));
        }}, new HashMap<DOFs.DOF, PathFollower.K>() {{
            put(DOFs.DOF.X, new PathFollower.K(TuningConstants.x, 1));
            put(DOFs.DOF.Y, new PathFollower.K(TuningConstants.y, 1));
            put(DOFs.DOF.THETA, new PathFollower.K(TuningConstants.theta, 1));
        }}, this::isStopRequested);

        pathFollower.run(telemetry);
    }
}
