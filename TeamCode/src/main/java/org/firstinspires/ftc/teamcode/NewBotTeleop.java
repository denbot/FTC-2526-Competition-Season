package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class NewBotTeleop extends OpMode {
    private DriveSubsystem drive = new DriveSubsystem(telemetry);

    @Override
    public void init() {

        drive.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }
    @Override
    public void loop() {
        drive.arcadeDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);
        drive.showDriveTelem();

    }
}
