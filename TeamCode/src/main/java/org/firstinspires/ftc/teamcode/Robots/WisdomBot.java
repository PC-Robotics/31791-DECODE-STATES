package org.firstinspires.ftc.teamcode.Robots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Dropper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.subsystems.ArtifactColorQueue;


public class WisdomBot extends DriveBase {

    private Intake intake;
    private Dropper dropper;
    private ArtifactColorQueue colorQueue;


    private Transfer transfer;

    public WisdomBot(LinearOpMode mode, boolean isFC) {
        super(mode, isFC);
    }

    public void init() {

        DcMotorEx intakeMotor =
                myOpMode.hardwareMap.get(DcMotorEx.class, "intake");

        DcMotorEx transferMotor =
                myOpMode.hardwareMap.get(DcMotorEx.class, "transfer");

        Servo dropServo =
                myOpMode.hardwareMap.get(Servo.class, "drop_servo");

        ColorSensor sensor1 =
                myOpMode.hardwareMap.get(ColorSensor.class, "color1");

        ColorSensor sensor2 =
                myOpMode.hardwareMap.get(ColorSensor.class, "color2");

        Servo ledServo =
                myOpMode.hardwareMap.get(Servo.class, "led_servo");

        colorQueue = new ArtifactColorQueue(sensor1, sensor2, ledServo);


        dropper = new Dropper(dropServo);

        intake = new Intake(intakeMotor);
        transfer = new Transfer(transferMotor);

        super.init();
    }

    public Intake getIntake() {
        return intake;
    }

    public Dropper getDropper() {
        return dropper;
    }


    public Transfer getTransfer() {
        return transfer;
    }

    public ArtifactColorQueue getColorQueue() {
        return colorQueue;
    }

}
