package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

@Autonomous (name = "servoTest", group = "Auto")
//@Disabled

public class servoTest extends LinearOpMode{

    CRServo jewelHit;

    //@Override
    public void runOpMode()  throws InterruptedException {

        jewelHit = hardwareMap.crservo.get("jewelHit");
        jewelHit.setPower(0);

        waitForStart();

        while (opModeIsActive()) {

            // testing for distance and power values
            telemetry.addData("", "start");
            telemetry.update();

            jewelHit.setPower(1);

            telemetry.addData("", "powerSet");
            telemetry.update();

            Thread.sleep(1000);


            jewelHit.setPower(0);

            telemetry.addData("", "powerStop");
            telemetry.update();

            // testing for jewelCode : do this after we find the correct distance values
            // hitJewel;

        }

    }

}


