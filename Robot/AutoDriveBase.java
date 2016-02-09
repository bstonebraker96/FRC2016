package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class AutoDriveBase {
	
	private InitializeRobot robotComponents;
	private DigitalInput dI8;
	private DigitalInput dI9;
	
	private Victor leftMotor;
	private Victor rightMotor;
	
	private Gyro gyro;
	
	public void autoDriveInit(){
		
		robotComponents = InitializeRobot.GetInstance();
		dI8 = new DigitalInput(8);
		dI9 = new DigitalInput(9);
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		
		gyro = robotComponents.getGyro();
		
	}
	
	public double fixDirection(double leftRate, double rightRate, boolean turning)
	{
			
			if(turning)
			{
				if(leftRate < rightRate)
				{
					rightMotor.set(leftRate / 67702.5);
				}
			
				if(rightRate < leftRate)
				{				
					leftMotor.set(rightRate / 67702.5);
				}
			}
			
			if(!turning)
			{
				if(leftRate < rightRate)
				{
					rightMotor.set(-1 * leftRate / 67702.5);
				}
			
				if(rightRate < leftRate)
				{				
					leftMotor.set(rightRate / 67702.5);				
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
		if(dI8.get() && dI9.get())
		{
			return 2;
		}
		
		if(dI8.get() && !dI9.get())
		{
			return 1;
		}
		
		if(!dI8.get() && !dI9.get())
		{
			return 0;
		}
		
		else{
			return 0;
		}
	}
}//end of class
