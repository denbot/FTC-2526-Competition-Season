package bot.den.ftc2526.bonevoyage.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bot.den.ftc2526.bonevoyage.subsystem.Drive;
import bot.den.ftc2526.bonevoyage.subsystem.Shooter;

@TeleOp(name = "BaseTeleop", group = "Denbot")
@Disabled
public class BaseTeleop extends OpMode {
   private final Drive drive = new Drive(telemetry);
   private final Shooter shooter = new Shooter(telemetry);

    @Override
    public void init() {
        drive.init(hardwareMap);
        shooter.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        drive.arcadeDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (gamepad1.y) {
            shooter.startLauncher();
        } else if (gamepad1.b) {
            shooter.stopLauncher();
        }

        if(gamepad1.rightBumperWasPressed()){
            shooter.requestShot();
        }
        shooter.launch();


        drive.showTelemetry();
        shooter.showTelemetry();
    }
}