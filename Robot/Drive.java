package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Drive {
	
	private long nanotimeOld;
	
	private SpeedController leftMotor;
	private SpeedController rightMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	private double leftEncoderOld;
	private double rightEncoderOld;
	
	private static final double diameter = 7.65;
	
	private Gyro gyro;
	
	public Drive(){

		leftMotor = new Victor(PortMap.leftMotor);
		rightMotor = new Victor(PortMap.rightMotor);
		
		leftEncoder = new Encoder(PortMap.leftEncoderOne, PortMap.leftEncoderTwo, false, EncodingType.k1X);
		rightEncoder = new Encoder(PortMap.rightEncoderOne, PortMap.rightEncoderTwo, false, EncodingType.k1X);
		
		
		nanotimeOld = System.nanoTime();
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
		
		gyro = new ADXRS450_Gyro();
		
	}
    
	public boolean driveDistance(double driveDistance, boolean forward){
		
		if(forward){
            //TODO: There is a huge source of error in this class that could cause the right motor to be reversed by mistake.
            //TODO: Instead, a setRawMotors(left, right) should've been used that does the right motor negation for you.
			leftMotor.set(0.25);
			rightMotor.set(-0.25);
		}
		else if(!forward){
			leftMotor.set(-0.25);
			rightMotor.set(0.25);
		}
        
        //TODO: There is an example of what I am talking about below in the file in the root named exampleStateMachineInteractions.cs
        //TODO: I think you guys are doing yourselves a huge disservice with how this works.
        //TODO: Instead, don't even care about the RPMs. Just care about the distance traveled and correct when the distances are different.
        //TODO: The Encoder class has helper methods for getting the distance and resetting the distance it is tracking, this would be easier to keep track of. (Make sure you call Encoder.setDistancePerPulse.)
        //TODO: There is a subtle potential bug where if the robot has been doing a lot of stuff for a while (IE: Not special Drive methods called) that nanotimeOld and other *Old fields will be very out of date, resulting in some strange values.
        //TODO: Consider instead, having a Drive.Process method and a state machine that allows it to do all this stuff, and a pair of methods for starting certain actions and checking if the last action is completed.
        //TODO: !!! There is a bug everywhere this is calculated. You should be dividing nanoseconds be 10e9 to convert nanoseconds to seconds.
        //TODO: Additionally, you should store this value in a constant, rather than using Math.pow
        //TODO: IE: Have a const NANOSECONDS_TO_MINUTES calculation.
		double leftRate = ((leftEncoder.get() - leftEncoderOld) * PortMap.countsPerRevolution) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		double rightRate = ((rightEncoder.get() - rightEncoderOld) * PortMap.countsPerRevolution) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
		double leftDistance = leftEncoder.get() * PortMap.countsPerRevolution * diameter * Math.PI;
		double rightDistance = rightEncoder.get() * PortMap.countsPerRevolution * diameter * Math.PI;
		
		nanotimeOld = System.nanoTime();
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
		
		if(leftRate != rightRate)
		{
			fixDirection(leftRate, rightRate, false);
		}
		if(Math.abs(leftDistance - driveDistance) <= .1 || Math.abs(rightDistance - driveDistance) <= .1){
			
			leftMotor.set(0);
			rightMotor.set(0);
			
			leftEncoder.reset();
			rightEncoder.reset();
			
			return true;
		}
		
		else if(Timer.getMatchTime() >= 10)
		{
			leftMotor.set(0);
			rightMotor.set(0);
			return false;
		}
		
		return false;
	}//end of some method
	
	public enum CrossingStates {
		ENTERED, LEAVING, CROSSED, MISC_RESPONSE
	}
	
	private CrossingStates crossingState = null;
	private int flatDistance = 0;
	private final double flatDistanceToDrive = 59.5;
	
	public CrossingStates driveAcrossDefense(){

		leftMotor.set(0.25);
		rightMotor.set(-0.25);
		
		double leftRate = ((leftEncoder.get() - leftEncoderOld) * PortMap.countsPerRevolution) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		double rightRate = ((rightEncoder.get() - rightEncoderOld) * PortMap.countsPerRevolution) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
		nanotimeOld = System.nanoTime();
		
		if(leftRate != rightRate)
		{
			fixDirection(leftRate, rightRate, false);
		}
		
		if(gyro.getAngle() > 0 && crossingState == null)
		{
			crossingState = CrossingStates.ENTERED;
			flatDistance = 0;
			return CrossingStates.ENTERED;
			
		}
		
		if(gyro.getAngle() < 0 && crossingState == CrossingStates.ENTERED || crossingState == CrossingStates.LEAVING)
		{
			crossingState = CrossingStates.LEAVING;
			flatDistance = 0;
			return CrossingStates.LEAVING;
		}
		
		if(Math.abs(gyro.getAngle()) <= .01 && crossingState == CrossingStates.ENTERED)
		{
			flatDistance += rightEncoder.get() * PortMap.countsPerRevolution * diameter * Math.PI;
			
			if(Math.abs(flatDistance - flatDistanceToDrive) <= .1)
			{
				leftEncoder.reset();
				rightEncoder.reset();
				return CrossingStates.CROSSED;
			}
			
			else{
				return CrossingStates.ENTERED;
			}
		}
		
		else
		{
			flatDistance = 0;
			crossingState = CrossingStates.ENTERED;
			rightEncoder.reset();
			leftEncoder.reset();
			return CrossingStates.ENTERED;
		}

	}//end of method
	
	
	public void scootUp(double dist)
	{
		double rotationsNeeded = dist/(diameter * Math.PI);
		rotationsNeeded = (int)Math.ceil(rotationsNeeded) * 512;
		while (true)
		{
			if (leftEncoder.get() != rotationsNeeded + leftEncoder.get())
			{
				leftMotor.set(.1);
				rightMotor.set(-.1);
			}
			else
			{
				leftMotor.set(0);
				rightMotor.set(0);
				return;
			}
		}
	}//end of scootUp
	
	public void scootBack(double dist)
	{
		double rotationsNeeded = dist/(diameter * Math.PI);
		rotationsNeeded = (int)Math.ceil(rotationsNeeded) * PortMap.countsPerRevolution;
		while (true)
		{
			if (leftEncoder.get() != leftEncoder.get() - rotationsNeeded)
			{
				leftMotor.set(-.1);
				rightMotor.set(.1);
			}
			else
			{
				leftMotor.set(0);
				rightMotor.set(0);
				return;
			}
		}
	}
	public double fixDirection(double leftRate, double rightRate, boolean turning)
	{
        //TODO: !!! I am not 100% sure what these magic numbers are, but this whole method smells fishy to me.
        if(turning)
        {
            if(leftRate < rightRate)
            {
                rightMotor.set(leftRate / 67702.5);
            }
        
            if(rightRate < leftRate)
            {				
                leftMotor.set(rightRate / 67702.5);
            }
        }
        
        if(!turning)
        {
            if(leftRate < rightRate)
            {
                rightMotor.set(-1 * leftRate / 67702.5);
            }
        
            if(rightRate < leftRate)
            {				
                leftMotor.set(rightRate / 67702.5);				
            }
        }
		return 0;
	}
	
	
	public boolean turn(double turnAngle, String direction) {
		
		double leftDistance = leftEncoder.get() * PortMap.countsPerRevolution * 7.65 * Math.PI;
		double rightDistance = rightEncoder.get() * PortMap.countsPerRevolution * 7.65 * Math.PI;
			
		double leftRate = ((leftEncoder.get() - leftEncoderOld) * PortMap.countsPerRevolution) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		double rightRate = ((rightEncoder.get() - rightEncoderOld) * PortMap.countsPerRevolution) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
		double c = 22.4 * Math.PI * (turnAngle / 360);
			
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
			fixDirection(leftRate, rightRate, true);
				
			nanotimeOld = System.nanoTime();
			leftEncoderOld = leftEncoder.get();
			rightEncoderOld = rightEncoder.get();
		
		}
		if(leftDistance - c <= .1 || rightDistance - c <= .1)
		{
			leftMotor.set(0);
			rightMotor.set(0);
			leftEncoder.reset();
			rightEncoder.reset();
			
			return true;
		}
		return false;
	}
	/*public void scootAngle(double angle)
	{
		if (angle < angle_MIN)
		{
			
		}
	}*/
	public void humanDrive(double leftSpeed, double rightSpeed)
	{
		if(Math.abs(leftSpeed) <= .03)
		{
			//leftSpeed = 0.0;
		}
		
		if(Math.abs(rightSpeed) <= .03)
		{
			//rightSpeed = 0.0;
		}
		
		leftMotor.set(leftSpeed);
		rightMotor.set(rightSpeed);
	}
	
	public int getRightEncoder() {
		return rightEncoder.get();
	}
	public int getLeftEncoder() {
		return leftEncoder.get();
	}
	public double getGyro() {
		return gyro.getAngle();
	}
	
	
}
