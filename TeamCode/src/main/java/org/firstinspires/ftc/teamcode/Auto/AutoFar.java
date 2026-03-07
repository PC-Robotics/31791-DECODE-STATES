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


    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        robot.init();
        robot.setRobotPosition(new Pose2D(
                DistanceUnit.INCH,
                58.031,
                -15,
                AngleUnit.DEGREES,
                180));

        waitForStart();
        robot.setRobotPosition(new Pose2D(
                DistanceUnit.INCH,
                58.031,
                -12,
                AngleUnit.DEGREES,
                180));


        if (opModeIsActive()) {
            robot.goToPosition(-12, 53, 160, 0.5, .6);
            /*
            robot.getFlyWheel().setVelocity(3800);
            Thread.sleep(1000);
            robot.getDropper().shoot();
            robot.getTransfer().run();
            Thread.sleep(3000);
            robot.getDropper().hold();
            robot.getTransfer().stop();

             */
            robot.goToPosition(-12, 46, 90, .5, .6);

            robot.getIntake().intake();
            robot.getTransfer().run();


            robot.goToPosition(16, 46, 90, .2, .6);

            robot.getIntake().stop();
            robot.getTransfer().stop();
            robot.getDropper().drop();
            Thread.sleep(300);
            robot.getDropper().hold();


            robot.goToPosition(-12, 53, 160, 0.5, .1);
            /*
            robot.getFlyWheel().setVelocity(3800);
            Thread.sleep(1000);
            robot.getDropper().shoot();
            robot.getTransfer().run();

             */
            robot.goToPosition(20, 50, 180, 0.5, 0.3);
            PoseStorage.currentPose = robot.getRobotPosition();

        }
        telemetry.addLine("Auton Finished");
        telemetry.update();
    }

}
