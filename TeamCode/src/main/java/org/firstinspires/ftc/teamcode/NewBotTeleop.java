package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class NewBotTeleop extends OpMode {
    private final DriveSubsystem drive = new DriveSubsystem(telemetry);

    private final IntakeSubsystem intake = new IntakeSubsystem(telemetry);


    @Override
    public void init() {
        intake.init(hardwareMap);
        drive.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }
    @Override
    public void loop() {
        drive.arcadeDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);
        drive.showDriveTelem();
        if (gamepad1.left_trigger > 0) {
            intake.intake();
        } else if (gamepad1.b) { //stop intake
            intake.stopIntake();
        }
    }
}
