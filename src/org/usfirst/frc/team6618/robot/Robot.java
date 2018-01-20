/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6618.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	// check names or brand of motor controllers
	VictorSP backLeft = new VictorSP(0);
	VictorSP frontLeft = new VictorSP(1);
	VictorSP backRight = new VictorSP(2);
	VictorSP frontRight = new VictorSP(3);
	Spark suckerLeft = new Spark(4);
	Spark suckerRight = new Spark(5);
	Spark coolLift = new Spark(6);

	// DifferentialDrive driveTrain1 = new DifferentialDrive(frontLeft, frontRight);
	// DifferentialDrive driveTrain = new DifferentialDrive(backLeft, backRight);

	SpeedControllerGroup m_left = new SpeedControllerGroup(backLeft, frontLeft);
	SpeedControllerGroup m_right = new SpeedControllerGroup(backRight, frontRight);

	DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

	XboxController driveController = new XboxController(0);
	XboxController operatorController = new XboxController(1);

	Timer timer = new Timer();
	String[] theArrayMyDude = new String[] { "left", "straight", "right" };

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		SmartDashboard.putStringArray("Auto List", theArrayMyDude);
		CameraServer.getInstance().startAutomaticCapture();
	}

	@Override
	public void autonomousInit() {
		timer.start();
		String theString = SmartDashboard.getString("Auto Selector", "straight");
		System.out.println(theString + "!");
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		String theString = SmartDashboard.getString("Auto Selector", "straight");
		if (timer.get() <= 5) {
			m_drive.arcadeDrive(0.5, 0);
		} else if (timer.get() > 5 && timer.get() < 7) {
			m_drive.arcadeDrive(0.6, 0.5);
		}
	}

	/**
	 * Supersize me This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		// Run Drive Train
		// driveTrain1.arcadeDrive(driveController.getRawAxis(1),
		// driveController.getRawAxis(4));
		// driveTrain.arcadeDrive(driveController.getRawAxis(2),
		// driveController.getRawAxis(3));
											//foward packwards          //left right
		m_drive.arcadeDrive(driveController.getRawAxis(1), driveController.getRawAxis(4));

		if (operatorController.getAButton()) {
			suckerLeft.set(-0.5);
			suckerRight.set(-0.5);
		} else if (operatorController.getBButton()) {
			suckerLeft.set(0.5);
			suckerRight.set(0.5);
		} else {
			suckerLeft.set(0);
			suckerRight.set(0);
		}

		if (operatorController.getXButton()) {
			coolLift.set(0.7);
		} else if (operatorController.getYButton()) {
			coolLift.set(-0.7);
		} else {
			coolLift.set(0);
		}

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}
}
// bbm pins place here:
// Chad is bad
// Michigan will beat state