package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class CrossDefenseLeft {
	
	private double leftDistance;
	private double rightDistance;
	
	private Talon leftMotor;
	private Talon rightMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private long nanotime;
	private long nanotimeOld;
	private long timeAccumulative;
	
	private InitializeRobot robotComponents;
	private AutoDrive ad;
	
	public void init(){
		
		robotComponents = new InitializeRobot();
		ad = new AutoDrive();
		
		nanotimeOld = System.nanoTime();
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		leftMotor.set(0.25);
		rightMotor.set(0.25);
		
	}
	
	public void crossDefenseLeft(){
		
		nanotime = System.nanoTime();
		timeAccumulative = System.nanoTime();
		
		leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
		rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
		
		if(leftDistance != rightDistance) ad.fixDirection(nanotime, nanotimeOld, false);
		
		
		
	}
}
