package bot.den.ftc2526.bonevoyage;

public class Constants {
    public static class Drive {
        public static class Auto {
            public static final double toleranceMM = 10;
            public static final double moveSpeed = 0.5;
            public static final double turnSpeed = 0.2;
            public static final double holdSeconds = 0.5;
        }

        public static final double maxSpeed = 1.0;
    }

    public static class Game {
        public static final int maxArtifacts = 3;
    }

    public static class Intake {
        public static final double outtakePower = -1.0;
        public static final double intakePower = 1.0;
        public static final double stopPower = 0.0;
    }

    public static class Robot {
        public static class ConfigNames {
            public static final String rightDrive = "right_drive";
            public static final String leftDrive = "left_drive";
            public static final String rightFeederServo = "right_feeder";
            public static final String leftFeederServo = "left_feeder";
            public static final String launcher = "launcher";
            public static final String intake = "intake";
            public static final String limelight = "limelight";
        }

        private static final double wheelDiameterMM = 96;
        private static final double encoderTicksPerRev = 537.7;
        public static final double ticksPerMM = encoderTicksPerRev/(wheelDiameterMM * Math.PI);
        public static final double trackWidthMM = 449;
    }

    public static class Shooter {
        public static final double feederStopPower = 0.0;
        public static final double feederRunPower = 1.0;
        public static final double launcherTargetVelocityRpm = 1125;
        public static final double launcherMinVelocityRpm = 1075;
        public static final double launcherStopVelocityRpm = 0.0;
        public static final double feedTimeSeconds = 0.20;
        public static final double launchTimeSeconds = 1.0;
    }
}
