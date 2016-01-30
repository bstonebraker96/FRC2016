package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.JoystickButton;



public class Turn {

	private InitializeRobot robotComponents;
	private IterativeRobot robot;
	private AutoDrive ad;

	private JoystickButton leftStop;
	private JoystickButton rightStop;
	
	private Talon leftMotor;
	private Talon rightMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private double c;
	
	public void turn(int leftEncoderStart, int rightEncoderStart, double turnAngle, JoystickButton leftStop, JoystickButton rightStop) {
		
		robotComponents = new InitializeRobot();
		robot = new IterativeRobot();
		ad = new AutoDrive();
		
		long nanotime = System.nanoTime();
		long nanotimeOld = nanotime;
		
		double leftDistance;
		double rightDistance;
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		if(robot.isAutonomous())
		{
			
			leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			
			c = 22.4 * Math.PI * (turnAngle / 360);
			
			while(leftDistance != c || rightDistance != c){
				nanotime = System.nanoTime();
				
				leftMotor.set(.5);
				rightMotor.set(.5);
				
				ad.fixDirection(nanotime, nanotimeOld, true);
				
				nanotimeOld = nanotime;
			}
			
			leftMotor.set(0);
			rightMotor.set(0);
			
		}
		
		else if(!robot.isAutonomous())
		{
			
			leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			
			c = 22.4 * Math.PI * (turnAngle / 360);
			
			while(leftDistance != c || rightDistance != c){
				nanotime = System.nanoTime();
				
				leftMotor.set(.5);
				rightMotor.set(.5);
				
				ad.fixDirection(nanotime, nanotimeOld, true);
				
				if(leftStop.get() || rightStop.get())
				{
					
					leftMotor.set(0);
					rightMotor.set(0);
					break;
					
				}
				
				nanotimeOld = nanotime;
				
			}
			
			leftMotor.set(0);
			rightMotor.set(0);
			
		}
 
	}
	
}
