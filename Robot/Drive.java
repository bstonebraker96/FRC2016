package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class Drive {
	
	private double leftRate;
	private double rightRate;
	
	private long nanotimeOld;
	private long timeStart;
	
	private Victor leftMotor;
	private Victor rightMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	private double leftEncoderOld;
	private double rightEncoderOld;
	
	private String defenseStatus;
	
	private int flatSamples = 0;
	
	private InitializeRobot robotComponents;
	private AutoDriveBase ad;
	private BallShoot shoot;
	
	public void init(){
		robotComponents = InitializeRobot.GetInstance();
		ad = new AutoDriveBase();
		
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
	
	public boolean autoDrive(){
			
			leftRate = ((leftEncoder.get() - leftEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
			rightRate = ((rightEncoder.get() - rightEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
		
			if(leftRate != rightRate) ad.fixDirection(leftRate, rightRate, false);
			
			if(ad.onDefense() == 1)
			{
				defenseStatus = "Entered";
				flatSamples = 0;
			}
		
			if(ad.onDefense() == 2 && defenseStatus == "Entered")
			{	
				defenseStatus = "Crossed";
				flatSamples = 0;
			}
		
			if(ad.onDefense() == 0 && defenseStatus == "Crossed")
			{	
				flatSamples++;
				if(ad.onDefense() == 0 && flatSamples == 200)
				{
					
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
				
				leftEncoder.reset();
				rightEncoder.reset();
				return false;
			}
		
		
			nanotimeOld = System.nanoTime();
			leftEncoderOld = leftEncoder.get();
			rightEncoderOld = rightEncoder.get();
		
			return false;
	}
}
