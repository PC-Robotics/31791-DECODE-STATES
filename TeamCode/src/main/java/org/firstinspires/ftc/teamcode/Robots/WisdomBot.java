package org.firstinspires.ftc.teamcode.Robots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Booster;
import org.firstinspires.ftc.teamcode.subsystems.Dropper;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.subsystems.ArtifactColorQueue;


public class WisdomBot extends DriveBasePID {

    private Intake intake;
    private Flywheel flywheel;
    private Dropper dropper;
    private ArtifactColorQueue colorQueue;
    private Booster booster;



    private Transfer transfer;

    public WisdomBot(LinearOpMode mode, boolean isFC) {
        super(mode, isFC);
    }

    public void init() {


        DcMotorEx intakeMotor = myOpMode.hardwareMap.get(DcMotorEx.class, "intake");

        DcMotorEx transferMotor =
                myOpMode.hardwareMap.get(DcMotorEx.class, "transfer");

        Servo dropServo = myOpMode.hardwareMap.get(Servo.class, "drop_servo");
        DcMotorEx fly1 = myOpMode.hardwareMap.get(DcMotorEx.class, "flywheel1");
        DcMotorEx fly2 = myOpMode.hardwareMap.get(DcMotorEx.class, "flywheel2");





        ColorSensor sensor1 =
                myOpMode.hardwareMap.get(ColorSensor.class, "color1");

        ColorSensor sensor2 =
                myOpMode.hardwareMap.get(ColorSensor.class, "color2");

        Servo ledServo =
                myOpMode.hardwareMap.get(Servo.class, "led_servo");
        Servo ledServo2 =
                myOpMode.hardwareMap.get(Servo.class, "led_servo2");
        Servo ledServo3 =
                myOpMode.hardwareMap.get(Servo.class, "led_servo3");











        colorQueue = new ArtifactColorQueue(sensor1, sensor2, ledServo, ledServo2, ledServo3);


        dropper = new Dropper(dropServo);

        intake = new Intake(intakeMotor);
        transfer = new Transfer(transferMotor);
        Servo shooterAngle =
                myOpMode.hardwareMap.get(Servo.class, "shooterAngle");

        flywheel = new Flywheel(fly1, fly2);



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
    public Flywheel getFlywheel(){
        return flywheel;
    }




}
