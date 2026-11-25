package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;

import java.util.function.DoubleSupplier;

/**
 * Basic ShootCommand that spins up the shooter motor to a target velocity.
 * When the shooter motor reaches the target speed (within a specified tolerance),
 * it turns on the transfer motor to feed the shooter.
 */
public class ShootCommand extends CommandBase {
    private final ShooterSubsystem shooterSubsystem;
    private final DoubleSupplier targetSpeedSupplier;
    private final DoubleSupplier transferPowerSupplier;
    private final double speedTolerance;
    /**
     * Constructor using ShooterSubsystem which manages both shooter and transfer motors.
     */
    public ShootCommand(
            ShooterSubsystem shooterSubsystem,
            DoubleSupplier targetSpeedSupplier,
            DoubleSupplier transferPowerSupplier,
            double speedTolerance
    ) {
        this.shooterSubsystem = shooterSubsystem;
        this.targetSpeedSupplier = targetSpeedSupplier;
        this.transferPowerSupplier = transferPowerSupplier;
        this.speedTolerance = speedTolerance;
        addRequirements(shooterSubsystem);
    }

    public ShootCommand(
            ShooterSubsystem shooterSubsystem,
            DoubleSupplier targetSpeedSupplier,
            DoubleSupplier transferPowerSupplier
    ) {
        this(shooterSubsystem, targetSpeedSupplier, transferPowerSupplier, 50.0);
    }

    public ShootCommand(
            ShooterSubsystem shooterSubsystem,
            double targetSpeed,
            double transferPower,
            double speedTolerance
    ) {
        this(shooterSubsystem, () -> targetSpeed, () -> transferPower, speedTolerance);
    }

    public ShootCommand(
            ShooterSubsystem shooterSubsystem,
            double targetSpeed,
            double transferPower
    ) {
        this(shooterSubsystem, () -> targetSpeed, () -> transferPower, 50.0);
    }

    public ShootCommand(
            ShooterSubsystem shooterSubsystem,
            double targetSpeed
    ) {
        this(shooterSubsystem, targetSpeed, 1.0);
    }

    @Override
    public void execute() {
        double targetSpeed = targetSpeedSupplier.getAsDouble();
        shooterSubsystem.setVelocity(targetSpeed);

        double transferPower = transferPowerSupplier.getAsDouble();
        if (shooterSubsystem.isAtTargetVelocity(speedTolerance)) {
            shooterSubsystem.setTransferPower(transferPower);
        } else {
            shooterSubsystem.setTransferPower(0.0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stop();
    }
}
