package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.LinkedList;
import java.util.Queue;

public class ArtifactColorQueue {

    public enum ArtifactColor {
        PURPLE,
        GREEN,
        UNKNOWN
    }

    private ColorSensor sensor1;
    private ColorSensor sensor2;

    private Servo ledServo;

    private Queue<ArtifactColor> queue = new LinkedList<>();

    private boolean lastDetected = false;

    // ===== SERVO POSITIONS =====
    private static final double PURPLE_POS = 0.8;
    private static final double GREEN_POS  = 0.2;
    private static final double OFF_POS    = 0.5;

    // Detection threshold (tune this)
    private static final int DETECT_THRESHOLD = 220;

    public ArtifactColorQueue(ColorSensor sensor1,
                              ColorSensor sensor2,
                              Servo ledServo) {

        this.sensor1 = sensor1;
        this.sensor2 = sensor2;
        this.ledServo = ledServo;

        ledServo.setPosition(OFF_POS);
    }

    public void update() {

        boolean detected = detectArtifact();

        // Rising edge detection (only add once)
        if (detected && !lastDetected) {
            ArtifactColor color = classify();
            queue.add(color);
        }

        lastDetected = detected;

        updateLED();
    }

    private boolean detectArtifact() {

        int total1 = sensor1.red() + sensor1.green() + sensor1.blue();
        int total2 = sensor2.red() + sensor2.green() + sensor2.blue();

        return total1 > DETECT_THRESHOLD || total2 > DETECT_THRESHOLD;
    }

    private ArtifactColor classify() {

        int r = sensor1.red() + sensor2.red();
        int g = sensor1.green() + sensor2.green();
        int b = sensor1.blue() + sensor2.blue();

        // Purple = high red + blue
        if (r > g && b > g) {
            return ArtifactColor.PURPLE;
        }

        // Green dominant
        if (g > r && g > b) {
            return ArtifactColor.GREEN;
        }

        return ArtifactColor.UNKNOWN;
    }

    private void updateLED() {

        if (queue.isEmpty()) {
            ledServo.setPosition(OFF_POS);
            return;
        }

        ArtifactColor first = queue.peek();

        switch (first) {
            case PURPLE:
                ledServo.setPosition(PURPLE_POS);
                break;

            case GREEN:
                ledServo.setPosition(GREEN_POS);
                break;

            case UNKNOWN:
                ledServo.setPosition(OFF_POS);
                break;
        }
    }

    public void removeFirst() {
        if (!queue.isEmpty()) {
            queue.poll();
        }
    }

    public int size() {
        return queue.size();
    }

    public ArtifactColor peek() {
        return queue.peek();
    }
}
