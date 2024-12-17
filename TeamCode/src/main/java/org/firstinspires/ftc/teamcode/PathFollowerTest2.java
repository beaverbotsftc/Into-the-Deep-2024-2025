package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.collections.Motors;
import org.firstinspires.ftc.teamcode.collections.Sensors;
import org.firstinspires.ftc.teamcode.pathfollower2.DOFs;
import org.firstinspires.ftc.teamcode.pathfollower2.PID;
import org.firstinspires.ftc.teamcode.pathfollower2.Path;
import org.firstinspires.ftc.teamcode.pathfollower2.PathFollower;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


@TeleOp(name="Path follower test 2")
public class PathFollowerTest2 extends LinearOpMode {
    @Override
    public void runOpMode() {

        Motors motors = new Motors();
        Sensors sensors = new Sensors();
        motors.init(hardwareMap);
        sensors.init(hardwareMap);

        Path path = new Path.PathBuilder()
                .linearTo(new HashMap<DOFs.DOF, Double>() {{
                    put(DOFs.DOF.X, 24.0);
                    put(DOFs.DOF.Y, 0.0);
                    put(DOFs.DOF.THETA, 0.0);
                }}, 5)
                .addTime(15.0)
                .buildSegment()
                .build();
        telemetry.addData("A", path.paths.get(0).f.get(DOFs.DOF.X).apply(180.0));

        PathFollower pathFollower = new PathFollower(path, new DOFs(sensors.odometry, motors), new HashMap<DOFs.DOF, PID.K>() {{
            put(DOFs.DOF.X, new PID.K(1, 0, 0));
            put(DOFs.DOF.Y, new PID.K(1, 0, 0));
            put(DOFs.DOF.THETA, new PID.K(1, 0, 0));
        }}, new HashMap<DOFs.DOF, PathFollower.K>() {{
                put(DOFs.DOF.X, new PathFollower.K(1, 1));
                put(DOFs.DOF.Y, new PathFollower.K(1, 1));
                put(DOFs.DOF.THETA, new PathFollower.K(1, 1));
            }});


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        pathFollower.run(telemetry);

        // while (opModeIsActive()) {}
    }
}
