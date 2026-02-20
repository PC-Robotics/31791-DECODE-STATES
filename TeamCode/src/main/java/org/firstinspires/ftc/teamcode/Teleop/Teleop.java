package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.DriveBase;
import org.firstinspires.ftc.teamcode.Robots.WisdomBot;


@TeleOp(name="Testing Teleop", group="Test Modes")
public class Teleop extends LinearOpMode
{
    private WisdomBot robot = new WisdomBot(this, false);

    public void runOpMode()
    {
        robot.init();

        waitForStart();

        while(opModeIsActive())
        {
            double axial = gamepad1.left_stick_y;   // Forward on left stick yields negative val
            double lateral = -gamepad1.left_stick_x;
            double yaw = -gamepad1.right_stick_x;


            robot.drive(axial,lateral,yaw);

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
            if (gamepad1.dpadDownWasPressed()) {
                robot.getDropper().drop();
            }

            if (gamepad1.dpadUpWasPressed()) {
                robot.getDropper().hold();
            }


            telemetry.update();
        }
    }

}
