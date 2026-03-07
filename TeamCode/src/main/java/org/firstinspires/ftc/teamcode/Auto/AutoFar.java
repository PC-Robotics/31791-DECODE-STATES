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

@Autonomous(name = "Autonomous Far Good")
public class AutoFar extends LinearOpMode {

    TestRobot robot = new TestRobot(this, false);


    public void runOpMode() throws InterruptedException
    {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        robot.init();
        robot.setRobotPosition(new Pose2D(
                DistanceUnit.INCH,
                58.031,
                -12,
                AngleUnit.DEGREES,
                180));

        waitForStart();
        robot.setRobotPosition(new Pose2D(
                DistanceUnit.INCH,
                58.031,
                -12,
                AngleUnit.DEGREES,
                180));



        if(opModeIsActive())
        {
            robot.goToPosition(-18, 48, 143, 0.5, .1);
            robot.getFlyWheel().setVelocity(3800);
            Thread.sleep(1000);
            robot.getDropper().shoot();
            robot.getTransfer().run();
            Thread.sleep(3000);
            robot.getDropper().hold();
            robot.getTransfer().stop();
            robot.goToPosition(-24, 36, 90, .8,.4);
            robot.getIntake().intake();
            robot.getTransfer().run();
            robot.goToPosition(-48, 36, 90, .8, .4);
            robot.getIntake().stop();
            robot.getTransfer().stop();
            robot.goToPosition(-18, 48, 143, 0.5, .1);
            robot.getFlyWheel().setVelocity(3800);
            Thread.sleep(1000);
            robot.getDropper().shoot();
            robot.getTransfer().run();
            robot.goToPosition(-36, 48, 0, 0.5, 0.3);
            PoseStorage.currentPose = robot.getRobotPosition();

        }
        telemetry.addLine("Auton Finished");
        telemetry.update();
    }

}
