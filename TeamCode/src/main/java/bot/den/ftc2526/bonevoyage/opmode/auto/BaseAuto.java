package bot.den.ftc2526.bonevoyage.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import bot.den.ftc2526.bonevoyage.Alliance;
import bot.den.ftc2526.bonevoyage.subsystem.Drive;
import bot.den.ftc2526.bonevoyage.subsystem.Shooter;

@Autonomous(name = "BaseAuto", group = "Denbot", preselectTeleOp = "FullBotTeleop")
public class BaseAuto extends OpMode {
    private enum AutoState {
        PREP,
        SHOOT,
        GO_BACK,
        ROTATE_TO_BACK,
        GO_FORWARD,
        ROTATE_TO_LOADING_ZONE,
        END
    }

    private final Drive drive = new Drive(telemetry);
    private final Shooter shooter = new Shooter(telemetry);
    private Alliance alliance = Alliance.RED;
    private AutoState state = AutoState.PREP;

    @Override
    public void init() {
        drive.init(hardwareMap);
        drive.resetEncoders();
        shooter.init(hardwareMap);

    }

    @Override
    public void init_loop() {
        if (gamepad1.circle) {
            alliance = Alliance.RED;
        } else if (gamepad1.cross) {
            alliance = Alliance.BLUE;
        }

        telemetry.addData("Press cross", "for BLUE");
        telemetry.addData("Press circle", "for RED");
        telemetry.addData("Selected Alliance", alliance);
    }

    @Override
    public void loop() {
        double degreesToRotate = 0;
        switch (state) {
            case PREP:
                shooter.setNumberOfArtifacts(3);
                state = AutoState.SHOOT;
                break;
            case SHOOT:
                shooter.launch();
                if (shooter.doneShooting()) {
                    shooter.stopLauncher();
                    drive.resetEncoders();
                    state = AutoState.GO_BACK;
                }
                break;
            case GO_BACK:
                if (drive.drive(-45, DistanceUnit.INCH)) {
                    drive.resetEncoders();
                    state = AutoState.ROTATE_TO_BACK;
                }
                break;
            case ROTATE_TO_BACK:
                degreesToRotate = 135;
                if (alliance == Alliance.RED) {
                    degreesToRotate *= -1;
                }
                if (drive.rotate(degreesToRotate, AngleUnit.DEGREES)) {
                    drive.resetEncoders();
                    state = AutoState.GO_FORWARD;
                }
                break;
            case GO_FORWARD:
                if (drive.drive(52, DistanceUnit.INCH)) {
                    drive.resetEncoders();
                    state = AutoState.ROTATE_TO_LOADING_ZONE;
                }
                break;
            case ROTATE_TO_LOADING_ZONE:
                degreesToRotate = 90;
                if (alliance == Alliance.RED) {
                    degreesToRotate *= -1;
                }
                if (drive.rotate(degreesToRotate, AngleUnit.DEGREES)) {
                    drive.resetEncoders();
                    state = AutoState.END;
                }
                break;
        }

        drive.showTelemetry();
        shooter.showTelemetry();
    }
}
