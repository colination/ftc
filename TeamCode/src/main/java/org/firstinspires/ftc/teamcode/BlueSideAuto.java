/*package org.firstinspires.ftc.teamcode;

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

/*@Autonomous
@Disabled
public class BlueSideAuto extends AutoMethods {

    public void runOpMode(){

        initVariables();
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTrackables.activate();

        while (opModeIsActive()){
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
            }

            else {
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

            switch(vuMark) {
                case LEFT :
                    motorEncoder(0.5, -14, 14, 14, -14);
                case CENTER :
                    motorEncoder(0.5, -22.5, 22.5, 22.5, -22.5);
                case RIGHT :
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
