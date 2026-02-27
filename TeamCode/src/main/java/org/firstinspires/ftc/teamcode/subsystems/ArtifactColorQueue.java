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
    private Servo ledServo2;
    private Servo ledServo3;

    private Queue<ArtifactColor> queue = new LinkedList<>();

    // ===== SERVO POSITIONS =====
    private static final double PURPLE_POS = 0.722;
    private static final double GREEN_POS  = 0.5;
    private static final double OFF_POS    = 0.0;

    // Detection threshold (tune this)
    private static final int DETECT_THRESHOLD = 180; // lowered to detect artifacts closely spaced

    // Last RGB readings for telemetry
    private int lastRed = 0;
    private int lastGreen = 0;
    private int lastBlue = 0;

    // Cooldown to prevent duplicate detection
    private long lastAddTime = 0;
    private boolean lastDetected;

    private static final int MAX_ARTIFACTS = 3;

    private static final long COOLDOWN_MS = 600;

    public ArtifactColorQueue(ColorSensor sensor1,
                              ColorSensor sensor2,
                              Servo ledServo,
                              Servo ledServo2,
                              Servo ledServo3) {

        this.sensor1 = sensor1;
        this.sensor2 = sensor2;
        this.ledServo = ledServo;
        this.ledServo2 = ledServo2;
        this.ledServo3 = ledServo3;

        // Turn all LEDs off initially
        ledServo.setPosition(OFF_POS);
        ledServo2.setPosition(OFF_POS);
        ledServo3.setPosition(OFF_POS);
    }

    public void update() {

        // If already full, stop scanning and just update LED
        if(queue.size() >= MAX_ARTIFACTS) {
            lastDetected = true;   // prevents rising-edge add
            updateLED();
            return;
        }

        boolean detected = detectArtifact();

        // Rising edge detection (only add once)
        if (detected && !lastDetected) {

            ArtifactColor color = classify();

            if(color != ArtifactColor.UNKNOWN) {
                queue.add(color);
            }
        }

        lastDetected = detected;

        updateLED();
    }


    private boolean detectArtifact() {
        int total1 = sensor1.red() + sensor1.green() + sensor1.blue();
        int total2 = sensor2.red() + sensor2.green() + sensor2.blue();

        return total1 > DETECT_THRESHOLD || total2 > DETECT_THRESHOLD;
    }

    public ArtifactColor classify() {
        int r = sensor1.red() + sensor2.red();
        int g = sensor1.green() + sensor2.green();
        int b = sensor1.blue() + sensor2.blue();

        // Save for telemetry
        lastRed = r;
        lastGreen = g;
        lastBlue = b;

        if (b > r && b > g) return ArtifactColor.PURPLE;
        if (g > r && g > b) return ArtifactColor.GREEN;

        return ArtifactColor.UNKNOWN;
    }

    private void updateLED() {
        ArtifactColor[] arr = queue.toArray(new ArtifactColor[0]);

        setLEDFromQueue(ledServo, arr, 0);
        setLEDFromQueue(ledServo2, arr, 1);
        setLEDFromQueue(ledServo3, arr, 2);
    }

    private void setLEDFromQueue(Servo led, ArtifactColor[] arr, int index) {
        if (arr.length > index) {
            switch (arr[index]) {
                case PURPLE: led.setPosition(PURPLE_POS); break;
                case GREEN:  led.setPosition(GREEN_POS);  break;
                case UNKNOWN: led.setPosition(OFF_POS);  break;
            }
        } else {
            led.setPosition(OFF_POS);
        }
    }

    // Queue management
    public void removeFirst() {
        if (!queue.isEmpty()) {
            queue.poll();
        }

        // Allow scanning again once below max
        lastDetected = false;
    }


    public int size() {
        return queue.size();
    }

    public ArtifactColor peek() {
        return queue.peek();
    }

    public Queue<ArtifactColor> getQueue() {
        return queue;
    }

    // Telemetry
    public int getLastRed() { return lastRed; }
    public int getLastGreen() { return lastGreen; }
    public int getLastBlue() { return lastBlue; }

}
