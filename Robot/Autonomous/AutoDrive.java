package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class AutoDrive {
	
	private Talon leftMotor;
	private Talon rightMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private double leftRate;
	private double rightRate;
	
	private Gyro gyro;
	
	private PortReference ref;
	
	public void autoDriveInit()
	{
		ref = new PortReference();
		
		leftMotor = new Talon(ref.getLeftMotor());
		rightMotor = new Talon(ref.getRightMotor());
	
		leftEncoder = new Encoder(ref.getLeftEncoder1(),ref.getLeftEncoder2(),false,EncodingType.k1X);
		rightEncoder = new Encoder(ref.getRightEncoder1(),ref.getRightEncoder2(),false,EncodingType.k1X);
		
		//fix analog input
		gyro = new AnalogGyro(1);
		gyro.calibrate();
	}
	
	public double fixDirection(double nanotime, double nanotimeOld)
	{
		
		leftRate = (leftEncoder.get() * ref.getCountsPerRevolution()) / ((nanotime - nanotimeOld) * 60 * Math.pow(10, 9));
		rightRate = (rightEncoder.get() * ref.getCountsPerRevolution()) / ((nanotime - nanotimeOld) * 60 * Math.pow(10, 9));
			
			if(leftRate < rightRate)
			{
				rightMotor.set(leftRate / 67702.5);
			}
			
			if(rightRate < leftRate)
			{				
				leftMotor.set(rightRate / 67702.5);				
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
		
		if(Math.abs(gyro.getAngle()) <= .01 && Math.abs(gyro.getAngle()) >= -.01)
		//On defense or on ground
		{ 			
			return 0;			
		}
		
		if(Math.abs(gyro.getAngle()) < -5)// Leaving defense
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
	
	public Gyro getGyro()
	{	
		System.out.println("Gyro: " + gyro);
		return gyro;		
	}
	
	public Encoder getRightEncoder()
	{	
		System.out.println("Right Encoder: " + rightEncoder);
		return rightEncoder;		
	}
	
	public Encoder getLeftEncoder()
	{		
		System.out.println("Left Encoder: " + leftEncoder);
		return leftEncoder;		
	}
	
	public Talon getLeftMotor()
	{
		System.out.println("Left Motor: " + leftMotor);
		return leftMotor;	
	}
	
	public Talon getRightMotor()
	{	
		System.out.println("Right Motor: " + rightMotor);
		return rightMotor;		
	}
}//end of class
