package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class CrossDefenseStraight {

	private Talon leftMotor;
	private Talon rightMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private double leftDistance;
	private double rightDistance;
	
	private long nanotime;
	private long nanotimeOld;
	
	private PortReference ref;
	private AutoDrive ad;
	
	private String defenseStatus;
	
	public void init(){
		nanotimeOld = System.nanoTime();
		
		ref = new PortReference();
		ad = new AutoDrive();
		
		leftEncoder = new Encoder(ref.getLeftEncoder1(),ref.getLeftEncoder2(),false,EncodingType.k1X);
		rightEncoder = new Encoder(ref.getRightEncoder1(),ref.getRightEncoder2(),false,EncodingType.k1X);
		
		leftMotor = new Talon(ref.getLeftMotor());
		rightMotor = new Talon(ref.getRightMotor());
		
		leftMotor.set(0.25);
		rightMotor.set(0.25);
		
	}
	
	public boolean crossDefenseStraight(){
		nanotime = System.nanoTime();
		
		leftDistance = leftEncoder.get() * ref.getCountsPerRevolution() * 4 * Math.PI;
		rightDistance = rightEncoder.get() * ref.getCountsPerRevolution() * 4 * Math.PI;
		
		if(leftDistance != rightDistance) ad.fixDirection(leftEncoder, rightEncoder, nanotime, nanotimeOld);
		
		nanotimeOld = nanotime;
		
		if(ad.onDefense() == 1){
			
			defenseStatus = "Entered";
			
		}
		
		
		
		if(ad.onDefense() == 2 && defenseStatus == "Entered"){
			
			defenseStatus = "Crossed";
			
			
		}
		
		if(ad.onDefense() == 0 && defenseStatus == "Crossed"){
			
			leftMotor.set(0);
			rightMotor.set(0);
			leftEncoder.reset();
			rightEncoder.reset();
			
			ad.getGyro().reset();
			
			return true;
		}
		
		else{
			return false;
		}
	}
}
