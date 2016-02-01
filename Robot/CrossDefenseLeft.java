package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class CrossDefenseLeft {
	
	private double leftDistance;
	private double rightDistance;
	private double leftRate;
	private double rightRate;
	
	private Talon leftMotor;
	private Talon rightMotor;
	private Talon leftMotor2;
	private Talon rightMotor2;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	private int leftEncoderTurn;
	private int rightEncoderTurn;
	private int rightEncoderOld;
	private int leftEncoderOld;
	
	private String defenseStatus;
	
	private boolean turnComplete = false;
	
	private long nanotimeOld;
	private long timeStart;
	
	
	private InitializeRobot robotComponents;
	private AutoDrive ad;
	private Turn turn;
	
	public void init(){
		
		robotComponents = new InitializeRobot();
		ad = new AutoDrive();
		turn = new Turn();
		
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		leftMotor2 = robotComponents.getLeftMotor2();
		rightMotor2 = robotComponents.getRightMotor2();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		nanotimeOld = System.nanoTime();
		timeStart = System.nanoTime();
		
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
		
		leftMotor.set(0.25);
		rightMotor.set(-0.25);
		leftMotor2.set(0.25);
		rightMotor2.set(-0.25);
		
	}
	
	public boolean crossDefenseLeft(){
		
		while(true){
		
			leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
		
			leftRate = ((leftEncoder.get() - leftEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
			rightRate = ((rightEncoder.get() - rightEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
			if(leftRate != rightRate) ad.fixDirection(leftRate, rightRate, false);
		
			if(leftDistance == 50 || rightDistance == 50)
			{
				leftEncoderTurn = leftEncoder.get();
				rightEncoderTurn = rightEncoder.get();
			
				if(!turnComplete)
				{
					if(turn.turn(leftEncoderTurn, rightEncoderTurn, 90, "clockwise"))
					{
						System.out.println("Turn successful! Crossing defense...");
						turnComplete = true;
						
						leftEncoder.reset();
						rightEncoder.reset();
					}
					else
					{
						System.out.println("[Error] Turn failed. Emergency stop.");
						return false;
					}
				
				}
			
			}
		
			if(turnComplete)
			{
				leftMotor.set(.25);
				rightMotor.set(-.25);
				leftMotor2.set(.25);
				rightMotor2.set(-.25);
				
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
					} 
					catch (InterruptedException e) {
						System.out.println("InterruptedException");
					}
				
					if(ad.onDefense() == 0)
					{
						leftMotor.set(0);
						rightMotor.set(0);
						leftMotor2.set(0);
						rightMotor2.set(0);
						
						leftEncoder.reset();
						rightEncoder.reset();
				
						robotComponents.getGyro().reset();
				
						return true;
					}
				}
			
				if((System.nanoTime() - timeStart)>= (6 * Math.pow(10, 9)) && !defenseStatus.equals("Crossed"))
				{
				
					leftMotor.set(0);
					rightMotor.set(0);
					leftMotor2.set(0);
					rightMotor2.set(0);
				
					return false;
				}
			
			}
			nanotimeOld = System.nanoTime();
			leftEncoderOld = leftEncoder.get();
			rightEncoderOld = rightEncoder.get();
		}
	}
}
