package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class AutoDriveBase {
	
	private InitializeRobot robotComponents;

	private DigitalInput driveSwitch;
	private DigitalInput shootSwitch;
	private DigitalInput defenseSwitch1;
	private DigitalInput defenseSwitch2;
	private DigitalInput defenseSwitch3;
	
	private Victor leftMotor;
	private Victor rightMotor;
	
	private Gyro gyro;
	
	public void autoDriveInit(){
		
		robotComponents = InitializeRobot.GetInstance();
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		
		driveSwitch = robotComponents.getDriveSwitch();
		shootSwitch = robotComponents.getShootSwitch();
		defenseSwitch1 = robotComponents.getModeSwitch1();
		defenseSwitch2 = robotComponents.getModeSwitch2();
		defenseSwitch3 = robotComponents.getModeSwitch3();
		
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
		if(driveSwitch.get() && shootSwitch.get())
		{
			return 2;
		}
		
		if(driveSwitch.get() && !shootSwitch.get())
		{
			return 1;
		}
		
		if(!driveSwitch.get() && !shootSwitch.get())
		{
			return 0;
		}
		
		else{
			return 0;
		}
	}
	public int getDefenseToCross(){
		
		if(defenseSwitch1.get())
		{
			return 1;
		}
		if(defenseSwitch2.get())
		{
			return 2;
		}
		if(defenseSwitch3.get())
		{
			return 3;
		}
		if(defenseSwitch1.get() && defenseSwitch2.get() && defenseSwitch3.get())
		{
			return 4;
		}
		if(!defenseSwitch1.get() && !defenseSwitch2.get() && !defenseSwitch3.get())
		{
			return 5;
		}
		return 0;
	}
}//end of class
