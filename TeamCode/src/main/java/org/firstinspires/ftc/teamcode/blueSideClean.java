package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

 /* Created by Navya Janga on 9/20/2017.*/


@Autonomous
//@Disabled
public class blueSideClean extends LinearOpMode {//STILL RED SIDE AUTO

    //static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   5000;     // period of each cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position
    static final double     COUNTS_PER_MOTOR_REV    = 2240 ;     //REV 41 1301 Encoders
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 5.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    // Define class members
    //double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    //boolean rampUp = true;
    DcMotor motorFR;
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;

    DcMotor collectLeft;
    DcMotor collectRight;

    DcMotor manipulator;

    ColorSensor sensorColor;
    DcMotor lift;

    Servo jewelHit;
    Servo manipServo;

    VuforiaLocalizer vuforia;


    @Override
    public void runOpMode() {
        jewelHit = hardwareMap.get(Servo.class, "jewelHit");
        manipServo = hardwareMap.get(Servo.class, "manipServo");
        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");
        sensorColor = hardwareMap.get(ColorSensor.class, "jewelColor");
        manipulator = hardwareMap.dcMotor.get("manipulator");

        motorFL.setDirection(DcMotor.Direction.REVERSE);
        motorBL.setDirection(DcMotor.Direction.REVERSE);


        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        collectLeft = hardwareMap.dcMotor.get("collectLeft");
        collectRight = hardwareMap.dcMotor.get("collectRight");

        lift = hardwareMap.dcMotor.get("lift");

        // Wait for the start button
        // Wait for the start button
        telemetry.addLine().addData(">", "MAKE SURE YOU LOAD ME WITH A GLYPH" );
        telemetry.update();
        waitForStart();

        manipServo.setPosition(0);
        jewelHit.setPosition(0);

        while (opModeIsActive()) {


            // Display the current value
            telemetry.addLine().addData("Servo Position", "%5.2f", jewelHit.getPosition());
            telemetry.addLine().addData(">", "Press Stop to end test.");
            telemetry.update();

            // Set the servo to the new position and pause
            jewelHit.setPosition(.84);
            sleep(1000);
            idle();

            telemetry.addLine().addData("Color", sensorColor.red());
            telemetry.update();
            sleep(3000);

            telemetry.update();


            if(sensorColor.red() > 12) {
                coolEncoderForward(.4, 100);
                idle();
                sleep(400);
                jewelHit.setPosition(0);
                idle();
                sleep(1000);
                coolEncoderForward(-.3, 1000);

            }
            else {
                coolEncoderForward(-.3, 200);
                sleep(300);
                jewelHit.setPosition(0);
                sleep(1000);
                coolEncoderForward(-.3, 600);
            }
            sleep(1000);
            coolEncoderForward(-.3, 800);
            idle();
            sleep(1000);
            turnLeft();
            coolEncoderForward(-.3, 275);
            sleep(1000);
            manipServo.setPosition(1);
            sleep(1000);


            manipulator.setPower(-1);
            sleep(3000);
            coolEncoderForward(.4, 300);
            sleep(500);
            coolEncoderForward(-.4, 325);

            manipulator.setPower(0);
            coolEncoderForward(.4, 150);

            sleep(20000);


            telemetry.addLine().addData(">", "Done");
            telemetry.update();

        }
    }

    public void encoderReset() {
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void coolEncoderForward(double speed, int distance) {

        encoderReset();
        while ((Math.abs(motorFR.getCurrentPosition()) < distance) && (opModeIsActive())) {//encoders may be moving forward
            motorFL.setPower(speed);
            motorFR.setPower(speed);
            motorBL.setPower(speed);
            motorBR.setPower(speed);
            telemetry.addData("Encoder", (Math.abs(motorFR.getCurrentPosition())));
            telemetry.update();
        }
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);

    }

    public void rightEncoder(double speed, int distance) {
        encoderReset();
        while ((Math.abs(motorFR.getCurrentPosition()) < distance) && (opModeIsActive())) {
            motorFL.setPower(speed);
            motorFR.setPower(-speed);
            motorBL.setPower(-speed);
            motorBR.setPower(speed);
            telemetry.addData("Encoder", (Math.abs(motorFR.getCurrentPosition())));
            telemetry.update();
        }
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public void turnLeft() {
        encoderReset();
        while ((Math.abs(motorFR.getCurrentPosition()) < 1350) && (opModeIsActive())) {//clockwise
            motorFL.setPower(-.5);
            motorFR.setPower(.5);
            motorBL.setPower(-.5);
            motorBR.setPower(.5);
            telemetry.addData("Encoder", (Math.abs(motorFR.getCurrentPosition())));
            telemetry.update();
        }
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public void turnRight() {
        encoderReset();
        while ((Math.abs(motorFR.getCurrentPosition()) < 1350) && (opModeIsActive())) {//clockwise
            motorFL.setPower(.5);
            motorFR.setPower(-.5);
            motorBL.setPower(.5);
            motorBR.setPower(-.5);
            telemetry.addData("Encoder", (Math.abs(motorFR.getCurrentPosition())));
            telemetry.update();
        }
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }
}
