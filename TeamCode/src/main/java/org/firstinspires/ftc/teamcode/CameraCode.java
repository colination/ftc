package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import for_camera_opmodes.LinearOpModeCamera;

@Autonomous
public class jewelCamera extends LinearOpModeCamera {

    @Override
    public void runOpMode() throws InterruptedException {

        VuforiaLocalizer vuforia;

        waitForStart();

        setCameraDownsampling(2);

        startCamera();
        sleep(2000);

        // Get two values, of the left and right jewel, and then compare the red and blue values

        boolean left;
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

            for (int x = (int)(rgbJewel.getWidth() * .5); x < rgbJewel.getWidth(); x++) {
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

        telemetry.addData("move on", "");
        telemetry.update();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ATW/8fr/////AAAAGZ5Fjme7F0bTj0e+AOR2QIAOmUyzJb0YwYzAFqJZ9s/Mn3mkJq6MvoHNP03tdbewGWZg7BNT4+3qq8AydmSrU5Gbsvd35P3vIf1lJ36C9drgbusNC+rtTTW9lt6rGarj9kvrotz5c6CR2frUiNaxHK3JA6xEjyjGo8jvSgQ3YB03yW5rBdAAxRyKj/Ij30RL6ohnIyKDi03LvDBJiOlTMW3DvXnSgAU+D7TLEokjbjon1U3IS/zjGldbPi2Cv7D5Q98oIlTSfOxJpIgJ9kceLNAqoOQziy3CXc0FUeY8fTQ3/QKOKbF9brRCLoEAn9FmMc2m/MmMlwrImvoLyGvcQWcTabM1zxZXnXX4Q4+AUZaB";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);


        relicTrackables.activate();


        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        while (vuMark == RelicRecoveryVuMark.UNKNOWN && opModeIsActive()) {

               /* Found an instance of the template. In the actual game, you will probably
                * loop until this condition occurs, then move on to act accordingly depending
                * on which VuMark was visible. */
            telemetry.addData("VuMark", "not visible");


        }

        telemetry.addData("VuMark", "%s visible", vuMark);


        telemetry.update();
    }
}

