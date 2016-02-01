package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class AutoDrive {
	
	private InitializeRobot robotComponents;
	
	private double leftRate;
	private double rightRate;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private Talon leftMotor;
	private Talon rightMotor;
	private Talon leftMotor2;
	private Talon rightMotor2;
	
	private Gyro gyro;
	
	public void autoDriveInit(){
		
		robotComponents = new InitializeRobot();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		leftMotor2 = robotComponents.getLeftMotor2();
		rightMotor2 = robotComponents.getRightMotor2();
		
		gyro = robotComponents.getGyro();
		
	}
	
	public double fixDirection(double leftRate, double rightRate, boolean turning)
	{
			
			if(turning)
			{
				if(leftRate < rightRate)
				{
					rightMotor.set(leftRate / 67702.5);
					rightMotor2.set(leftRate / 67702.5);
				}
			
				if(rightRate < leftRate)
				{				
					leftMotor.set(rightRate / 67702.5);
					leftMotor2.set(rightRate / 67702.5);
				}
			}
			
			if(!turning)
			{
				if(leftRate < rightRate)
				{
					rightMotor.set(-1 * leftRate / 67702.5);
					rightMotor2.set(-1 * leftRate / 67702.5);
				}
			
				if(rightRate < leftRate)
				{				
					leftMotor.set(rightRate / 67702.5);				
					leftMotor2.set(rightRate / 67702.5);
				}
			}
		return 0;
	}
	
	public int onDefense()
	{
		
		//check this angle
		if(gyro.getAngle() > 5)// Entering defense
		{ 
			System.out.println("OW!");
			System.out.println("Gyro Angle = " + gyro.getAngle());
			return 1;
		}
		
		if(Math.abs(gyro.getAngle()) <= .01)
		//On defense or on ground
		{ 			
			return 0;			
		}
		
		if(gyro.getAngle() < 5)// Leaving defense
		{ 			
			System.out.println("Owie!");
			System.out.println("Gyro Angle = " + gyro.getAngle());
			return 2;			
		}
		
		else
		{
			return 0;
		}
	}//end of method
	
	public int getMode()
	{		
		//code here
		return 0;
	}
}//end of class
