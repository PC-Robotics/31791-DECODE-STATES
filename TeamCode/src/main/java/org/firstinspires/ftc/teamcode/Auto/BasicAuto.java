package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robots.TestRobot;
import org.firstinspires.ftc.teamcode.Support.Alliance;
import org.firstinspires.ftc.teamcode.Support.PoseStorage;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;

// towards pits pos x, towards red pos y, towards red pos angle
@Autonomous(name = "Basic Auto")
public class BasicAuto extends LinearOpMode {

    TestRobot robot = new TestRobot(this, false);
    public static final Pose2D startPose = new Pose2D(DistanceUnit.INCH, 72 - 9, -12, AngleUnit.DEGREES, 0);

    public void runOpMode() throws InterruptedException
    {
        robot.init();
        robot.setRobotPosition(startPose);

        handleAllianceSelection();

        waitForStart();

        if(opModeIsActive())
        {
            robot.getFlyWheel().setVelocity(Flywheel.shootingVelocity);
            robot.goToPosition(TestRobot.farScorePose, 0.5, 0.2);
            Thread.sleep(1000);
            robot.getDropper().shoot();
            robot.getTransfer().run();
            Thread.sleep(1000);
            robot.getFlyWheel().stop();
            robot.getDropper().hold();
            PoseStorage.currentPose = robot.getRobotPosition();
        }
        telemetry.addLine("Auton Finished");
        telemetry.update();
    }


    private void handleAllianceSelection() {
        Alliance a = Alliance.BLUE;
        gamepad1.rumble(500); // reminder to set alliance team
        while (opModeInInit()) {
            if (gamepad1.leftBumperWasPressed()) {
                a = Alliance.BLUE;
            } else if (gamepad1.rightBumperWasPressed()) {
                a = Alliance.RED;
            }


            telemetry.addData("","Select Alliance");
            telemetry.addData("","Left Bumper: Blue Alliance");
            telemetry.addData("","Right Bumper: Red Alliance");
            telemetry.addData(""," ------------------------- ");
            telemetry.addData("","Current Alliance: " + a.toString());
            telemetry.update();
        }
        robot.setAlliance(a);
    }
}
