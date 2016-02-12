package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;



public class Turn {

	private InitializeRobot robotComponents;
	private AutoDriveBase ad;
	
	private Victor leftMotor;
	private Victor rightMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	private int leftEncoderOld;
	private int rightEncoderOld;
	
	private long nanotimeOld;
	private long nanotimeStart;
	
	private double leftDistance;
	private double rightDistance;
	private double leftRate;
	private double rightRate;
	
	
	private boolean turnInitialized = false;
	
	private double c;
	
	
	
	
	/*public boolean turn(int leftEncoderStart, int rightEncoderStart, double turnAngle, JoystickButton leftStop, JoystickButton rightStop) {
		
		robotComponents = InitializeRobot.GetInstance();
		robot = new IterativeRobot();
		ad = new AutoDriveBase();
		
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
	}*/
	private void turnInit(){
		long nanotimeOld = System.nanoTime();
		long nanotimeStart = System.nanoTime();
		
		robotComponents = InitializeRobot.GetInstance();
		ad = new AutoDriveBase();
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
	}
	
	public boolean turn(double turnAngle, String direction) {
		
		if(!turnInitialized){
			turnInit();
		}
			
		leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
		rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
			
		leftRate = ((leftEncoder.get() - leftEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		rightRate = ((rightEncoder.get() - rightEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
		c = 22.4 * Math.PI * (turnAngle / 360);
			
		if(leftDistance - c >= .1 || rightDistance - c >= .1)
		{
				
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
				
			if(System.nanoTime() - nanotimeStart >= 5 * Math.pow(10,9))
			{
				leftMotor.set(0);
				rightMotor.set(0);
					
				return false;
			}
				
			nanotimeOld = System.nanoTime();
			leftEncoderOld = leftEncoder.get();
			rightEncoderOld = rightEncoder.get();
		
		}
		if(leftDistance - c <= .1 || rightDistance - c <= .1)
		{
			leftMotor.set(0);
			rightMotor.set(0);
			
			return true;
		}
		return false;
	}
}