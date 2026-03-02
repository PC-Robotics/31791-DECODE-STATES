package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;



import org.firstinspires.ftc.teamcode.Robots.TestRobot;


@TeleOp(name="Testing Teleop", group="Competition")
public class Teleop extends LinearOpMode {

    private TestRobot robot;

    @Override
    public void runOpMode() {
        robot = new TestRobot(this, false);
        robot.init();

        telemetry.addLine("Robot Ready");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            handleDrive();

            handleIntakeAndTransfer();

            handleArtifactQueue();

            updateTelemetry();
        }
    }

    // ===== DRIVE =====
    private void handleDrive() {
        double axial = -gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double yaw = gamepad1.right_stick_x;
        robot.drive(axial, lateral, yaw);
    }


    // ===== INTAKE & TRANSFER =====
    private void handleIntakeAndTransfer() {
        boolean intakeTrigger = gamepad1.right_trigger > 0.2;
        boolean transferTrigger = gamepad1.left_trigger > 0.2;

        if (intakeTrigger) {
            robot.getIntake().intake();
            robot.getTransfer().run();
        } else if (transferTrigger) {
            robot.getIntake().stop();
            robot.getTransfer().run();
        } else {
            robot.getIntake().stop();
            robot.getTransfer().stop();
        }
    }


    // ===== ARTIFACT QUEUE =====
    private void handleArtifactQueue() {
        robot.getColorQueue().update();
        if (gamepad1.squareWasPressed()) robot.getColorQueue().removeFirst();
    }

    // ===== TELEMETRY =====
    private void updateTelemetry() {
        telemetry.addLine("==== ROBOT ====");
        telemetry.addData("Artifacts", robot.getColorQueue().size());
        telemetry.addData("Next", robot.getColorQueue().peek());
        telemetry.update();
    }
}
