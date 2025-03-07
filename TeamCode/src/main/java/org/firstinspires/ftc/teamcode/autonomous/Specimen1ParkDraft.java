package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.collections.Motors;
import org.firstinspires.ftc.teamcode.collections.Sensors;
import org.firstinspires.ftc.teamcode.pathfollower2.DOFs;
import org.firstinspires.ftc.teamcode.pathfollower2.PID;
import org.firstinspires.ftc.teamcode.pathfollower2.Path;
import org.firstinspires.ftc.teamcode.pathfollower2.PathBuilder;
import org.firstinspires.ftc.teamcode.pathfollower2.PathFollower;
import org.firstinspires.ftc.teamcode.pathfollower2.TuningConstants;

import java.util.HashMap;

@Autonomous(name = "1 Specimen + Park")
public class Specimen1ParkDraft extends LinearOpMode {
    @Override
    public void runOpMode() {
        Motors motors = new Motors();
        Sensors sensors = new Sensors();
        motors.init(hardwareMap);
        sensors.init(hardwareMap);

        sensors.odometry.setPosition(new Pose2D(DistanceUnit.INCH, 0, 72, AngleUnit.DEGREES, 0));

        telemetry.addData("Status", "Initialized");
        telemetry.speak("I have been initialized!");
        telemetry.update();
        waitForStart();

        Path path = new PathBuilder()
                .linearTo(new HashMap<DOFs.DOF, Double>() {{
                    put(DOFs.DOF.X, 24.0);
                    put(DOFs.DOF.Y, 72.0);
                    put(DOFs.DOF.THETA, 0.0);
                }}, 5)
                .addTime(5)
                .linearTo(new HashMap<DOFs.DOF, Double>() {{
                    put(DOFs.DOF.X, 0.0);
                    put(DOFs.DOF.Y, 120.0);
                    put(DOFs.DOF.THETA, 0.0);
                }}, 5)
                .buildSegment()
                .build();

        PathFollower pathFollower = new PathFollower(path, new DOFs(sensors.odometry, motors), new HashMap<DOFs.DOF, PID.K>() {{
            put(DOFs.DOF.X, new PID.K(1, 0.1, 0.05));
            put(DOFs.DOF.Y, new PID.K(1, 0.1, 0.05));
            put(DOFs.DOF.THETA, new PID.K(1, 0.1, 0.05));
        }}, new HashMap<DOFs.DOF, PathFollower.K>() {{
            for (DOFs.DOF dof : DOFs.DOF.values()) put(dof, new PathFollower.K(TuningConstants.v.get(dof), 0, 1));
        }}, this::isStopRequested);

        pathFollower.run(telemetry);
    }
}
