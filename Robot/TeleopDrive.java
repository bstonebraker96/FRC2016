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
	
	private boolean turning;
	
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
		
		bLeft = new JoystickButton(leftStick, 1);
		bRight = new JoystickButton(rightStick, 1);
		turnStopLeft = new JoystickButton(leftStick, 2);
		turnStopRight = new JoystickButton(rightStick, 2);
	}//end of method
	
	
	public void driveBase(){
		
		nanotime = System.nanoTime();
		
		
		leftMotor.set(leftFactor * leftStick.getY());
		rightMotor.set(rightFactor * -1 * rightStick.getY());
		leftMotor2.set(leftFactor * leftStick.getY());
		rightMotor2.set(rightFactor * -1 * rightStick.getY());
		
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
