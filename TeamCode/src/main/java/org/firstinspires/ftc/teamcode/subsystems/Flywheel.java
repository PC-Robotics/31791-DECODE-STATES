package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

public class Flywheel {

    private DcMotorEx motor1;
    private DcMotorEx motor2;
    private Servo angleServo;

    private double targetVelocity = 0;

    // ===== PIDF VALUES (TUNE THESE) =====
    private static final double P = 160;
    private static final double I = 0;
    private static final double D = 0;
    private static final double F = 12;

    public Flywheel(DcMotorEx m1, DcMotorEx m2) {

        motor1 = m1;
        motor2 = m2;


        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        motor1.setDirection(DcMotorSimple.Direction.REVERSE);

        motor2.setDirection(DcMotor.Direction.REVERSE);

        PIDFCoefficients pidf = new PIDFCoefficients(P, I, D, F);

        motor1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
        motor2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);

        // Default angle

    }

    // ===== VELOCITY CONTROL =====

    public void setVelocity(double velocity) {
        targetVelocity = velocity;
        motor1.setVelocity(velocity);
        motor2.setVelocity(velocity);
    }

    public void stop() {
        targetVelocity = 0;
        motor1.setVelocity(0);
        motor2.setVelocity(0);
    }

    public double getVelocity() {
        return (motor1.getVelocity() + motor2.getVelocity()) / 2.0;
    }

    public boolean atSpeed() {
        return Math.abs(getVelocity() - targetVelocity) < 75;
    }


}
