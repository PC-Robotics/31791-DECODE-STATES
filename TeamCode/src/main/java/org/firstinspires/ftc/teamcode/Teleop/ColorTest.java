package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.DriveBase;
import org.firstinspires.ftc.teamcode.Robots.WisdomBot;
import org.firstinspires.ftc.teamcode.subsystems.ArtifactColorQueue;


@TeleOp(name="Color Test", group="Test Modes")
public class ColorTest extends LinearOpMode
{
    private WisdomBot bot = new WisdomBot(this, false);

    boolean lastA = false;

    public void runOpMode()
    {
        bot.init();

        waitForStart();

        while (opModeIsActive())
        {
            bot.getColorQueue().update();

            if (gamepad1.cross && !lastA) {
                bot.getColorQueue().removeFirst();
            }
            lastA = gamepad1.cross;

            // Queue info
            int size = bot.getColorQueue().size();
            telemetry.addData("Queue Size", size);

            // Show first three items
            ArtifactColorQueue.ArtifactColor[] arr =
                    bot.getColorQueue().getQueue().toArray(new ArtifactColorQueue.ArtifactColor[0]);

            telemetry.addData("Item 1", arr.length > 0 ? arr[0] : "EMPTY");
            telemetry.addData("Item 2", arr.length > 1 ? arr[1] : "EMPTY");
            telemetry.addData("Item 3", arr.length > 2 ? arr[2] : "EMPTY");

            // Show last RGB reading
            telemetry.addData("Red", bot.getColorQueue().getLastRed());
            telemetry.addData("Green", bot.getColorQueue().getLastGreen());
            telemetry.addData("Blue", bot.getColorQueue().getLastBlue());

            telemetry.update();
        }

    }

}
