package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class ReachDefense {
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private Talon leftMotor;
	private Talon rightMotor;
	private Talon leftMotor2;
	private Talon rightMotor2;
	
	private long nanotimeOld;
	private long nanotimeStart;
	
	private int leftEncoderOld;
	private int rightEncoderOld;
	
	private double leftRate;
	private double rightRate;
	private double leftDistance;
	private double rightDistance;
	
	InitializeRobot robotComponents = new InitializeRobot();
	AutoDrive ad = new AutoDrive();
	
	public void init(){
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		leftMotor2 = robotComponents.getLeftMotor2();
		rightMotor2 = robotComponents.getRightMotor2();
		
		nanotimeOld = System.nanoTime();
		nanotimeStart = System.nanoTime();
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
		
		leftMotor.set(0.25);
		rightMotor.set(-0.25);
		leftMotor2.set(0.25);
		rightMotor2.set(-0.25);
	}
	public boolean reachDefense(){
		while(true){
			
			leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			
			leftRate = ((leftEncoder.get() - leftEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
			rightRate = ((rightEncoder.get() - rightEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
			if(leftRate != rightRate) ad.fixDirection(leftRate, rightRate, false);
			
			if(leftDistance == 86 || rightDistance == 86)
			{
				
				leftMotor.set(0);
				rightMotor.set(0);
				leftMotor2.set(0);
				rightMotor2.set(0);
				
				leftEncoder.reset();
				rightEncoder.reset();
				
				return true;
			}
			
			if(System.nanoTime() - nanotimeStart >= 6 * Math.pow(10, 9)){
				
				leftMotor.set(0);
				rightMotor.set(0);
				leftMotor2.set(0);
				rightMotor2.set(0);
				
				leftEncoder.reset();
				rightEncoder.reset();
				
				return false;
			}
			
			nanotimeOld = System.nanoTime();
			leftEncoderOld = leftEncoder.get();
			rightEncoderOld = rightEncoder.get();
		}
		
	}
}
