package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class CrossDefenseStraight {
	
	private double leftDistance;
	private double rightDistance;
	private double leftRate;
	private double rightRate;
	
	private long nanotimeOld;
	private long timeStart;
	
	private Talon leftMotor;
	private Talon rightMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	private double leftEncoderOld;
	private double rightEncoderOld;
	
	private String defenseStatus;
	
	private InitializeRobot robotComponents;
	private AutoDrive ad;
	
	public void init(){
		robotComponents = new InitializeRobot();
		ad = new AutoDrive();
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		nanotimeOld = System.nanoTime();
		timeStart = System.nanoTime();
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
		
		leftMotor.set(0.25);
		rightMotor.set(-0.25);
		
	}
	
	public boolean crossDefenseStraight(){
		while(true)
		{
					
			leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			
			leftRate = ((leftEncoder.get() - leftEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
			rightRate = ((rightEncoder.get() - rightEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
		
			if(leftRate != rightRate) ad.fixDirection(leftRate, rightRate, false);
			
			if(ad.onDefense() == 1)
			{
				defenseStatus = "Entered";	
			}
		
			if(ad.onDefense() == 2 && defenseStatus == "Entered")
			{	
				defenseStatus = "Crossed";
			}
		
			if(ad.onDefense() == 0 && defenseStatus == "Crossed")
			{	
			
				try {
					Thread.sleep(250);
				} 
				catch (InterruptedException e) {
					System.out.println("InterruptedException");
				}
			
				if(ad.onDefense() == 0)
				{
					leftMotor.set(0);
					rightMotor.set(0);
					leftEncoder.reset();
					rightEncoder.reset();
			
					robotComponents.getGyro().reset();
			
					return true;
				}
			}
		
			if((System.nanoTime() - timeStart)>= (6 * Math.pow(10, 9)) && !defenseStatus.equals("Crossed"))
			{
			
				leftMotor.set(0);
				rightMotor.set(0);
			
				return false;
			}
		
		
			nanotimeOld = System.nanoTime();
			leftEncoderOld = leftEncoder.get();
			rightEncoderOld = rightEncoder.get();
		
		}
	}
}
