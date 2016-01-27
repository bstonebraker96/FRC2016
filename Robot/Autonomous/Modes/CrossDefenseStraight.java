package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.interfaces.Gyro;

@SuppressWarnings("unused")
public class CrossDefenseStraight {
	
	private double leftDistance;
	private double rightDistance;
	
	private long nanotime;
	private long nanotimeOld;
	
	private long timeAccumulative;
	
	private PortReference ref;
	private AutoDrive ad;
	
	private String defenseStatus;
	
	public void init(){
		
		nanotimeOld = System.nanoTime();
		
		ref = new PortReference();
		ad = new AutoDrive();
		
		
		ad.getLeftMotor().set(0.25);
		ad.getRightMotor().set(-0.25);
		
	}
	
	public boolean crossDefenseStraight(){
		nanotime = System.nanoTime();
		timeAccumulative = System.nanoTime();
		
		
		leftDistance = ad.getLeftEncoder().get() * ref.getCountsPerRevolution() * 7.65 * Math.PI;
		rightDistance = ad.getRightEncoder().get() * ref.getCountsPerRevolution() * 7.65 * Math.PI;
		
		if(leftDistance != rightDistance)
		{
			ad.fixDirection(nanotime, nanotimeOld, false);
		}
		
		nanotimeOld = nanotime;
		
		if(ad.onDefense() == 1)
		{
			defenseStatus = "Entered";	
		}
		
		
		
		if(ad.onDefense() == 2 && defenseStatus == "Entered")
		{	
			defenseStatus = "Crossed";
		}
		
		if(ad.onDefense() == 0 && defenseStatus == "Crossed")
		{	
			ad.getLeftMotor().set(0);
			ad.getRightMotor().set(0);
			ad.getLeftEncoder().reset();
			ad.getRightEncoder().reset();
			
			ad.getGyro().reset();
			
			return true;
		}
		
		if(timeAccumulative >= 6 * Math.pow(10, 9) && !defenseStatus.equals("Crossed"))
		{
			
			ad.getLeftMotor().set(0);
			ad.getRightMotor().set(0);
			
			return false;
		}
		
		else
		{	
			return false;
		}
	}
}
