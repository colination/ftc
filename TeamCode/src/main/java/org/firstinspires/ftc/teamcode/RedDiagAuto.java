package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
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

public class RedDiagAuto extends LinearOpMode {//STILL RED SIDE

    //static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   5000;     // period of each cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position
    static final double     COUNTS_PER_MOTOR_REV    = 2240 ;     //REV 41 1301 Encoders
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 5.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    static final double ejectBlock = 0.7;
    static final double moveSpeed = .6;
    static final int pushBlock = 400;
    //
    static final int cryptoDist = 1500;
    static final int cryptoInt = 200;
    static final int cryptoMid = 1500;

    // Define class members
    //double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    //boolean rampUp = true;
    DcMotor motorFR;
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;
    ElapsedTime time = new ElapsedTime();

    DcMotor collectLeft;
    DcMotor collectRight;


    ColorSensor sensorColor;
    DcMotor lift;

    Servo jewelHit;

    CRServo manipFL;
    CRServo manipFR;
    CRServo manipBL;
    CRServo manipBR;


    VuforiaLocalizer vuforia;


    @Override
    public void runOpMode() {
        jewelHit = hardwareMap.get(Servo.class, "jewelHit");

        manipBL = hardwareMap.get(CRServo.class, "manipBL");
        manipBR = hardwareMap.get(CRServo.class, "manipBR");
        manipFL = hardwareMap.get(CRServo.class, "manipBL");
        manipFR = hardwareMap.get(CRServo.class, "manipFR");
        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");
        sensorColor = hardwareMap.get(ColorSensor.class, "jewelColor");


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
        telemetry.addLine().addData(">", "MAKE SURE YOU LOAD ME WITH A GLYPH" );
        telemetry.update();
        waitForStart();

        VuforiaLocalizer vuforia;


        /*int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ATW/8fr/////AAAAGZ5Fjme7F0bTj0e+AOR2QIAOmUyzJb0YwYzAFqJZ9s/Mn3mkJq6MvoHNP03tdbewGWZg7BNT4+3qq8AydmSrU5Gbsvd35P3vIf1lJ36C9drgbusNC+rtTTW9lt6rGarj9kvrotz5c6CR2frUiNaxHK3JA6xEjyjGo8jvSgQ3YB03yW5rBdAAxRyKj/Ij30RL6ohnIyKDi03LvDBJiOlTMW3DvXnSgAU+D7TLEokjbjon1U3IS/zjGldbPi2Cv7D5Q98oIlTSfOxJpIgJ9kceLNAqoOQziy3CXc0FUeY8fTQ3/QKOKbF9brRCLoEAn9FmMc2m/MmMlwrImvoLyGvcQWcTabM1zxZXnXX4Q4+AUZaB";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTrackables.activate();*/

        while (opModeIsActive()) {//distnce 1600
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
            // Display the current value
            telemetry.addLine().addData("Servo Position", "%5.2f", jewelHit.getPosition());
            telemetry.addLine().addData(">", "Press Stop to end test.");
            telemetry.update();
            sleep(3000);

            // Set the servo to the new position and pause;


            sleep(500);
            idle();
            jewelHit.setPosition(.99);
            sleep(2500);
            idle();
            telemetry.addLine().addData("red", sensorColor.red());
            telemetry.addLine().addData("blue", sensorColor.blue());
            telemetry.update();

            if(sensorColor.red() > sensorColor.blue() + sensorColor.blue() * .4) {
                coolEncoderForward(-moveSpeed, 240);
                idle();

                coolEncoderForward(moveSpeed, 240);
                idle();
                jewelHit.setPosition(0);
            }
            else {
                coolEncoderForward(moveSpeed, 240);
                idle();

                coolEncoderForward(-moveSpeed, 240);
                idle();
                jewelHit.setPosition(0);
            }
            //coolEncoderForward(.3, 1350);
            /*sleep(1000);
            coolEncoderForward(.3, 500);
            idle();
            sleep(1000);
            rightEncoder(-.3, 1900);
            turnAround();
            coolEncoderForward(-.3, 350);
            sleep(1000);
            manipServo.setPosition(0);
            sleep(1000);
            manipulator.setPower(-1);*/
            idle();
            sleep(3000);
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            //RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            telemetry.addData("read vuforia", vuMark);
            telemetry.update();
            /*if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                // Found an instance of the template.
                telemetry.addData("VuMark", "%s visible", vuMark);
                telemetry.update();
                if (vuMark == RelicRecoveryVuMark.LEFT) {
                    sleep(1000);
                    coolEncoderForward(.3, cryptoDist);
                    idle();
                    sleep(1000);
                    rightEncoder(-.3, 1950);
                    turnAround();
                    coolEncoderForward(-.3, 100);
                    coolEncoderForward(-.3, 200);
                    sleep(1000);

                    sleep(1000);
                    manipulator.setPower(-1);
                }
                if (vuMark == RelicRecoveryVuMark.CENTER) {
                    sleep(1000);
                    coolEncoderForward(.3, cryptoDist);
                    idle();
                    sleep(1000);
                    rightEncoder(-.3, 1750);
                    turnAround();
                    coolEncoderForward(-.3, 100);
                    coolEncoderForward(-.3, 200);
                    sleep(1000);

                    sleep(1000);
                    manipulator.setPower(-1);
                }
                if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    sleep(1000);
                    coolEncoderForward(.3, cryptoDist);
                    idle();
                    sleep(1000);
                    rightEncoder(-.3, 1550);
                    turnAround();
                    coolEncoderForward(-.3, 100);
                    coolEncoderForward(-.3, 200);
                    sleep(1000);

                    sleep(1000);
                    manipulator.setPower(-1);
                }
            }*/
            switch(vuMark) {
                case LEFT :
                    sleep(1000);
                    coolEncoderForward(moveSpeed, cryptoDist);
                    idle();
                    sleep(1000);
                    rightEncoder(-moveSpeed, cryptoMid + cryptoInt);
                    turnAround();
                    coolEncoderForward(-moveSpeed, 500);

                    sleep(1000);
                    manipPower(-ejectBlock);
                    break;

                case RIGHT :
                    sleep(1000);
                    coolEncoderForward(moveSpeed, cryptoDist);
                    idle();
                    sleep(1000);
                    rightEncoder(-moveSpeed, cryptoMid - cryptoInt);
                    turnAround();
                    coolEncoderForward(-moveSpeed, 500);

                    sleep(1000);
                    manipPower(ejectBlock);
                    break;

                case CENTER :
                    sleep(1000);
                    coolEncoderForward(moveSpeed,cryptoDist);
                    idle();
                    sleep(1000);
                    rightEncoder(-moveSpeed, cryptoMid);
                    turnAround();
                    coolEncoderForward(-moveSpeed, 500);

                    sleep(1000);
                    manipPower(-ejectBlock);
                    break;

                default :
                    sleep(1000);
                    coolEncoderForward(moveSpeed, cryptoDist);
                    idle();
                    sleep(1000);
                    rightEncoder(-moveSpeed, cryptoMid);
                    turnAround();
                    coolEncoderForward(-moveSpeed, 850);

                    sleep(1000);
                    manipPower(-ejectBlock);
                    telemetry.addData("defaultCenter", "");
                    telemetry.update();
                    break;
            }
            telemetry.addData("done reading vuforia", vuMark);
            telemetry.update();

            sleep(3000);
            coolEncoderForward(moveSpeed, 300);
            sleep(500);
            coolEncoderForward(-moveSpeed, 325);
            manipPower(0);
            coolEncoderForward(moveSpeed, 150);
            sleep(20000);
            telemetry.addLine().addData(">", "Done");
            telemetry.update();
        }
    }

    public void manipPower(double power) {
        manipBL.setPower(-power);
        manipBR.setPower(power);
        manipFR.setPower(power);
        manipFL.setPower(-power);
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
            //telemetry.addData("Encoder", (Math.abs(motorFR.getCurrentPosition())));
            //telemetry.update();
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
}
