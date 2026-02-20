package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.DriveBase;
import org.firstinspires.ftc.teamcode.Robots.WisdomBot;


@TeleOp(name="Color Test", group="Test Modes")
public class ColorTest extends LinearOpMode
{
    private WisdomBot bot = new WisdomBot(this, false);

    boolean lastA = false;

    public void runOpMode()
    {
        bot.init();

        waitForStart();

        while(opModeIsActive())
        {
            bot.getColorQueue().update();


            if (gamepad1.crossWasPressed() && !lastA) {
                bot.getColorQueue().removeFirst();
            }
            lastA = gamepad1.crossWasPressed();

            telemetry.addData("Queue Size",
                    bot.getColorQueue().size());

            telemetry.addData("Next Color",
                    bot.getColorQueue().peek());

            telemetry.update();



            telemetry.update();
        }
    }

}
