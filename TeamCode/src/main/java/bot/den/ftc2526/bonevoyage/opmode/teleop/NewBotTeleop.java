package bot.den.ftc2526.bonevoyage.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bot.den.ftc2526.bonevoyage.subsystem.Drive;
import bot.den.ftc2526.bonevoyage.subsystem.Intake;

@TeleOp(name = "NewBotTeleop", group = "Denbot")
@Disabled
public class NewBotTeleop extends OpMode {
    private final Drive drive = new Drive(telemetry);
    private final Intake intake = new Intake(telemetry);

    @Override
    public void init() {
        intake.init(hardwareMap);
        drive.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        drive.arcadeDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);
        drive.showTelemetry();
        if (gamepad1.left_trigger > .5) {
            intake.intake();

        }
        else if(gamepad1.left_bumper){
            intake.outtake();
        }
        else {
            intake.stopIntake();
        }
        intake.showTelemetry();
        telemetry.addData("LeftTrigger",gamepad1.left_trigger);
    }
}
