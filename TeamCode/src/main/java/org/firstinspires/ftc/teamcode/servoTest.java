package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by Navya Janga on 10/21/2017.
 */

@TeleOp(name = "servo", group = "Test")
//@Disabled

public class servoTest extends LinearOpMode{

    CRServo manipServo;

    //@Override
    public void runOpMode()  throws InterruptedException {

        manipServo = hardwareMap.crservo.get("manipServo");
        // try using the servo like a normal servo?
        manipServo.setPower(0);

        waitForStart();

        while (opModeIsActive()) {


            manipServo.setPower(1);
            telemetry.addData("Servo Position", "%5.2f", manipServo.getPower());
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();

            sleep(500);
            manipServo.setPower(0);
            telemetry.addData("Servo Position", "%5.2f", manipServo.getPower());
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();

            sleep(500);
            // testing for distance and power values

            /*while (gamepad1.left_stick_y > 0.1) {
                manipServo.setPower(1);

            }

            while (gamepad1.left_stick_y < -0.1) {
                manipServo.setPower(-1);

            }*/

        }

    }

}


