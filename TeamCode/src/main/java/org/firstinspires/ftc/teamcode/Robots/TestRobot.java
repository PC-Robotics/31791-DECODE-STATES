package org.firstinspires.ftc.teamcode.Robots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Support.Alliance;
import org.firstinspires.ftc.teamcode.subsystems.Booster;
import org.firstinspires.ftc.teamcode.subsystems.Dropper;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.subsystems.ArtifactColorQueue;


public class TestRobot extends DriveBasePID {

    private Intake intake;
    private Flywheel flywheel;
    private Dropper dropper;
    private ArtifactColorQueue colorQueue;
    private Booster booster;

    public static Alliance alliance = Alliance.BLUE;
    public static Pose2D farScorePose = new Pose2D(DistanceUnit.INCH, 72 - 12, -12, AngleUnit.RADIANS, 0);
    public static Pose2D goalPose = new Pose2D(DistanceUnit.INCH, -72, -72, AngleUnit.RADIANS, 0);



    private Transfer transfer;

    public TestRobot(LinearOpMode mode, boolean isFC) {
        super(mode, isFC);

        double dx = goalPose.getX(DistanceUnit.INCH) - farScorePose.getX(DistanceUnit.INCH);
        double dy = goalPose.getY(DistanceUnit.INCH) - farScorePose.getY(DistanceUnit.INCH);

        double angle = Math.atan2(dx, -dy);

        farScorePose = new Pose2D(
                DistanceUnit.INCH,
                farScorePose.getX(DistanceUnit.INCH),
                farScorePose.getY(DistanceUnit.INCH),
                AngleUnit.RADIANS,
                angle
        );
    }

    public void setAlliance(Alliance alliance) {
        if (TestRobot.alliance != alliance) {
            farScorePose = mirror(farScorePose);
            goalPose = mirror(goalPose);
        }

        TestRobot.alliance = alliance;
    }

    public void init() {


        DcMotorEx intakeMotor = myOpMode.hardwareMap.get(DcMotorEx.class, "intake");

        DcMotorEx transferMotor =
                myOpMode.hardwareMap.get(DcMotorEx.class, "transfer");

        ColorSensor sensor1 =
                myOpMode.hardwareMap.get(ColorSensor.class, "color1");

        ColorSensor sensor2 =
                myOpMode.hardwareMap.get(ColorSensor.class, "color2");

        Servo ledServo =
                myOpMode.hardwareMap.get(Servo.class, "led1");
        Servo ledServo2 =
                myOpMode.hardwareMap.get(Servo.class, "led2");
        Servo ledServo3 =
                myOpMode.hardwareMap.get(Servo.class, "led3");

        Servo dropServo =
                myOpMode.hardwareMap.get(Servo.class, "drop_servo");

        DcMotorEx fly1 = myOpMode.hardwareMap.get(DcMotorEx.class, "flywheel1");

        DcMotorEx fly2 = myOpMode.hardwareMap.get(DcMotorEx.class, "flywheel2");

        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        transferMotor.setDirection(DcMotorSimple.Direction.REVERSE);







        colorQueue = new ArtifactColorQueue(sensor1, sensor2, ledServo, ledServo2, ledServo3);




        intake = new Intake(intakeMotor);

        transfer = new Transfer(transferMotor);
        dropper = new Dropper(dropServo);

        flywheel = new Flywheel(fly1, fly2);



                super.init();
    }


    public Intake getIntake() {
        return intake;
    }

    public Flywheel getFlyWheel(){
        return flywheel;
    }


    public Transfer getTransfer() {
        return transfer;
    }

    public ArtifactColorQueue getColorQueue() {
        return colorQueue;
    }

    public Dropper getDropper() {
        return dropper;
    }



}
