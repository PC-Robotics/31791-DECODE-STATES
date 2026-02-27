package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.Robots.TestRobot;
import org.firstinspires.ftc.teamcode.Robots.WisdomBot;
import org.firstinspires.ftc.teamcode.subsystems.ArtifactColorQueue;


@TeleOp(name="Testing Teleop", group="Test Modes")
public class Teleop extends LinearOpMode {
    TestRobot robot = new TestRobot(this, false);
    boolean autoShotActive = false;
    double autoShotStartTime = 0;
    boolean lastA = false;


    public void runOpMode() {
        robot.init();


        waitForStart();

        while (opModeIsActive()) {
            double axial = gamepad1.left_stick_y;   // Forward on left stick yields negative val
            double lateral = -gamepad1.left_stick_x;
            double yaw = -gamepad1.right_stick_x;


            robot.drive(axial, lateral, yaw);

            if (gamepad1.right_trigger > 0.2) {
                robot.getIntake().intake();
                robot.getTransfer().run();
            } else if (gamepad1.left_trigger > 0.2) {
                robot.getTransfer().run();
            } else {
                robot.getIntake().stop();
                robot.getTransfer().stop();
            }
            robot.getColorQueue().update();

            if (gamepad1.cross && !lastA) {
                robot.getColorQueue().removeFirst();
            }
            lastA = gamepad1.cross;

            // Queue info
            int size = robot.getColorQueue().size();
            telemetry.addData("Queue Size", size);

            // Show first three items
            ArtifactColorQueue.ArtifactColor[] arr =
                    robot.getColorQueue().getQueue().toArray(new ArtifactColorQueue.ArtifactColor[0]);

            telemetry.addData("Item 1", arr.length > 0 ? arr[0] : "EMPTY");
            telemetry.addData("Item 2", arr.length > 1 ? arr[1] : "EMPTY");
            telemetry.addData("Item 3", arr.length > 2 ? arr[2] : "EMPTY");

            // Show last RGB reading
            telemetry.addData("Red", robot.getColorQueue().getLastRed());
            telemetry.addData("Green", robot.getColorQueue().getLastGreen());
            telemetry.addData("Blue", robot.getColorQueue().getLastBlue());

            telemetry.update();
        }


        telemetry.update();
    }
}


