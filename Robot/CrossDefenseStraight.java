package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class CrossDefenseStraight {
	
	private double leftDistance;
	private double rightDistance;
	
	private long nanotime;
	private long nanotimeOld;
	
	private long timeAccumulative;
	
	private Talon leftMotor;
	private Talon rightMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private String defenseStatus;
	
	private InitializeRobot robotComponents;
	private AutoDrive ad;
	
	public void init(){
		robotComponents = new InitializeRobot();
		ad = new AutoDrive();
		
		nanotimeOld = System.nanoTime();
		
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		leftMotor.set(0.25);
		rightMotor.set(-0.25);
		
	}
	
	public boolean crossDefenseStraight(){
		nanotime = System.nanoTime();
		timeAccumulative = System.nanoTime();
		
		
		leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
		rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
		
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
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				System.out.println("InterruptedException");
			}
			
			if(ad.onDefense() == 2)
			{
				leftMotor.set(0);
				rightMotor.set(0);
				leftEncoder.reset();
				rightEncoder.reset();
			
				robotComponents.getGyro().reset();
			
				return true;
			}
		}
		
		if(timeAccumulative >= 6 * Math.pow(10, 9) && !defenseStatus.equals("Crossed"))
		{
			
			leftMotor.set(0);
			rightMotor.set(0);
			
			return false;
		}
		
		else
		{	
			return false;
		}
	}
}
