package org.usfirst.frc.team5968.robot;

import java.awt.event.ActionListener;

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
	int leftEncoderTurn;
	int rightEncoderTurn;
	
	double leftFactor = 1;
	double rightFactor = 1;
	
	boolean turning;
	
	JoystickButton bLeft;
	JoystickButton bRight;
	
	PortReference ref;
	
	public void driveInit(){
		
		ref = new PortReference();
		
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
	}
	
	
	public void driveBase(){
		
		nanotime = System.nanoTime();
		
		
		leftMotor.set(leftFactor * leftStick.getY());
		rightMotor.set(rightFactor * rightStick.getY());
		
		leftRate = (leftEncoder.get() - leftEncoderOld) / (nanotime - nanotimeOld); //rate in counts per nanosecond
		rightRate = (rightEncoder.get() - rightEncoderOld) / (nanotime - nanotimeOld); //rate in counts per nanosecond
		
		
		leftRate *= Math.pow(10, 9) * 60;
		rightRate *= Math.pow(10, 9) * 60;
		
		leftRate /= ref.getCountsPerRevolution();
		rightRate /= ref.getCountsPerRevolution();
		
		if(leftRate != rightRate && Math.abs(leftStick.getY()- rightStick.getY()) < .01){
			
			if(leftRate < rightRate){
				
				rightFactor = leftRate / 67702.5;
				rightMotor.set(leftRate / 67702.5);
				
			}
			
			if(rightRate < leftRate){
				
				leftFactor = rightRate / 67702.5;
				leftMotor.set(rightRate / 67702.5);
				
			}
			
		}
		
		if(bLeft.get() || bRight.get() && turning == false){
			
			turning = true;
			
			leftEncoderTurn = leftEncoderOld;
			rightEncoderTurn = rightEncoderOld;
			
			if(turn()){
				
				turning = false;
				
			}
			
			else{
				turning = false;
			}
			
		}
		
		nanotimeOld = nanotime;
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
		
	}
	
	private boolean turn(){
		
		leftMotor.set(-0.5);
		rightMotor.set(0.5);
		
		leftDistance = (leftEncoder.get() - leftEncoderTurn) * ref.getCountsPerRevolution() * 7.65 * Math.PI;
		rightDistance = (rightEncoder.get() - rightEncoderTurn) * ref.getCountsPerRevolution() * 7.65 * Math.PI;
		
		while(leftDistance < 11.2 && rightDistance < 11.2) {
			if(leftDistance == 11.2 || rightDistance == 11.2){
				leftMotor.set(0);
				rightMotor.set(0);
			
				return true;
			}
		}
		
	}
	
}
