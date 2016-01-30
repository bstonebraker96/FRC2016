package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class TeleopDrive{
	
	public Joystick leftStick;
	public Joystick rightStick;
	
	public Talon leftMotor;
	public Talon rightMotor;
	
	Encoder leftEncoder;
	Encoder rightEncoder;
	
	double leftRate;
	double rightRate;
	double leftDistance = 0;
	double rightDistance = 0;
	double leftReductionFactor;
	double rightReductionFactor;
	
	long nanotime;
	long nanotimeOld;
	int leftEncoderOld;
	int rightEncoderOld;
	
	double leftFactor = 1;
	double rightFactor = 1;
	
	boolean turning;
	
	private JoystickButton bLeft;
	private JoystickButton bRight;
	private JoystickButton turnStopLeft;
	private JoystickButton turnStopRight;
	
	private InitializeRobot robotComponents;
	private Turn turn;
	
	public void driveInit(){
		
		robotComponents = new InitializeRobot();
		turn = new Turn();
		
		leftStick = robotComponents.getLeftJoystick();
		rightStick = robotComponents.getRightJoystick();
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		leftEncoder.reset();
		rightEncoder.reset();
		
		nanotime = System.nanoTime();
		nanotimeOld = nanotime;
		
		rightEncoderOld = rightEncoder.get();
		leftEncoderOld = leftEncoder.get();
		
		bLeft = new JoystickButton(leftStick, 1);
		bRight = new JoystickButton(rightStick, 1);
		turnStopLeft = new JoystickButton(leftStick, 2);
		turnStopRight = new JoystickButton(rightStick, 2);
	}//end of method
	
	
	public void driveBase(){
		
		nanotime = System.nanoTime();
		
		
		leftMotor.set(leftFactor * leftStick.getY());
		rightMotor.set(rightFactor * -1 * rightStick.getY());
		
		leftRate = ((((leftEncoder.get() - leftEncoderOld) / (nanotime - nanotimeOld)) / robotComponents.getCountsPerRevolution()) * 60 * Math.pow(10, 9)); //rate in rpm
		rightRate = ((((rightEncoder.get() - rightEncoderOld) / (nanotime - nanotimeOld)) / robotComponents.getCountsPerRevolution()) * 60 * Math.pow(10, 9)); //rate in rpm
		
		if(leftRate != rightRate && Math.abs(leftStick.getY()- rightStick.getY()) < .01)
		{	
			if(leftRate < rightRate)
			{
				rightFactor = leftRate / 67702.5;
				rightMotor.set(leftRate / 67702.5);
			}
			
			if(rightRate < leftRate)
			{
				leftFactor = rightRate / 67702.5;
				leftMotor.set(rightRate / 67702.5);
			}	
		}
		
		if(bLeft.get() || bRight.get() && turning == false)
		{
			
			turning = true;
			if(turn.turn(leftEncoderOld, rightEncoderOld, 180, turnStopLeft, turnStopRight))
			{
				System.out.println("Turn successful!");
			}
			else{
				System.out.println("[Error] Turn aborted");
			}
			
		}
		
		nanotimeOld = nanotime;
		turning = false;
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
		
	}
}
