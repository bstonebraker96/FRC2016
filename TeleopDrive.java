package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class TeleopDrive {
	
	public Joystick leftStick;
	public Joystick rightStick;
	
	public Talon leftMotor;
	public Talon rightMotor;
	
	Encoder leftEncoder;
	Encoder rightEncoder;
	
	double leftRate;
	double rightRate;
	double leftReductionFactor;
	double rightReductionFactor;
	
	long nanotime;
	long nanotimeOld;
	
	public void driveInit(){
		
		leftStick = new Joystick(1);
		rightStick = new Joystick(2);
		
		leftMotor = new Talon(1);
		rightMotor = new Talon(2);
		
		leftEncoder = new Encoder(1,2,false,EncodingType.k1X);
		rightEncoder = new Encoder(3,4,false,EncodingType.k1X);
		
		leftEncoder.setSamplesToAverage(5);
		rightEncoder.setSamplesToAverage(5);
		
		nanotime = System.nanoTime();
		nanotimeOld = nanotime;
	}
	
	
	public void driveBase(){
		
		nanotime = System.nanoTime();
		
		leftMotor.set(leftStick.getY());
		rightMotor.set(leftStick.getY());
		
		leftRate = leftEncoder.get() / (nanotime - nanotimeOld);
		rightRate = rightEncoder.get() / (nanotime - nanotimeOld);
		
		if(leftRate != rightRate && Math.abs(leftStick.getY()- rightStick.getY()) < .01){
			
			if(leftRate < rightRate){
				
				//Fix max rate (2400)
				rightMotor.set(leftRate / 2400);
				
			}
			
			if(rightRate < leftRate){
				
				//Fix max rate (2400)
				leftMotor.set(leftRate / 2400);
				
			}
			
		}
		
		nanotimeOld = nanotime;
		
	}
	
}
