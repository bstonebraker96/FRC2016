package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class TeleopDrive{
	
	private Joystick leftStick;
	private Joystick rightStick;
	
	private Talon leftMotor;
	private Talon rightMotor;
	private Talon leftMotor2;
	private Talon rightMotor2;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private double leftRate;
	private double rightRate;
	
	private long nanotime;
	private long nanotimeOld;
	private int leftEncoderOld;
	private int rightEncoderOld;
	
	private double leftFactor = 1;
	private double rightFactor = 1;
	
	private JoystickButton reverseControls;
	private JoystickButton resetControls;
	
	private boolean reversedControls = false;
	
	private InitializeRobot robotComponents;
	
	public void driveInit(){
		
		robotComponents = new InitializeRobot();
		
		leftStick = robotComponents.getLeftJoystick();
		rightStick = robotComponents.getRightJoystick();
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		leftMotor2 = robotComponents.getLeftMotor2();
		rightMotor2 = robotComponents.getRightMotor2();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		leftEncoder.reset();
		rightEncoder.reset();
		
		nanotime = System.nanoTime();
		nanotimeOld = nanotime;
		
		rightEncoderOld = rightEncoder.get();
		leftEncoderOld = leftEncoder.get();
		
		reverseControls = new JoystickButton(leftStick, 1);
		resetControls = new JoystickButton(rightStick, 1);
	}//end of method
	
	
	public void driveBase(){
		
		nanotime = System.nanoTime();
		
		if(!reversedControls)
		{
			
			leftMotor.set(leftFactor * leftStick.getY());
			rightMotor.set(rightFactor * -1 * rightStick.getY());
			leftMotor2.set(leftFactor * leftStick.getY());
			rightMotor2.set(rightFactor * -1 * rightStick.getY());
		
		}
		
		if(reversedControls)
		{
			
			leftMotor.set(leftFactor * -1 * leftStick.getY());
			rightMotor.set(rightFactor * rightStick.getY());
			leftMotor2.set(leftFactor * -1 * leftStick.getY());
			rightMotor2.set(rightFactor * rightStick.getY());
			
		}
		
		leftRate = ((((leftEncoder.get() - leftEncoderOld) / (nanotime - nanotimeOld)) / robotComponents.getCountsPerRevolution()) * 60 * Math.pow(10, 9)); //rate in rpm
		rightRate = ((((rightEncoder.get() - rightEncoderOld) / (nanotime - nanotimeOld)) / robotComponents.getCountsPerRevolution()) * 60 * Math.pow(10, 9)); //rate in rpm
		
		if(leftRate != rightRate && Math.abs(leftStick.getY()- rightStick.getY()) < .01)
		{	
			if(leftRate < rightRate)
			{
				rightFactor = leftRate / 67702.5;
				rightMotor.set(leftRate / 67702.5);
				rightMotor2.set(leftRate / 67702.5);
			}
			
			if(rightRate < leftRate)
			{
				leftFactor = rightRate / 67702.5;
				leftMotor.set(rightRate / 67702.5);
				leftMotor2.set(rightRate / 67702.5);
			}	
		}
		
		if(reverseControls.get())
		{
			
			reversedControls = true;
			
		}
		
		if(resetControls.get())
		{
		
			reversedControls = false;
			
		}
		
		nanotimeOld = nanotime;
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
		
	}
}
