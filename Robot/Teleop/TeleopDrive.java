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
	
	double leftFactor = 1;
	double rightFactor = 1;
	
	PortReference ref;
	
	public void driveInit(){
		
		ref = new PortReference();
		
		leftStick = new Joystick(ref.getLeftJoystick());
		rightStick = new Joystick(ref.getRightJoystick());
		
		leftMotor = new Talon(ref.getLeftMotor());
		rightMotor = new Talon(ref.getRightMotor());
		
		leftEncoder = new Encoder(ref.getLeftEncoder1(),ref.getLeftEncoder2(),false,EncodingType.k1X);
		rightEncoder = new Encoder(ref.getRightEncoder1(),ref.getRightEncoder2(),false,EncodingType.k1X);
		
		leftEncoder.setSamplesToAverage(5);
		rightEncoder.setSamplesToAverage(5);
		
		nanotime = System.nanoTime();
		nanotimeOld = nanotime;
	}
	
	
	public void driveBase(){
		
		nanotime = System.nanoTime();
		
		leftMotor.set(leftFactor * leftStick.getY());
		rightMotor.set(rightFactor * rightStick.getY());
		
		leftRate = leftEncoder.get() / (nanotime - nanotimeOld); //rate in counts per nanosecond
		rightRate = rightEncoder.get() / (nanotime - nanotimeOld); //rate in counts per nanosecond
		
		leftRate *= Math.pow(10, 9) * 60;
		rightRate *= Math.pow(10, 9) * 60;
		
		leftRate /= ref.getCountsPerRevolution();
		rightRate /= ref.getCountsPerRevolution();
		
		if(leftRate != rightRate && Math.abs(leftStick.getY()- rightStick.getY()) < .01){
			
			if(leftRate < rightRate){
				
				rightFactor = leftRate / 67702.5;
				rightMotor.set(leftRate / 67702.5);
				
			}
			
			if(rightRate < leftRate){
				
				leftFactor = rightRate / 67702.5;
				leftMotor.set(rightRate / 67702.5);
				
			}
			
		}
		
		nanotimeOld = nanotime;
		leftEncoder.reset();
		rightEncoder.reset();
		
	}
	
}
