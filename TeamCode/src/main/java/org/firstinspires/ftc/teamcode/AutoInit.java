package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Navya Janga on 10/23/2017.
 */

public abstract class AutoInit extends AutoObjects {

    public void initVariables() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

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

        collectLeft = hardwareMap.dcMotor.get("collectLeft");
        collectRight = hardwareMap.dcMotor.get("collectRight");

        lift = hardwareMap.dcMotor.get("lift");

        clawRight = hardwareMap.servo.get("clawRight");
        clawLeft = hardwareMap.servo.get("clawLeft");

        manipulator = hardwareMap.dcMotor.get("manipulator");

        jewelColor = hardwareMap.get(ColorSensor.class, "jewelColor");

        jewelHit = hardwareMap.servo.get("jewelHit");

        sleep(2000);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // OR...  Do Not Activate the Camera Monitor View, to save power
        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code onthe next line, between the double quotes.
         */
        parameters.vuforiaLicenseKey = "ATW/8fr/////AAAAGZ5Fjme7F0bTj0e+AOR2QIAOmUyzJb0YwYzAFqJZ9s/Mn3mkJq6MvoHNP03tdbewGWZg7BNT4+3qq8AydmSrU5Gbsvd35P3vIf1lJ36C9drgbusNC+rtTTW9lt6rGarj9kvrotz5c6CR2frUiNaxHK3JA6xEjyjGo8jvSgQ3YB03yW5rBdAAxRyKj/Ij30RL6ohnIyKDi03LvDBJiOlTMW3DvXnSgAU+D7TLEokjbjon1U3IS/zjGldbPi2Cv7D5Q98oIlTSfOxJpIgJ9kceLNAqoOQziy3CXc0FUeY8fTQ3/QKOKbF9brRCLoEAn9FmMc2m/MmMlwrImvoLyGvcQWcTabM1zxZXnXX4Q4+AUZaB";


        /*
         * We also indicate which camera on the RC that we wish to use.
         * Here we chose the back (HiRes) camera (for greater range), but
         * for a competition robot, the front camera might be more convenient.
         */
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        /**
         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
         * in this data set: all three of the VuMarks in the game were created from this one template,
         * but differ in their instance id information.
         * @see VuMarkInstanceId
         */
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary


        telemetry.addData("", "hi");
        telemetry.update();

        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        time.reset();

        waitForStart();

    }
}
