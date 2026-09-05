package bot.den.ftc2526.bonevoyage.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "Auto1Black", preselectTeleOp = "FullBotTeleopBlack")
public class Auto1Black extends LinearOpMode {

    private DcMotor left_drive;
    private CRServo left_feeder;
    private DcMotor intake;
    private DcMotor launcher;
    private CRServo right_feeder;
    private DcMotor right_drive;

    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
     * Comment Blocks show where to place Initialization code (runs once, after touching the
     * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
     * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
     * Stopped).
     */
    @Override
    public void runOpMode() {
        left_drive = hardwareMap.get(DcMotor.class, "left_drive");
        left_feeder = hardwareMap.get(CRServo.class, "left_feeder");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launcher = hardwareMap.get(DcMotor.class, "launcher");
        right_feeder = hardwareMap.get(CRServo.class, "right_feeder");
        right_drive = hardwareMap.get(DcMotor.class, "right_drive");

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            left_drive.setDirection(DcMotor.Direction.REVERSE);
            left_feeder.setDirection(CRServo.Direction.REVERSE);
            intake.setDirection(DcMotor.Direction.REVERSE);
            ((DcMotorEx) launcher).setVelocityPIDFCoefficients(100, 0, 0, 0);
            launcher.setDirection(DcMotor.Direction.REVERSE);
            ((DcMotorEx) launcher).setVelocity(180, AngleUnit.DEGREES);
            sleep(1000);
            for (int count = 0; count < 3; count++) {
                left_feeder.setPower(1);
                right_feeder.setPower(1);
                sleep(300);
                left_feeder.setPower(0);
                right_feeder.setPower(0);
                sleep(500);
            }
            left_drive.setPower(-1);
            right_drive.setPower(-1);
            sleep(2000);
            left_drive.setPower(0);
            right_drive.setPower(0);
            sleep(2000);
            left_drive.setPower(1);
            right_drive.setPower(1);
            sleep(2000);
            left_drive.setPower(0);
            right_drive.setPower(0);
            for (int count = 0; count < 3; count++) {
                left_feeder.setPower(1);
                right_feeder.setPower(1);
                sleep(300);
                left_feeder.setPower(0);
                right_feeder.setPower(0);
                sleep(500);
            }
            left_drive.setPower(-1);
            right_drive.setPower(-1);
            sleep(2000);
            left_drive.setPower(0);
            right_drive.setPower(0);
            sleep(2000);
            left_drive.setPower(1);
            right_drive.setPower(1);
            sleep(2000);
            left_drive.setPower(0);
            right_drive.setPower(0);
            for (int count = 0; count < 3; count++) {
                left_feeder.setPower(1);
                right_feeder.setPower(1);
                sleep(300);
                left_feeder.setPower(0);
                right_feeder.setPower(0);
                sleep(500);
            }
        }
    }
}