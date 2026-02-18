package org.firstinspires.ftc.teamcode.Robots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Robots.DriveBasePID;
import org.firstinspires.ftc.teamcode.subsystems.DualFlywheelShooter;
import org.firstinspires.ftc.teamcode.subsystems.ArtifactRecycler;
import org.firstinspires.ftc.teamcode.subsystems.ArtifactQueueManager;
import org.firstinspires.ftc.teamcode.Support.ConstantsPID;

public class WisdomBot extends DriveBasePID {

    private DualFlywheelShooter shooter;
    private static Servo rgbIndicator;
    private ArtifactRecycler recycler;
    private ArtifactQueueManager queueManager;

    public WisdomBot(LinearOpMode opMode, boolean isFC) {
        super(opMode, isFC);
    }

    public void init() {

        // Flywheel motors
        DcMotorEx leftFly = myOpMode.hardwareMap.get(DcMotorEx.class, "flywheel_left");
        DcMotorEx rightFly = myOpMode.hardwareMap.get(DcMotorEx.class, "flywheel_right");

        // Recycler motors
        DcMotorEx intake = myOpMode.hardwareMap.get(DcMotorEx.class, "intake");
        DcMotorEx conveyor = myOpMode.hardwareMap.get(DcMotorEx.class, "transfer"); // renamed for clarity
        DcMotorEx boostLeft = myOpMode.hardwareMap.get(DcMotorEx.class, "boost_left");
        DcMotorEx boostRight = myOpMode.hardwareMap.get(DcMotorEx.class, "boost_right");

        // Recycler servo
        Servo dropServo = myOpMode.hardwareMap.get(Servo.class, "gate_servo");

        // Color sensor
        ColorSensor chamberSensor = myOpMode.hardwareMap.get(ColorSensor.class, "upper_color");

        // Initialize shooter
        shooter = new DualFlywheelShooter(leftFly, rightFly);

        // Initialize queue manager for green/purple detection
        queueManager = new ArtifactQueueManager(chamberSensor, null, null, null); // If you have LEDs, put them here

        // Initialize recycler
        recycler = new ArtifactRecycler(
                intake,
                conveyor,
                boostLeft,
                boostRight,
                dropServo,
                chamberSensor,
                shooter,
                queueManager,
                ConstantsPID.LAUNCHER_MIN_VELOCITY
        );

        super.init();
    }

    public void setFlywheelVelocity(double velocity) {
        shooter.setVelocity(velocity);
    }

    public void stopFlywheel() {
        shooter.stop();
    }

    public enum RGBColor {
        OFF(0.0),       // 500µs
        RED(0.33),     // 1100µs
        YELLOW(0.388),  // 1300µs
        SAGE(0.444),    // 1400µs
        GREEN(0.500),   // 1500µs
        AZURE(0.555),   // 1600µs
        BLUE(0.611),    // 1700µs
        INDIGO(0.666),  // 1800µs
        VIOLET(0.722),  // 1900µs
        WHITE(1.0);     // 2500µs

        private final double position;

        RGBColor(double position) {
            this. position = position;
        }

        public double getPosition() {
            return position;
        }
    }

        public void setRGBColor(RGBColor color) {
        if(rgbIndicator != null) {
            rgbIndicator.setPosition(color.getPosition());
        }

    }

    public void updateRecycler(boolean intakeCommand, boolean shootCommand, boolean dropCommand) {
        recycler.update(intakeCommand, shootCommand, dropCommand);
    }

    public ArtifactQueueManager getQueueManager() {
        return queueManager;
    }
}
