package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by Navya Janga on 10/23/2017.
 */

public abstract class AutoObjects extends LinearOpMode {

    DcMotor motorFR;
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;

    DcMotor collectLeft;
    DcMotor collectRight;

    DcMotor manipulator;

    ColorSensor jewelColor;

    Servo clawRight;
    Servo clawLeft;

    DcMotor lift;

    Servo jewelHit;

    static final double     COUNTS_PER_MOTOR_REV    = 2240 ;     //REV 41 1301 Encoders
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 5.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

}
