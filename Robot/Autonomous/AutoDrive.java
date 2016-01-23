package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
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
	
	public void autoDriveInit(){
		ref = new PortReference();
		
		leftMotor = new Talon(ref.getLeftMotor());
		rightMotor = new Talon(ref.getRightMotor());
	
		//fix analog input
		gyro = new AnalogGyro(1);
		gyro.calibrate();
	}
	
	public double fixDirection(Encoder l, Encoder r, double nanotime, double nanotimeOld){
		
		leftRate = (leftEncoder.get() * ref.getCountsPerRevolution()) / ((nanotime - nanotimeOld) * 60 * Math.pow(10, 9));
		rightRate = (rightEncoder.get() * ref.getCountsPerRevolution()) / ((nanotime - nanotimeOld) * 60 * Math.pow(10, 9));
			
			if(leftRate < rightRate){
				
				rightMotor.set(leftRate / 67702.5);
				
			}
			
			if(rightRate < leftRate){
				
				leftMotor.set(rightRate / 67702.5);
				
			}
			
		return 0;
	}
	
	public boolean onDefense(){
		
		//check this angle
		if(gyro.getAngle() > 5){
			
			return true;
			
		}
		
		if(Math.abs(gyro.getAngle()) <= .01){
			
			return false;
			
		}
		
		else{
			
			return false;
			
		}
	}
	
	public int getMode(){
		
		//code here
		return 0;
	}
}
