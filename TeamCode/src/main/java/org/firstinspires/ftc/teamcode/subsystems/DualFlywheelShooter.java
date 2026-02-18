package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class DualFlywheelShooter {

    private DcMotorEx left;
    private DcMotorEx right;

    private double targetVelocity = 0;

    public DualFlywheelShooter(DcMotorEx left, DcMotorEx right) {
        this.left = left;
        this.right = right;

        left.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        left.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        right.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        right.setDirection(DcMotorEx.Direction.REVERSE);

        // PIDF tuned for 6000RPM motors
        left.setVelocityPIDFCoefficients(15, 0, 2, 12);
        right.setVelocityPIDFCoefficients(15, 0, 2, 12);
    }

    public void setVelocity(double velocity) {
        targetVelocity = velocity;
        left.setVelocity(velocity);
        right.setVelocity(velocity);
    }

    public void stop() {
        left.setVelocity(0);
        right.setVelocity(0);
        targetVelocity = 0;
    }

    public boolean atSpeed(double minVelocity) {
        return left.getVelocity() >= minVelocity &&
                right.getVelocity() >= minVelocity;
    }

    public double getAverageVelocity() {
        return (left.getVelocity() + right.getVelocity()) / 2.0;
    }
}
