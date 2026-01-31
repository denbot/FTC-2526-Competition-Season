package bot.den.ftc2526.bonevoyage.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import bot.den.ftc2526.bonevoyage.Alliance;
import bot.den.ftc2526.bonevoyage.subsystem.Drive;
import bot.den.ftc2526.bonevoyage.subsystem.Intake;
import bot.den.ftc2526.bonevoyage.subsystem.Shooter;

@Autonomous(name = "IntakeAuto", group="Denbot", preselectTeleOp = "IntakeAuto")
public class IntakeAuto extends OpMode {
    private enum AutoState {
         PREP,
         SHOOT_A,
         BACKUP,
         ROTATE_A,
         COLLECT,
         BACKUP_B,
         ROTATE_B,
         FORWARD,
         SHOOT_B,
         OUT_OF_WAY,
         OUT_OF_WAY_B,
         OUT_OF_WAY_C,
         END
    }
    private final Drive drive = new Drive(telemetry);
    private final Shooter shooter = new Shooter(telemetry);
    private final Intake intake = new Intake(telemetry);
    private Alliance alliance = Alliance.RED;
    private AutoState state = AutoState.PREP;

    @Override
    public void init(){
        drive.init(hardwareMap);
        drive.resetEncoders();
        shooter.init(hardwareMap);
        intake.init(hardwareMap);
    }
    @Override
    public void init_loop(){
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
                state = IntakeAuto.AutoState.SHOOT_A;
                break;
            case SHOOT_A:
                shooter.launch();
                if (shooter.doneShooting()) {
                    shooter.stopLauncher();
                    drive.resetEncoders();
                    state = AutoState.BACKUP;
                }
                break;
            case BACKUP:
                if (drive.drive(-50, DistanceUnit.INCH)) {
                    drive.resetEncoders();
                    state = AutoState.ROTATE_A;
                }
                break;
            case ROTATE_A:
                degreesToRotate = 45;
                if (alliance == Alliance.RED) {
                    degreesToRotate *= -1;
                }
                if (drive.rotate(degreesToRotate, AngleUnit.DEGREES)) {
                    drive.resetEncoders();
                    state = AutoState.COLLECT;
                    intake.intake();
                }
                break;
            case COLLECT:
                if (drive.driveSlow(34, DistanceUnit.INCH)) {
                    drive.resetEncoders();
                    intake.stopIntake();
                    shooter.runFeederReverse();
                    state = AutoState.BACKUP_B;
                }
                break;
            case BACKUP_B:
                if (drive.drive(-40, DistanceUnit.INCH)) {
                    drive.resetEncoders();
                    shooter.stop();
                    state = AutoState.ROTATE_B;
                }
                break;
            case ROTATE_B:
                degreesToRotate = -45;
                if (alliance == Alliance.RED) {
                    degreesToRotate *= -1;
                }
                if (drive.rotate(degreesToRotate, AngleUnit.DEGREES)) {
                    drive.resetEncoders();
                    state = AutoState.FORWARD;
                }
                break;
            case FORWARD:
                if (drive.drive(52, DistanceUnit.INCH)) {
                    shooter.setNumberOfArtifacts(3);
                    drive.resetEncoders();
                    state = AutoState.SHOOT_B;
                }
                break;
            case SHOOT_B:
                shooter.launch();
                if (shooter.doneShooting()) {
                    shooter.stopLauncher();
                    drive.resetEncoders();
                    state = AutoState.OUT_OF_WAY;
                }
                break;
            case OUT_OF_WAY:
                if (drive.drive(-49, DistanceUnit.INCH)) {
                    drive.resetEncoders();
                    state = AutoState.OUT_OF_WAY_B;
                }
                break;
            case OUT_OF_WAY_B:
                degreesToRotate = 135;
            if (drive.rotate(degreesToRotate, AngleUnit.DEGREES)) {
                drive.resetEncoders();
                state = AutoState.OUT_OF_WAY_C;
            }
            break;
            case OUT_OF_WAY_C:
                if (drive.drive(50, DistanceUnit.INCH)) {
                    drive.resetEncoders();
                    state = AutoState.END;
                }
                break;
        }

    }
}