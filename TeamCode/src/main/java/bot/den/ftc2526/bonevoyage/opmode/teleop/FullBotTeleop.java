package bot.den.ftc2526.bonevoyage.opmode.teleop;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bot.den.ftc2526.bonevoyage.subsystem.Drive;
import bot.den.ftc2526.bonevoyage.subsystem.Intake;
import bot.den.ftc2526.bonevoyage.subsystem.Shooter;
import bot.den.ftc2526.bonevoyage.subsystem.Spinnythingy;

@TeleOp(name = "FullBotTeleop", group = "Denbot")
public class FullBotTeleop extends OpMode {
    private final Spinnythingy spinnythingy = new Spinnythingy(telemetry);
    private final Drive drive = new Drive(telemetry);
    private final Shooter shooter = new Shooter(telemetry);
    private final Intake intake = new Intake(telemetry);

    @Override
    public void init() {
        spinnythingy.init(hardwareMap);
        drive.init(hardwareMap);
        shooter.init(hardwareMap);
        intake.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void start(){
        spinnythingy.startSpinning();
    }
    @Override
    public void loop() {
        drive.arcadeDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (gamepad1.circle) {
            intake.stopIntake();
        } else if (gamepad1.left_bumper) {
            intake.intake();
        } else if (gamepad1.left_trigger > .5) {
            intake.outtake();
        } else if (gamepad1.square) {
            intake.slowIntake();
        } else {
            intake.stopIntake();
        }

        if (gamepad1.circle) {
            shooter.stop();
        } else if (gamepad1.y) {
            shooter.startLauncher();
        } else if (gamepad1.rightBumperWasPressed()) {
            shooter.requestShot();
        } else if (gamepad1.left_bumper) {
            shooter.runFeederSlowIn();
        } else if (!shooter.doneShooting()) {
            shooter.launch();
        } else if (gamepad1.square) {
            shooter.reverseShooter();
            shooter.runFeederReverse();
        } else {
            shooter.stop();
        }

            if (gamepad1.y) {
                spinnythingy.reverseDirection();
            }
        }
        spinnythingy.showTelemetry();
        drive.showTelemetry();
        shooter.showTelemetry();
    }
};
