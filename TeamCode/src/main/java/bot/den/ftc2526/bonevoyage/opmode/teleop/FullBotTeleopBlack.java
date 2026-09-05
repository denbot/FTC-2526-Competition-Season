 package bot.den.ftc2526.bonevoyage.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "FullBotTeleopBlack", group = "Denbot")
public class FullBotTeleopBlack extends LinearOpMode {

    private DcMotor left_drive;
    private CRServo left_feeder;
    private DcMotor intake;
    private DcMotor launcher;
    private DcMotor right_drive;
    private CRServo right_feeder;

    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
     * Comment Blocks show where to place Initialization code (runs once, after touching the
     * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
     * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
     * Stopped).
     */
    @Override
    public void runOpMode() {
        float LeftStickY;

        left_drive = hardwareMap.get(DcMotor.class, "left_drive");
        left_feeder = hardwareMap.get(CRServo.class, "left_feeder");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launcher = hardwareMap.get(DcMotor.class, "launcher");
        right_drive = hardwareMap.get(DcMotor.class, "right_drive");
        right_feeder = hardwareMap.get(CRServo.class, "right_feeder");

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            left_drive.setDirection(DcMotor.Direction.REVERSE);
            left_feeder.setDirection(CRServo.Direction.REVERSE);
            intake.setDirection(DcMotor.Direction.REVERSE);
            ((DcMotorEx) launcher).setVelocityPIDFCoefficients(100, 0, 0, 0);
            launcher.setDirection(DcMotor.Direction.REVERSE);
            while (opModeIsActive()) {
                LeftStickY = gamepad1.left_stick_y;
                left_drive.setPower(-LeftStickY);
                right_drive.setPower(-LeftStickY);
                if (gamepad1.right_stick_x < 0) {
                    left_drive.setPower(-1);
                    right_drive.setPower(1);
                } else if (gamepad1.right_stick_x > 0) {
                    left_drive.setPower(1);
                    right_drive.setPower(-1);
                }
                if (gamepad1.a) {
                    intake.setPower(1);
                    right_feeder.setPower(-1);
                    ((DcMotorEx) launcher).setVelocity(-180, AngleUnit.DEGREES);
                    left_feeder.setPower(-1);
                } else {
                    left_feeder.setPower(0);
                    intake.setPower(0);
                    right_feeder.setPower(0);
                }
                if (gamepad1.right_trigger_pressed) {
                    left_feeder.setPower(1);
                    right_feeder.setPower(1);
                } else {
                    left_feeder.setPower(0);
                    right_feeder.setPower(0);
                }
                if (gamepad1.b) {
                    ((DcMotorEx) launcher).setVelocity(180, AngleUnit.DEGREES);
                } else {
                    ((DcMotorEx) launcher).setVelocity(0, AngleUnit.DEGREES);
                }
                telemetry.update();
            }
        }
    }
}