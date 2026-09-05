package bot.den.ftc2526.bonevoyage.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import bot.den.ftc2526.bonevoyage.Alliance;
import bot.den.ftc2526.bonevoyage.subsystem.Drive;
import bot.den.ftc2526.bonevoyage.subsystem.Intake;
import bot.den.ftc2526.bonevoyage.subsystem.Shooter;

@Autonomous(name = "Auto2Black", group="Denbot", preselectTeleOp = "FullBotTeleopBlackDuo")
public class Auto2Black extends OpMode {
    private final Drive drive = new Drive(telemetry);
    private final Shooter shooter = new Shooter(telemetry);
    private final Intake intake = new Intake(telemetry);
    private Alliance alliance = Alliance.RED;

    private enum AutoState {
        FORWARD,
        REVERSE_LONG,
        REVERSE_SHORT,
        ROTATE_CW,
        ROTATE_CCW,
        ROTATE_CLEAR,
        INTAKE,
        SHOOT,
        END;
    };
    private int i = 0;

    private final AutoState[] autoSequence = {
            AutoState.SHOOT,
            AutoState.REVERSE_LONG,
            AutoState.ROTATE_CCW,
            AutoState.INTAKE,
            AutoState.REVERSE_SHORT,
            AutoState.ROTATE_CW,
            AutoState.FORWARD,
            AutoState.SHOOT,
            AutoState.REVERSE_LONG,
            AutoState.ROTATE_CLEAR,
            AutoState.FORWARD,
            AutoState.END
    };

    @Override
    public void init() {
        drive.init(hardwareMap);
        drive.resetEncoders();
        shooter.init(hardwareMap);
        intake.init(hardwareMap);
        shooter.setNumberOfArtifacts(3);
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
        telemetry.addData("Step", i);
        telemetry.addData("Sequence", autoSequence[i]);
        switch (autoSequence[i]) {
            case SHOOT :
                shoot();
                break;
            case FORWARD :
                drive(50);
                break;
            case REVERSE_LONG :
                drive(-50);
                break;
            case REVERSE_SHORT :
                drive(-36);
                break;
            case ROTATE_CCW :
                rotate(45);
                break;
            case ROTATE_CW :
                rotate(-45);
                break;
            case INTAKE :
                driveAndCollect();
                break;
            case END:
                break;
        }
    }

    private void shoot() {
        shooter.launch();
        if (shooter.doneShooting()) {
            shooter.stopLauncher();
            drive.resetEncoders();
            i++;
        }
    }

    private void drive(int distance) {
        if (drive.drive(distance, DistanceUnit.INCH)) {
            drive.resetEncoders();
            intake.stopIntake();
            shooter.stop();
            i++;
        }
    }

    private void rotate(int degrees) {
        int degreesToRotate = alliance == Alliance.RED ? degrees : (degrees *= -1);
        if (drive.rotate(degreesToRotate, AngleUnit.DEGREES)) {
            drive.resetEncoders();
            i++;
        }

    }

    private void driveAndCollect(){
        intake.intake();
        shooter.runFeederSlowIn();
        if(drive.driveSlow(36,DistanceUnit.INCH)){
            drive.resetEncoders();
            intake.slowIntake();
            shooter.reverseShooter();
            shooter.setNumberOfArtifacts(3);
            i++;
        }
    }
}