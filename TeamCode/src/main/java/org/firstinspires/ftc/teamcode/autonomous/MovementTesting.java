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
import org.firstinspires.ftc.teamcode.pathfollower2.TuningConstants;

import java.util.HashMap;

@Autonomous(name = "A")
public class MovementTesting extends LinearOpMode {
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
                .onInit(() -> telemetry.speak("Segment negative 3 point one four 1 5 9 2 6 5"))
                .addTime(5.0)
                .isFinished((Double t) -> {telemetry.addData("T", t); telemetry.speak("hi, hi, hi, hi"); telemetry.update(); return t > 8.0;})
                .buildSegment()
                .onInit(() -> telemetry.speak("Segment 2"))
                .easePolynomialTo(new HashMap<DOFs.DOF, Double>() {{
                        put(DOFs.DOF.X, 12.0);
                        put(DOFs.DOF.Y, 12.0);
                        put(DOFs.DOF.THETA, 90.0);
                    }}, 2, 5
                )
                .buildSegment()
                .onInit(() -> telemetry.speak("Hello"))
                .addTime(5)
                .buildSegment()
                .build();

        PathFollower pathFollower = new PathFollower(path, new DOFs(sensors.odometry, motors), new HashMap<DOFs.DOF, PID.K>() {{
            put(DOFs.DOF.X, new PID.K(0.2, 0.1, 0.02));
            put(DOFs.DOF.Y, new PID.K(0.2, 0.1, 0.02));
            put(DOFs.DOF.THETA, new PID.K(0.25, 0.05, 0.0025));
        }}, new HashMap<DOFs.DOF, PathFollower.K>() {{
            for (DOFs.DOF dof : DOFs.DOF.values()) put(dof, new PathFollower.K(TuningConstants.v.get(dof), 0, 1));
        }}, this::isStopRequested);

        pathFollower.run(telemetry, TuningConstants.weights);
    }
}
