package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.DriveBase;
import org.firstinspires.ftc.teamcode.Robots.WisdomBot;


@TeleOp(name="Transfer/Intake Test", group="Test Modes")
public class TransferSystemTest extends LinearOpMode
{
    private WisdomBot robot = new WisdomBot(this, false);

    public void runOpMode()
    {
        robot.init();

        waitForStart();

        while(opModeIsActive())
        {


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
