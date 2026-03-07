package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robots.TestRobot;
import org.firstinspires.ftc.teamcode.Support.PoseStorage;

@TeleOp(name="Wisdom Blue", group="Competition")
public class Teleop extends LinearOpMode {

    private TestRobot robot;

    // ===== DRIVE STATE MACHINE =====
    private enum DriveState {
        TAG_ALIGN,
        POSITION_LOCK,
        DRIVE
    }

    private DriveState driveState = DriveState.DRIVE;

    // ===== LOCKED POSITION =====
    double lockX = 0;
    double lockY = 0;

    // ===== GOAL POSITION =====
    double goalFieldX = -150;
    double goalFieldY = -86;

    double lastTurnError = 0;
    double lastTurnTime = 0;

    int vel = 3800;

    @Override
    public void runOpMode() {

        robot = new TestRobot(this, false);
        robot.init();

        telemetry.addLine("Robot Ready");
        telemetry.update();

        waitForStart();
        Pose2D startPose = PoseStorage.currentPose;

        if (startPose != null) {
            robot.setRobotPosition(startPose);
            telemetry.addLine("Using PoseStorage.currentPose for start pose");
        } else {

            robot.setRobotPosition(
                    new Pose2D(
                            DistanceUnit.INCH,
                            51,
                            36,
                            AngleUnit.DEGREES,
                            180));
            telemetry.addLine("PoseStorage.currentPose was null; using fallback start pose");
        }

        while (opModeIsActive()) {


            robot.updatePosition();

            handleDrive();
            handleIntakeAndTransfer();
            handleArtifactQueue();
            shooter();
            updateTelemetry();
        }
    }

    // ===== DRIVE =====
    private void handleDrive() {

        double axial = -gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double yawInput = gamepad1.right_stick_x;
        double yaw = yawInput;

        // ===== STATE SWITCH BUTTONS =====

        if(gamepad1.triangleWasPressed()){

            Pose2D pose = robot.getRobotPosition();

            lockX = pose.getX(DistanceUnit.INCH);
            lockY = pose.getY(DistanceUnit.INCH);

            robot.setLockX(lockX);
            robot.setLockY(lockY);
            robot.setLockHeading(
                    pose.getHeading(AngleUnit.DEGREES));

            driveState = DriveState.POSITION_LOCK;
        }

        if(gamepad1.left_trigger > 0.5){
            driveState = DriveState.TAG_ALIGN;
        }

        if(gamepad1.crossWasPressed()){
            driveState = DriveState.DRIVE;
        }

        // ===== STICK DETECTION =====

        boolean sticksActive =
                Math.abs(axial) > 0.01 ||
                        Math.abs(lateral) > 0.01 ||
                        Math.abs(yawInput) > 0.01;

        if(sticksActive && driveState != DriveState.TAG_ALIGN){
            driveState = DriveState.DRIVE;
        }
        else if(!sticksActive && driveState == DriveState.DRIVE){

            Pose2D pose = robot.getRobotPosition();

            lockX = pose.getX(DistanceUnit.INCH);
            lockY = pose.getY(DistanceUnit.INCH);

            robot.setLockX(lockX);
            robot.setLockY(lockY);
            robot.setLockHeading(
                    pose.getHeading(AngleUnit.DEGREES));

            driveState = DriveState.POSITION_LOCK;
        }

        // ===== STATE MACHINE =====

        switch(driveState){

            case POSITION_LOCK:

                robot.goToPositionNonBlocking(
                        lockY,
                        lockX,
                        robot.getHeading(AngleUnit.DEGREES),
                        0.7);
                break;

            case TAG_ALIGN:

                if(gamepad1.left_trigger <= 0.5 ||
                        Math.abs(yawInput) > 0.15){

                    driveState = DriveState.DRIVE;
                    break;
                }

                Pose2D pose = robot.getRobotPosition();

                double robotX = pose.getX(DistanceUnit.INCH);
                double robotY = pose.getY(DistanceUnit.INCH);

                double currentHeading =
                        robot.getHeading(AngleUnit.DEGREES);

                yaw = calculateGoalFacingYaw(
                        currentHeading,
                        robotX,
                        robotY);

                robot.drive(axial, lateral, yaw, 1);
                break;

            case DRIVE:

                robot.drive(axial, lateral, yawInput, 1);
                break;
        }
    }

    // ===== INTAKE & TRANSFER =====
    private void handleIntakeAndTransfer() {

        boolean intakeTrigger = gamepad1.right_trigger > 0.2;
        boolean transferTrigger = gamepad2.left_trigger > 0.2;

        if (intakeTrigger) {
            robot.getIntake().intake();

        }
        else{
            robot.getIntake().stop();
        }
        if(transferTrigger) {

            robot.getTransfer().run();
        } else{
            robot.getTransfer().stop();
        }

        if(gamepad2.dpad_up){
            robot.getDropper().shoot();
            telemetry.addLine("Dropper Shoot");

        }
        else if(gamepad2.dpad_down){
            robot.getDropper().drop();
            robot.getColorQueue().removeFirst();
        }
        else{
            robot.getDropper().hold();
        }
    }

    // ===== ARTIFACT QUEUE =====
    private void handleArtifactQueue() {
        robot.getColorQueue().update();

        if (gamepad1.squareWasPressed()) {
            robot.getColorQueue().removeFirst();
        }
    }

    private void shooter(){
        if(gamepad2.right_bumper){
            robot.getFlyWheel().setVelocity(vel);
        } else{
            robot.getFlyWheel().stop();
        }

        if (gamepad2.crossWasPressed()) {
            vel += 100;
        }
    }

    // ===== TELEMETRY =====
    private void updateTelemetry() {

        telemetry.addLine("==== ROBOT ====");
        telemetry.addData("Drive State", driveState);
        telemetry.addData("X", robot.getRobotPosition().getX(DistanceUnit.INCH));
        telemetry.addData("Y", robot.getRobotPosition().getY(DistanceUnit.INCH));
        telemetry.addData("Artifacts", robot.getColorQueue().size());
        telemetry.addData("Next", robot.getColorQueue().peek());
        telemetry.update();
    }

    // ===== GOAL ALIGN PID =====
    private double calculateGoalFacingYaw(
            double currentHeadingDeg,
            double robotX,
            double robotY){

        double dx = goalFieldX - robotX;
        double dy = goalFieldY - robotY;

        double targetAngle =
                Math.toDegrees(Math.atan2(dy, dx));

        double error =
                targetAngle - currentHeadingDeg;

        while(error > 180) error -= 360;
        while(error < -180) error += 360;

        double kp = 0.001;
        double kd = 0.00000000001;

        double now = getRuntime();
        double dt = now - lastTurnTime;

        if(dt <= 0) dt = 0.02;

        double derivative =
                (error - lastTurnError) / dt;

        lastTurnError = error;
        lastTurnTime = now;

        double scale =
                Range.clip(Math.abs(error)/40,0.2,0.8);

        return Range.clip(
                (kp*error + kd*derivative)*scale,
                -0.8,
                0.8);
    }
}
