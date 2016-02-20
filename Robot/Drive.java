package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class Drive extends BallShoot {
	
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
	
	private double rotationsNeeded, diameter;
	
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
				System.out.println("We've entered the defense captain!");
				flatSamples = 0;
			}
		
			if(ad.onDefense() == 2 && defenseStatus == "Entered")
			{	
				defenseStatus = "Crossed";
				System.out.println("We've crossed the defense captain!");
				flatSamples = 0;
			}
		
			if(ad.onDefense() == 0 && defenseStatus == "Crossed")
			{	
				flatSamples++;
				if(ad.onDefense() == 0 && flatSamples == 100)
				{
					
					leftEncoder.reset();
					rightEncoder.reset();
					System.out.println(defenseStatus);
					System.out.println("Encoders resetting captain");
			
					robotComponents.getGyro().reset();
					System.out.println("Gyro's reset captain");
					
					return true;
			
				}
			}
		
			//This is the fail-safe, but we're not sure exactly what to do right now so it's
			//getting pushed for something later.
			/*if((System.nanoTime() - timeStart)>= (6 * Math.pow(10, 9)) && !defenseStatus.equals("Crossed"))
			{
			
				leftMotor.set(0);
				rightMotor.set(0);
				
				leftEncoder.reset();
				rightEncoder.reset();
				return false;
			}*/
		
		
			nanotimeOld = System.nanoTime();
			leftEncoderOld = leftEncoder.get();
			rightEncoderOld = rightEncoder.get();
		
			return false;
	}//end of method
	
	public void scootUp(double dist)
	{
		rotationsNeeded = dist/(diameter * Math.PI);
		rotationsNeeded = (int)Math.ceil(rotationsNeeded) * 512;
		while (true)
		{
			if (leftEncoder.get() != rotationsNeeded + leftEncoder.get())
			{
				leftMotor.set(.1);
				rightMotor.set(.1);
			}
			else
			{
				leftMotor.set(0);
				rightMotor.set(0);
				return;
			}
		}
	}//end of scootUp
	
	public void scootBack(double dist)
	{
		rotationsNeeded = dist/(diameter * Math.PI);
		rotationsNeeded = (int)Math.ceil(rotationsNeeded) * 512;
		while (true)
		{
			if (leftEncoder.get() != leftEncoder.get() - rotationsNeeded)
			{
				leftMotor.set(-.1);
				rightMotor.set(-.1);
			}
			else
			{
				leftMotor.set(0);
				rightMotor.set(0);
				return;
			}
		}
	}
	/*public void scootAngle(double angle)
	{
		if (angle < angle_MIN)
		{
			
		}
	}*/
	
}
