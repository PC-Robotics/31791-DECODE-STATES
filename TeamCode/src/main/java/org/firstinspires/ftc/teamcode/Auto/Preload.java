package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robots.TestRobot;
import org.firstinspires.ftc.teamcode.Support.PoseStorage;

@Autonomous(name = "Red Preload")
public class Preload extends LinearOpMode {

    TestRobot robot = new TestRobot(this, false);

    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        robot.init();

        waitForStart();
        robot.setRobotPosition(new Pose2D(
                DistanceUnit.INCH,
                58.031,
                -12,
                AngleUnit.DEGREES,
                180));


        if (opModeIsActive()) {
            robot.goToPosition(-12, 53, 160, 0.5, .1);


            robot.getFlyWheel().setVelocity(4000);
            Thread.sleep(2000);
            robot.getDropper().shoot();
            robot.getTransfer().run();
            Thread.sleep(5000);
            robot.getDropper().hold();
            robot.getTransfer().stop();
            robot.goToPosition(36, 51, 180, .5, .6);
            PoseStorage.currentPose = robot.getRobotPosition();
        }
        telemetry.addLine("Auton Finished");
        telemetry.update();
    }
}
