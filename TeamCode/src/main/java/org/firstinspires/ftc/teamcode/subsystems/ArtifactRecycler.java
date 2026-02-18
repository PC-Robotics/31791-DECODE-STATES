package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArtifactRecycler {

    public enum State {
        IDLE,
        INTAKING,
        STAGED,
        BOOSTING,
        DROPPING
    }

    private State state = State.IDLE;

    private DcMotorEx intake;
    private DcMotorEx conveyor;
    private DcMotorEx boostLeft;
    private DcMotorEx boostRight;

    private Servo dropServo;

    private ColorSensor chamberSensor;

    private DualFlywheelShooter shooter;
    private ArtifactQueueManager queueManager;

    private ElapsedTime actionTimer = new ElapsedTime();

    private static final double INTAKE_POWER = 1.0;
    private static final double CONVEYOR_POWER = 1.0;
    private static final double BOOST_POWER = 1.0;

    private static final double DROP_OPEN = 0.75;
    private static final double DROP_CLOSED = 0.2;

    private static final double BOOST_TIME = 0.18;
    private static final double DROP_TIME = 0.25;

    private double minShootVelocity;

    public ArtifactRecycler(
            DcMotorEx intake,
            DcMotorEx conveyor,
            DcMotorEx boostLeft,
            DcMotorEx boostRight,
            Servo dropServo,
            ColorSensor chamberSensor,
            DualFlywheelShooter shooter,
            ArtifactQueueManager queueManager,
            double minShootVelocity
    ) {
        this.intake = intake;
        this.conveyor = conveyor;
        this.boostLeft = boostLeft;
        this.boostRight = boostRight;
        this.dropServo = dropServo;
        this.chamberSensor = chamberSensor;
        this.shooter = shooter;
        this.queueManager = queueManager;
        this.minShootVelocity = minShootVelocity;

        boostRight.setDirection(DcMotorEx.Direction.REVERSE);
        dropServo.setPosition(DROP_CLOSED);
    }

    public void update(boolean intakeCmd,
                       boolean shootCmd,
                       boolean dropCmd) {

        boolean chamberDetected = detect(chamberSensor);
        boolean shooterReady = shooter.atSpeed(minShootVelocity);

        switch (state) {

            case IDLE:
                stopAll();
                if (intakeCmd) {
                    state = State.INTAKING;
                }
                break;

            case INTAKING:
                intake.setPower(INTAKE_POWER);
                conveyor.setPower(CONVEYOR_POWER);

                if (chamberDetected) {
                    intake.setPower(0);
                    conveyor.setPower(0);
                    state = State.STAGED;
                }
                break;

            case STAGED:
                stopAll();

                if (shootCmd && shooterReady && queueManager.size() > 0) {
                    boostLeft.setPower(BOOST_POWER);
                    boostRight.setPower(BOOST_POWER);
                    actionTimer.reset();
                    state = State.BOOSTING;
                }

                if (dropCmd && queueManager.size() > 0) {
                    dropServo.setPosition(DROP_OPEN);
                    actionTimer.reset();
                    state = State.DROPPING;
                }

                if (intakeCmd && !chamberDetected) {
                    state = State.INTAKING;
                }

                break;

            case BOOSTING:
                if (actionTimer.seconds() > BOOST_TIME) {
                    boostLeft.setPower(0);
                    boostRight.setPower(0);
                    queueManager.removeFirst();  // 🔥 REMOVE FROM LIST
                    state = State.IDLE;
                }
                break;

            case DROPPING:
                if (actionTimer.seconds() > DROP_TIME) {
                    dropServo.setPosition(DROP_CLOSED);
                    queueManager.removeFirst();  // 🔥 REMOVE FROM LIST
                    state = State.IDLE;
                }
                break;
        }
    }

    private void stopAll() {
        intake.setPower(0);
        conveyor.setPower(0);
        boostLeft.setPower(0);
        boostRight.setPower(0);
    }

    private boolean detect(ColorSensor sensor) {
        int total = sensor.red() + sensor.green() + sensor.blue();
        return total > 220;
    }

    public State getState() {
        return state;
    }
}
