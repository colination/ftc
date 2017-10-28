/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

 /* Created by Navya Janga on 9/20/2017.*/


/*@Autonomous
@Disabled
public class BlueDiagAuto extends AutoMethods {

<<<<<<< HEAD
    ElapsedTime time;
    IMUSensors imu = new IMUSensors();
    imu.initIMU(HardwareMap map);
//init
    //use for all

    DcMotor motorFR;
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
=======
    public void runOpMode() {
>>>>>>> origin/master

        initVariables();
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTrackables.activate();*/
/*
        while (opModeIsActive()) {
            // open jewel servo
            jewelHit.setPosition(0.5); // value of servo to be open

            // move robot sideways until it senses the jewel
            while (jewelColor.red() > 10 && jewelColor.red() < 25) {

                motorPower(0.5); // strafe
            }

            motorStop();

            // color sense one jewel and knock off the opposite color
            if (jewelColor.red() < 10) // blue value
            {
                motorEncoder(0.5, 20, 20, 20, 20);
            } else {
                motorEncoder(0.5, -20, -20, -20, -20);
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
            switch (vuMark) {
                case LEFT:
                    motorEncoder(0.5, -14, 14, 14, -14);
                case CENTER:
                    motorEncoder(0.5, -22.5, 22.5, 22.5, -22.5);
                case RIGHT:
                    motorEncoder(0.5, -30.4, 30.4, 30.4, -30.4);
            }

            // move forward
            motorEncoder(0.5, 24, 24, 24, 24); // measure distance later based on encoder tickets or use time value


            // place glyph in correct section
            // figure out time value needed for one block to pass through manipulator
            manipMove(1);

            // turn off manipulator
            manipMove(0);

        }

    }

}*/
