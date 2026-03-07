package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robots.TestRobot;
import org.firstinspires.ftc.teamcode.Support.PoseStorage;

@Autonomous(name = "Autonomous Far Good")
public class AutoFar extends LinearOpMode {

    TestRobot robot = new TestRobot(this, false);


    public void runOpMode() throws InterruptedException
    {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        robot.init();

        waitForStart();


        if(opModeIsActive())
        {
            robot.goToPosition(-36, 0, 0, 0.5, .1);
            PoseStorage.currentPose = robot.getRobotPosition();

        }
        telemetry.addLine("Auton Finished");
        telemetry.update();
    }

}
