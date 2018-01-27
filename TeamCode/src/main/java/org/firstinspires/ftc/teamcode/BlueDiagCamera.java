package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import for_camera_opmodes.LinearOpModeCamera;

@Autonomous

public class BlueDiagCamera extends LinearOpModeCamera {

    //static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   5000;     // period of each cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position
    static final double     COUNTS_PER_MOTOR_REV    = 2240 ;     //REV 41 1301 Encoders
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 5.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final int vuforiaDist = 500;
    static final int vuforiaBack = 900;


    DcMotor motorFR;
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;

    DcMotor collectLeft;
    DcMotor collectRight;

    DcMotor manipulator;

    DcMotor lift;

    Servo jewelHit;

    CRServo manipFL;
    CRServo manipFR;
    CRServo manipBL;
    CRServo manipBR;

    VuforiaLocalizer vuforia;


    @Override
    public void runOpMode() throws InterruptedException {
        jewelHit = hardwareMap.get(Servo.class, "jewelHit");

        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");

        manipBL = hardwareMap.get(CRServo.class, "manipBL");
        manipBR = hardwareMap.get(CRServo.class, "manipBR");
        manipFL = hardwareMap.get(CRServo.class, "manipBL");
        manipFR = hardwareMap.get(CRServo.class, "manipFR");

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
        telemetry.addLine().addData(">", "MAKE SURE YOU LOAD ME WITH A GLYPH");
        telemetry.update();
        waitForStart();

        // Display the current value
        telemetry.addLine().addData("Servo Position", "%5.2f", jewelHit.getPosition());
        telemetry.addLine().addData(">", "Press Stop to end test.");
        telemetry.update();

        // Set jewel starting position

        jewelHit.setPosition(0);

        idle();

        jewelHit.setPosition(.84);
        sleep(1000);

        telemetry.update();

        setCameraDownsampling(2);

        startCamera();
        sleep(2000);

        // Get two values, of the left and right jewel, and then compare the red and blue values

        boolean left = false;
        int numPics = 0;
        int redValueLeft = 0;
        int redValueRight = 0;

        if (imageReady()) {

            Bitmap rgbJewel;
            rgbJewel = convertYuvImageToRgb(yuvImage, width, height, 1);

            for (int x = 0; x < (rgbJewel.getWidth() * .5); x++) {
                for (int y = 0; y < (int) (.33 * rgbJewel.getHeight()); y++) {
                    int pixel = rgbJewel.getPixel(x, y);
                    redValueRight += red(pixel);
                }
            }

            for (int x = (int) (rgbJewel.getWidth() * .5); x < rgbJewel.getWidth(); x++) {
                for (int y = 0; y < (int) (.33 * rgbJewel.getHeight()); y++) {
                    int pixel = rgbJewel.getPixel(x, y);
                    redValueLeft += red(pixel);
                }
            }

            sleep(1000);

            left = redValueLeft > redValueRight;

            telemetry.addData("Is Jewel Red on the Left?", left);

            telemetry.addData("numPics: ", numPics);

            telemetry.addData("redLeft to redRight: ", redValueLeft + "    " + redValueRight);

            telemetry.update();
        }

        stopCamera();

        sleep(1000);

        if (left) {
            coolEncoderForward(.5, 225);
            idle();
            jewelHit.setPosition(0);
            coolEncoderForward(-.5, 225);
            //coolEncoderForward(-.3, 700);

        } else if (!left){
            coolEncoderForward(-.3, 225);
            //sleep(1000);
            jewelHit.setPosition(0);
            //sleep(1000);
            coolEncoderForward(.5, 225);
            //coolEncoderForward(-.3, 250);
        }

        telemetry.addData("end of camera code", "");
        telemetry.update();

        coolEncoderForward(.1, vuforiaDist);



        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ATW/8fr/////AAAAGZ5Fjme7F0bTj0e+AOR2QIAOmUyzJb0YwYzAFqJZ9s/Mn3mkJq6MvoHNP03tdbewGWZg7BNT4+3qq8AydmSrU5Gbsvd35P3vIf1lJ36C9drgbusNC+rtTTW9lt6rGarj9kvrotz5c6CR2frUiNaxHK3JA6xEjyjGo8jvSgQ3YB03yW5rBdAAxRyKj/Ij30RL6ohnIyKDi03LvDBJiOlTMW3DvXnSgAU+D7TLEokjbjon1U3IS/zjGldbPi2Cv7D5Q98oIlTSfOxJpIgJ9kceLNAqoOQziy3CXc0FUeY8fTQ3/QKOKbF9brRCLoEAn9FmMc2m/MmMlwrImvoLyGvcQWcTabM1zxZXnXX4Q4+AUZaB";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        relicTrackables.activate();

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        while (vuMark == RelicRecoveryVuMark.UNKNOWN && opModeIsActive()) {

            telemetry.addData("VuMark", "not visible");
            telemetry.update();

            vuMark = RelicRecoveryVuMark.from(relicTemplate);
        }

        telemetry.addData("VuMark", "%s visible", vuMark);
        telemetry.update();
        coolEncoderForward(-1, vuforiaBack);
        //vuMark = RelicRecoveryVuMark.CENTER;
        sleep(1500);

        switch(vuMark) {
            case LEFT :
                sleep(1000);
                coolEncoderForward(-.3,1100);
                idle();
                sleep(1000);
                rightEncoder(-.5, 1550);
                coolEncoderForward(-.3, 400);
                sleep(1000);
                manipPower(-0.8);
                break;

            case RIGHT :
                sleep(1000);
                coolEncoderForward(-.3,1100);
                idle();
                sleep(1000);
                rightEncoder(-.5, 1950);
                coolEncoderForward(-.3, 400);
                sleep(1000);
                manipPower(-0.8);
                break;

            case CENTER :
                sleep(1000);
                coolEncoderForward(-.3,1100);
                idle();
                sleep(1000);
                rightEncoder(-.5, 1750);
                coolEncoderForward(-.3, 400);
                sleep(1000);
                manipPower(-0.8);
                break;
        }
        /*sleep(1000);
        coolEncoderForward(-.3, 775);
        idle();
        sleep(1000);
        rightEncoder(-.3, 1900);
        coolEncoderForward(-.3, 550);
        sleep(1000);*/
        manipPower(-0.8);
        //sleep(1000);



        sleep(3000);
        coolEncoderForward(.4, 300);
        sleep(500);
        coolEncoderForward(-.4, 325);
        manipPower(0);
        coolEncoderForward(.4, 150);
        sleep(20000);
        /*sleep(1000);
        coolEncoderForward(-.3, 750);
        idle();
        sleep(1000);
        turnLeft();
        coolEncoderForward(-.3, 300);
        sleep(1000);
        manipPower(-0.8);
        sleep(500);
        manipPower(-0.8);
        sleep(3500);
        coolEncoderForward(.4, 300);
        sleep(500);
        coolEncoderForward(-.4, 325);
        manipPower(0);
        coolEncoderForward(.4, 150);
        sleep(1000);

        telemetry.addLine().addData(">", "Done");
        telemetry.update();*/
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
        while ((Math.abs(motorFR.getCurrentPosition()) < distance) && (opModeIsActive())) {
            motorFL.setPower(speed);
            motorFR.setPower(speed);
            motorBL.setPower(speed);
            motorBR.setPower(speed);
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
        }
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public void manipPower(double power) {
        manipBL.setPower(power);
        manipBR.setPower(-power);
        manipFR.setPower(power);
        manipFL.setPower(power);
    }


    public void turnAround() {
        encoderReset();
        while ((Math.abs(motorFR.getCurrentPosition()) < 2900) && (opModeIsActive())) {//clockwise
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