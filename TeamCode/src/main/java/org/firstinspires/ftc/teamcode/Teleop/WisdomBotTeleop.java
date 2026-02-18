package org.firstinspires.ftc.teamcode.Teleop;

import static android.graphics.Color.BLUE;

import static org.firstinspires.ftc.teamcode.Robots.WisdomBot.RGBColor.AZURE;
import static org.firstinspires.ftc.teamcode.Robots.WisdomBot.RGBColor.GREEN;
import static org.firstinspires.ftc.teamcode.Robots.WisdomBot.RGBColor.OFF;
import static org.firstinspires.ftc.teamcode.Robots.WisdomBot.RGBColor.RED;
import static org.firstinspires.ftc.teamcode.Robots.WisdomBot.RGBColor.VIOLET;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robots.WisdomBot;
import org.firstinspires.ftc.teamcode.subsystems.ArtifactQueueManager.ArtifactType;

@TeleOp(name = "WisdomBot TeleOp", group = "TeleOp")
public class WisdomBotTeleop extends LinearOpMode {

    private WisdomBot robot;

    // Goal position for auto-alignment
    private double alignX = -80; // example X position in inches
    private double alignY = -90; // example Y position in inches

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize robot
        robot = new WisdomBot(this, true);
        robot.init();

        telemetry.addLine("Ready to start!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // --- DRIVE CONTROL ---
            double drive = -gamepad1.left_stick_y;   // forward/backward
            double strafe = gamepad1.left_stick_x;   // left/right
            double turn = gamepad1.right_stick_x;    // rotation

            // If left trigger is held, override drive to align to goal
            if (gamepad1.left_trigger > 0.1) {
                Pose2D pose = robot.getRobotPosition();
                double robotX = pose.getX(DistanceUnit.INCH);
                double robotY = pose.getY(DistanceUnit.INCH);
                double heading = pose.getHeading(AngleUnit.DEGREES);


                // Simple vector to target
                double deltaX = alignX - robotX;
                double deltaY = alignY - robotY;

                // Normalize and scale for driving
                double magnitude = Math.hypot(deltaX, deltaY);
                if (magnitude > 1) {
                    deltaX /= magnitude;
                    deltaY /= magnitude;
                }

                drive = deltaY;   // forward/back toward goal
                strafe = deltaX;  // strafe toward goal
                turn = 0;         // can be enhanced to rotate toward a heading
            }

            robot.driveWithHold(drive, strafe, turn);

            // --- FLYWHEEL CONTROL ---
            boolean shootLeft = gamepad1.left_bumper;   // L1
            boolean shootRight = gamepad1.right_bumper; // R1

            if (shootLeft || shootRight) {
                robot.setFlywheelVelocity(1200); // example RPM
            } else {
                robot.stopFlywheel();
            }

            // --- RECYCLER CONTROL ---
            boolean intakeCmd = gamepad1.right_trigger > 0.1; // R2
            boolean boostCmd = gamepad1.triangle; // Triangle
            boolean dropCmd = gamepad1.cross;  // Cross

            robot.updateRecycler(intakeCmd, boostCmd, dropCmd);

            // --- AUTOMATIC LED UPDATE ---
            ArtifactType first = robot.getQueueManager().peek();
            if (first != null) {
                switch (first) {
                    case GREEN:   // treat RED as GREEN now
                        robot.setRGBColor(GREEN); // green on
                        break;
                    case PURPLE:
                        robot.setRGBColor(VIOLET); // purple
                        break;
                    case UNKNOWN:
                        robot.setRGBColor(AZURE);

                    default:
                        robot.setRGBColor(OFF); // off
                }
            } else {
                robot.setRGBColor(OFF); // off if queue empty
            }

            // --- TELEMETRY ---
            telemetry.addData("First Artifact", first);
            telemetry.addData("Queue Size", robot.getQueueManager().size());
            telemetry.addData("Drive (Y,X,Turn)", "%.2f, %.2f, %.2f", drive, strafe, turn);
            telemetry.addData("Shooter", shootLeft || shootRight ? "ON" : "OFF");
            telemetry.update();
        }
    }
}
