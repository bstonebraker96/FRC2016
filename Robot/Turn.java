package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.JoystickButton;



public class Turn {

	private InitializeRobot robotComponents;
	private IterativeRobot robot;
	private AutoDrive ad;
	
	private Talon leftMotor;
	private Talon rightMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private double c;
	
	public boolean turn(int leftEncoderStart, int rightEncoderStart, double turnAngle, JoystickButton leftStop, JoystickButton rightStop) {
		
		robotComponents = new InitializeRobot();
		robot = new IterativeRobot();
		ad = new AutoDrive();
		
		long nanotime = System.nanoTime();
		long nanotimeOld = nanotime;
		
		double leftDistance;
		double rightDistance;
		double leftRate;
		double rightRate;
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		int leftEncoderOld = leftEncoder.get();
		int rightEncoderOld = rightEncoder.get();
		
		if(!robot.isAutonomous())
		{
			
			leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			
			leftRate = ((leftEncoder.get() - leftEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
			rightRate = ((rightEncoder.get() - rightEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
			
			c = 22.4 * Math.PI * (turnAngle / 360);
			
			while(leftDistance != c || rightDistance != c){
				nanotime = System.nanoTime();
				
				leftMotor.set(.5);
				rightMotor.set(.5);
				
				ad.fixDirection(leftRate, rightRate, true);
				
				if(leftStop.get() || rightStop.get())
				{
					
					leftMotor.set(0);
					rightMotor.set(0);
					return false;
					
				}
				
				nanotimeOld = nanotime;
				leftEncoderOld = leftEncoder.get();
				rightEncoderOld = rightEncoder.get();
				
			}
			
			leftMotor.set(0);
			rightMotor.set(0);
			return true;
		}
		return false;
	}
	public boolean turn(int leftEncoderStart, int rightEncoderStart, double turnAngle, String direction) {
		
		robotComponents = new InitializeRobot();
		robot = new IterativeRobot();
		ad = new AutoDrive();
		
		long nanotime = System.nanoTime();
		long nanotimeOld = nanotime;
		long nanotimeStart = System.nanoTime();
		
		double leftDistance;
		double rightDistance;
		double leftRate;
		double rightRate;
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		int leftEncoderOld = leftEncoder.get();
		int rightEncoderOld = rightEncoder.get();
		
		if(robot.isAutonomous())
		{
			
			leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			
			leftRate = ((leftEncoder.get() - leftEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
			rightRate = ((rightEncoder.get() - rightEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
			
			c = 22.4 * Math.PI * (turnAngle / 360);
			
			while(leftDistance != c || rightDistance != c){
				nanotime = System.nanoTime();
				
				if(direction.equals("counterclockwise"))
				{
					leftMotor.set(.5);
					rightMotor.set(.5);
				}
				else if(direction.equals("clockwise"))
				{
					leftMotor.set(-.5);
					rightMotor.set(-.5);
				}
				ad.fixDirection(leftRate, rightRate, true);
				
				if(nanotime - nanotimeStart >= 5 * Math.pow(10,9))
				{
					leftMotor.set(0);
					rightMotor.set(0);
					return false;
				}
				
				nanotimeOld = nanotime;
				leftEncoderOld = leftEncoder.get();
				rightEncoderOld = rightEncoder.get();
				
			}
			
			leftMotor.set(0);
			rightMotor.set(0);
			return true;
		}
		return false;
	}
	
}
