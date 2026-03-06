package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robots.WisdomBot;

@TeleOp(name="Wisdom Teleop", group="Competition")
public class WisdomTeleop extends LinearOpMode {



    @Override
    public void runOpMode() {

        WisdomBot robot = new WisdomBot(this,false);

        double testAngle = 0.5;          // starting angle
        double flywheelRPM = 1800;       // constant while testing
        double angleAdjustSpeed = 0.002; // how fast it changes

        robot.init();

        telemetry.addLine("Robot Ready");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()) {



            // ===== DRIVE =====
            double axial = -gamepad1.left_stick_y;
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;

            robot.drive(axial,lateral,yaw);

            // Increase angle
            if (gamepad1.dpadRightWasPressed()) {
                testAngle += angleAdjustSpeed;
            }


            if (gamepad1.dpadLeftWasPressed()) {
                testAngle -= angleAdjustSpeed;
            }


            testAngle = Range.clip(testAngle, 0.2, 0.8);




            // ===== INTAKE SYSTEM =====
            if (gamepad1.right_trigger > 0.2) {
                robot.getIntake().intake();
                robot.getTransfer().run();
            }
            else if (gamepad1.left_trigger > 0.2) {
                robot.getTransfer().run();
            }
            else {
                robot.getIntake().stop();
                robot.getTransfer().stop();
            }
            // Hold right trigger to spin flywheel
            if (gamepad1.rightBumperWasPressed()) {
                robot.getFlywheel().setVelocity(flywheelRPM);
            }
            if(gamepad1.leftBumperWasPressed()){
                robot.getFlywheel().stop();
            }



            /*
            // ===== DROPPER =====
            if (gamepad1.dpadDownWasPressed()) {
                robot.getDropper().drop();
            }

            if (gamepad1.dpadUpWasPressed()) {
                robot.getDropper().hold();
            }

             */

            // ===== ARTIFACT QUEUE =====
            robot.getColorQueue().update();

            if(gamepad1.squareWasPressed()) {
                robot.getColorQueue().removeFirst();
            }

            // ===== SMART FLYWHEEL =====

            double x =
                    robot.getRobotPosition()
                            .getX(DistanceUnit.INCH);





            // ===== TELEMETRY =====
            telemetry.addLine("==== ROBOT ====");
            /*

            telemetry.addData("X",
                    robot.getRobotPosition()
                            .getX(DistanceUnit.INCH));

             */

            telemetry.addData("Flywheel Speed",
                    robot.getFlywheel().getVelocity());

            telemetry.addData("Artifacts",
                    robot.getColorQueue().size());

            telemetry.addData("Next",
                    robot.getColorQueue().peek());

            telemetry.update();
        }
    }
}
