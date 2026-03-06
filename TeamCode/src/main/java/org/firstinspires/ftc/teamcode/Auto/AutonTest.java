package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robots.TestRobot;
import org.firstinspires.ftc.teamcode.Support.PoseStorage;

@Autonomous(name = "Autonomous Testing")
public class AutonTest extends LinearOpMode {

    TestRobot robot = new TestRobot(this, false);

    public void runOpMode() throws InterruptedException
    {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        robot.init();

        waitForStart();

        if(opModeIsActive())
        {
            robot.getFlyWheel().setVelocity(2000);
            Thread.sleep(1000);
            robot.getDropper().shoot();
            robot.getTransfer().run();
            Thread.sleep(1000);
            robot.getFlyWheel().stop();
            robot.getDropper().hold();

            robot.getIntake().intake();
            robot.getTransfer().run();
            robot.goToPosition(-24, -46, 90, .9, 0.1 );
            robot.goToPosition(-40, -46, 90, 0.9, 0.1);
            Thread.sleep(1000);
            robot.getTransfer().stop();
            robot.goToPosition(-10, 55, -29, .9, .11);
            robot.getFlyWheel().setVelocity(2000);
            Thread.sleep(1000);
            robot.getDropper().shoot();
            robot.getTransfer().run();
            Thread.sleep(1000);
            robot.goToPosition(-40, 55, 0, 0.9, .11);
            PoseStorage.currentPose = robot.getRobotPosition();






            //robot.turnTo(90, .5, 5);

            //robot.strafe(24, .5, 5);

            //robot.forward(24, .2, 100);

            //robot.turnTo(180, 1, 100);

            //robot.strafe(-24,1,100);


        }
        telemetry.addLine("Auton Finished");
        telemetry.update();
    }
}
