// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ControllerAccess;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Targeting;

public class Aim extends Command {
  private SwerveDrive drivetrain;
  private ControllerAccess controllers;
  private Targeting targeting;
  private int whichTarget;//0 speaker, 1 altSpeaker, 2 amp, 3 source left, 4 stage back
  private boolean isAuto;
  private boolean onTheFly;
  private double[] dSticks;

  /** Creates a new Aim. */
  public Aim(int whichTarget , SwerveDrive drivetrain, ControllerAccess controllers , Targeting targeting, boolean isAuto, boolean onTheFly) {
    this.whichTarget = whichTarget;
    this.drivetrain = drivetrain;
    this.controllers = controllers;
    this.targeting = targeting;
    this.isAuto = isAuto;
    this.onTheFly = onTheFly;
    dSticks = new double[3];
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain, controllers);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    targeting.changeTag(whichTarget);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(targeting.getValidTarget() == 0){
      controllers.setAlternate(true);
      controllers.setDriver(1);
      controllers.setOperator(1);
    }
    else if(targeting.calcRange() > 90){
      controllers.setAlternate(false);
      controllers.setDriver(1);
      controllers.setOperator(1);

    }
    if(onTheFly){
      dSticks = controllers.getDriverSticks();
    }
    if(whichTarget == 0){
      if(targeting.getValidTarget() == 0){
        drivetrain.podDriver(dSticks[0], dSticks[1], dSticks[2]);
      }
      else {
        drivetrain.podDriver(dSticks[0], dSticks[1], -(targeting.getX())/50);
      }
    }
    else if(whichTarget == 2){
      if(targeting.getSide()){
        drivetrain.podDriver((targeting.getX())/10, 0, -(90 - drivetrain.getYaw()) / 20.0);
      }
      else{
        drivetrain.podDriver((targeting.getX())/10, 0, -(-90 - drivetrain.getYaw()) / 20.0);
      }
      
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(isAuto){
      return (targeting.calcRange() < 71 && Math.abs(targeting.getX()) < 2) || targeting.getValidTarget() == 0;
    }
    else{
      return false;
    }
    
  }
}
