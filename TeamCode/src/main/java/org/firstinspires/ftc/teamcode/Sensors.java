package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.teamcode.pinpoint.GoBildaPinpointDriver;

public class Sensors {
    GoBildaPinpointDriver odometry;

    public void init() {
        // Pinpoint
        {
            odometry = hardwareMap.get(GoBildaPinpointDriver.class, "odo");

            /*
                Set the odometry pod positions relative to the point that the odometry computer tracks around.
                The X pod offset refers to how far sideways from the tracking point the X (forward) odometry pod is. Left of the center is a positive number, right of center is a negative number.
                The Y pod offset refers to how far forwards from the tracking point the Y (strafe) odometry pod is. forward of center is a positive number, backwards is a negative number.
            */

            // TODO: Make more accurate, these measurements were carelessly eyeballed
            odometry.setOffsets(-5 * 2.54 /* inch to cm constant */, 1.75 * 2.54);

            odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);

            // TODO: I have no idea if these are accurate, these need to be tested
            odometry.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

            odometry.recalibrateIMU();

            odometry.resetPosAndIMU();

            telemetry.addLine("Pinpoint initalized");
            telemetry.update();
        }

        telemetry.addLine("All sensors initalized");
        telemetry.update();
    }
}
