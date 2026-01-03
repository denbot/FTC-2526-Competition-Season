package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "BaseAuto")
public class BaseAuto extends OpMode {
    private enum AutoState{
        PREP,
        SHOOT,
        GO_BACK,
        ROTATE,
        GO_FORWARD,
        END
    }
    private final DriveSubsystem drive = new DriveSubsystem(telemetry);
    private final ShooterSubsystem shooter = new ShooterSubsystem(telemetry);
    private Alliance alliance = Alliance.RED;
    private AutoState state = AutoState.PREP;

    @Override
    public void init(){
        drive.init(hardwareMap);
        drive.resetEncoders();
        shooter.init(hardwareMap);

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
    public void loop(){
        switch (state){
            case PREP:
                shooter.setNumberOfArtifacts(3);
                state = AutoState.SHOOT;
                break;

            case SHOOT:
                shooter.launch();
                if (shooter.doneShooting()){
                    shooter.stopLauncher();
                    drive.resetEncoders();
                    state = AutoState.GO_BACK;
                }
                break;

            case GO_BACK:
                if (drive.drive(-46, DistanceUnit.INCH, 2)){
                    drive.resetEncoders();
                    state = AutoState.ROTATE;

                }
                break;

            case ROTATE:
                double degreesToRotate = 135;
                if (alliance == Alliance.BLUE){
                    degreesToRotate *= -1;
                }
                if (drive.rotate(degreesToRotate, AngleUnit.DEGREES, 2)){
                    drive.resetEncoders();
                    state = AutoState.GO_FORWARD;
                }
                break;

            case GO_FORWARD:
                if (drive.drive(52, DistanceUnit.INCH, 2)){
                    drive.resetEncoders();
                    state = AutoState.END;

                }
                break;
        }
        drive.showDriveTelem();
        shooter.showLauncherTelem();
    }
}
