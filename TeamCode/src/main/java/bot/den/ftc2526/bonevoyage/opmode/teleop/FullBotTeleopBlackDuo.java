package bot.den.ftc2526.bonevoyage.opmode.teleop;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bot.den.ftc2526.bonevoyage.subsystem.Drive;
import bot.den.ftc2526.bonevoyage.subsystem.Intake;
import bot.den.ftc2526.bonevoyage.subsystem.Shooter;

@TeleOp(name = "FullBotTeleopBlackDuo", group = "Denbot")
public class FullBotTeleopBlackDuo extends OpMode {
    private final Drive drive = new Drive(telemetry);
    private final Shooter shooter = new Shooter(telemetry);
    private final Intake intake = new Intake(telemetry);

    @Override
    public void init() {
        drive.init(hardwareMap);
        shooter.init(hardwareMap);
        intake.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        drive.arcadeDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (gamepad2.right_bumper) {
            shooter.startLauncher();
        } else if (gamepad2.right_trigger > .5) {
            shooter.requestShot();
        } else if (gamepad2.left_bumper) {
            shooter.runFeederSlowIn();
            intake.intake();
        } else if (gamepad2.left_trigger > .5) {
            intake.outtake();
        } else if (gamepad2.square) {
            shooter.reverseShooter();
            shooter.runFeederReverse();
            intake.slowIntake();
        } else if (!shooter.doneShooting()) {
            shooter.launch();
        } else {
            intake.stopIntake();
            shooter.stop();
        }

        drive.showTelemetry();
        shooter.showTelemetry();
        intake.showTelemetry();
    }
};
