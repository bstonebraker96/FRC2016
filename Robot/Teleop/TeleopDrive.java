package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
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
	
	PortReference ref;
	Turn turn;
	
	public void driveInit(){
		
		ref = new PortReference();
		turn = new Turn();
		
		leftStick = new Joystick(ref.getLeftJoystick());
		rightStick = new Joystick(ref.getRightJoystick());
		
		leftMotor = new Talon(ref.getLeftMotor());
		rightMotor = new Talon(ref.getRightMotor());
		
		leftEncoder = new Encoder(ref.getLeftEncoder1(),ref.getLeftEncoder2(),false,EncodingType.k1X);
		rightEncoder = new Encoder(ref.getRightEncoder1(),ref.getRightEncoder2(),false,EncodingType.k1X);
		
		leftEncoder.setSamplesToAverage(5);
		rightEncoder.setSamplesToAverage(5);
		
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
		
		leftRate = ((((leftEncoder.get() - leftEncoderOld) / (nanotime - nanotimeOld)) / ref.getCountsPerRevolution()) * 60 * Math.pow(10, 9)); //rate in rpm
		rightRate = ((((rightEncoder.get() - rightEncoderOld) / (nanotime - nanotimeOld)) / ref.getCountsPerRevolution()) * 60 * Math.pow(10, 9)); //rate in rpm
		
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
			turn.turn(leftEncoderOld, rightEncoderOld, 180, turnStopLeft, turnStopRight);
			
		}
		
		nanotimeOld = nanotime;
		turning = false;
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
		
	}
	
	public Encoder getLeftEncoder(){
		
		return rightEncoder;
		
	}
	
	public Encoder getRightEncoder(){
		
		return leftEncoder;
		
	}
	
	public Talon getLeftMotor(){
		
		return leftMotor;
		
	}
	
	public Talon getRightMotor(){
		
		return rightMotor;
		
	}
}
