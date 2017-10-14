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
@Disabled
public class BlueAuto extends LinearOpMode {

    ElapsedTime time;

    DcMotor motorFR;//my code maps it as fRMotor
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;

    DcMotor leftManip;
    DcMotor rightManip;

    ColorSensor jewelColor;

    Servo jewelHit;

    VuforiaLocalizer vuforia;


    static final double     COUNTS_PER_MOTOR_REV    = 2240 ;     //REV 41 1301 Encoders
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.5;
    static final double     TURN_SPEED              = 0.5;

    public void runOpMode(){

        time.reset();

        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorBR = hardwareMap.dcMotor.get("motorBR");
        motorBL = hardwareMap.dcMotor.get("motorBL");

        motorFL.setDirection(DcMotor.Direction.REVERSE);
        motorBL.setDirection(DcMotor.Direction.REVERSE);

        motorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftManip = hardwareMap.dcMotor.get("leftManip");
        rightManip = hardwareMap.dcMotor.get("rightManip");

        jewelColor = hardwareMap.colorSensor.get("jewelColor");

        jewelHit = hardwareMap.servo.get("jewelHit");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ATW/8fr/////AAAAGZ5Fjme7F0bTj0e+AOR2QIAOmUyzJb0YwYzAFqJZ9s/Mn3mkJq6MvoHNP03tdbewGWZg7BNT4+3qq8AydmSrU5Gbsvd35P3vIf1lJ36C9drgbusNC+rtTTW9lt6rGarj9kvrotz5c6CR2frUiNaxHK3JA6xEjyjGo8jvSgQ3YB03yW5rBdAAxRyKj/Ij30RL6ohnIyKDi03LvDBJiOlTMW3DvXnSgAU+D7TLEokjbjon1U3IS/zjGldbPi2Cv7D5Q98oIlTSfOxJpIgJ9kceLNAqoOQziy3CXc0FUeY8fTQ3/QKOKbF9brRCLoEAn9FmMc2m/MmMlwrImvoLyGvcQWcTabM1zxZXnXX4Q4+AUZaB";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive()){
            // open jewel servo
            jewelHit.setPosition(0.5); // value of servo to be open

            // move robot sideways until it senses the jewel

            motorFR.setPower(0.5);
            motorBR.setPower(-0.5);

            motorFL.setPower(-0.5);
            motorBL.setPower(0.5);

            if ((jewelColor.red() < 10 || jewelColor.red() > 25)) // replace 0 with value when color sensor reads a jewel (senses blue)
            {
               motorStop();

            }

            // color sense one jewel and knock off the opposite color (SensorTest.java)
            if (jewelColor.red() < 10) // blue value
            {   // insert while(time < 0.5 seconds)
                motorEncoder(0.5, 5, 5, 5, 5);
            }

            else {
                motorEncoder(0.5, 5, 5, 5, 5);
            }

            // put arm back in
            jewelHit.setPosition(0);

            // move towards glyphCode until the glyphCode is visible and scan glyphCode
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

            while (vuMark == RelicRecoveryVuMark.UNKNOWN) {
                motorEncoder(0.5, 5, 5, 5, 5);
            }

            motorStop();

            // get to the square based on the distance values
            if (vuMark.equals("LEFT")){
                motorStop();
            }

            else if (vuMark.equals("RIGHT")) {
                motorStop();
            }

            else if (vuMark.equals("CENTER"))
            {
                motorStop();
            }

            // move forward
            motorEncoder(0.5, 5, 5, 5, 5); // measure distance later based on encoder tickets or use time value


            // place glyph in correct section
            // figure out time value needed for one block to pass through manipulator
            manipMove(1);

            // turn off manipulator
            manipMove(0);

        }


    }

    public void motorEncoder(double speed, double inchesFR, double inchesFL, double inchesBR, double inchesBL){

        int newFLTarget;
        int newFRTarget;
        int newBLTarget;
        int newBRTarget;

        newFLTarget = motorFL.getCurrentPosition() + (int)(inchesFL * COUNTS_PER_INCH);
        newFRTarget = motorFR.getCurrentPosition() + (int)(inchesFR * COUNTS_PER_INCH);
        newBLTarget = motorBL.getCurrentPosition() + (int)(inchesBL * COUNTS_PER_INCH);
        newBRTarget = motorFR.getCurrentPosition() + (int)(inchesBR * COUNTS_PER_INCH);
        motorFL.setTargetPosition(newFLTarget);
        motorFR.setTargetPosition(newFRTarget);
        motorBL.setTargetPosition(newBLTarget);
        motorBR.setTargetPosition(newBRTarget);

        // Turn On RUN_TO_POSITION
        motorFL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFL.setPower(Math.abs(speed));
        motorFR.setPower(Math.abs(speed));
        motorBL.setPower(Math.abs(speed));
        motorBR.setPower(Math.abs(speed));


    }

    public void motorStop() {
        motorFR.setPower(0);
        motorBR.setPower(0);
        motorFL.setPower(0);
        motorBL.setPower(0);
    }

    public void manipMove(double speed){
        leftManip.setPower(speed);
        rightManip.setPower(speed);
    }

}
