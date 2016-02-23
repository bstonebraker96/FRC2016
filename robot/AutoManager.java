package org.usfirst.frc.team5968.robot;


import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
public class AutoManager {
	private ADXRS450_Gyro gyro;
	private DigitalInput driveSwitch;
	private String defenseStatus;
	private int flatSamples = 0;
	private double leftEncoderOld;
	private double rightEncoderOld;
	private double leftRate;
	private double rightRate;
	public Drive drive;
	
	private long nanotimeOld;
	private DigitalInput shootSwitch;
	private DigitalInput defenseSwitch1;
	private DigitalInput defenseSwitch2;
	private DigitalInput defenseSwitch3;
	public AutoManager() {
		gyro = new ADXRS450_Gyro();
		driveSwitch = new DigitalInput(PortMap.driveSwitch); //Digital IN 4
		shootSwitch = new DigitalInput(PortMap.shootSwitch); //Digital IN 5
		defenseSwitch1 = new DigitalInput(PortMap.modeSwitch1); //Digital IN 6
		defenseSwitch2 = new DigitalInput(PortMap.modeSwitch2); //Digital IN 7
		defenseSwitch3 = new DigitalInput(PortMap.modeSwitch3); //Digital IN 8
		drive = new Drive();
	}
	public int onDefense()
	{
		
		//check this angle
		if(gyro.getAngle() > 5)// Entering defense
		{ 
			System.out.println("OW!");
			System.out.println("Gyro Angle = " + gyro.getAngle());
			return 1;
		}
		
		if(Math.abs(gyro.getAngle()) <= .01)
		//On defense or on ground
		{ 			
			return 0;			
		}
		
		if(gyro.getAngle() < 5)// Leaving defense
		{ 			
			System.out.println("Owie!");
			System.out.println("Gyro Angle = " + gyro.getAngle());
			return 2;			
		}
		
		else
		{
			return 0;
		}
	}//end of method
	
	public int getMode()
	{		
		if(driveSwitch.get() && shootSwitch.get())
		{
			return 2;
		}
		
		if(driveSwitch.get() && !shootSwitch.get())
		{
			return 1;
		}
		
		if(!driveSwitch.get() && !shootSwitch.get())
		{
			return 0;
		}
		
		else{
			return 0;
		}
	}
	public int getDefenseToCross(){
		
		if(defenseSwitch1.get())
		{
			return 1;
		}
		if(defenseSwitch2.get())
		{
			return 2;
		}
		if(defenseSwitch3.get())
		{
			return 3;
		}
		if(defenseSwitch1.get() && defenseSwitch2.get() && defenseSwitch3.get())
		{
			return 4;
		}
		if(!defenseSwitch1.get() && !defenseSwitch2.get() && !defenseSwitch3.get())
		{
			return 5;
		}
		return 0;
	}
	public boolean autoDrive(){
		
		leftRate = ((drive.getLeftEncoder() - leftEncoderOld) * PortMap.countsPerRevolution) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		rightRate = ((drive.getRightEncoder() - rightEncoderOld) * PortMap.countsPerRevolution) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
	
	
		if(leftRate != rightRate)
		{
			drive.fixDirection(leftRate, rightRate, false);
		}
		
		if(onDefense() == 1)
		{
			defenseStatus = "Entered";
			System.out.println("We've entered the defense captain!");
			flatSamples = 0;
		}
	
		if(onDefense() == 2 && defenseStatus == "Entered")
		{	
			defenseStatus = "Crossed";
			System.out.println("We've crossed the defense captain!");
			flatSamples = 0;
		}
	
		if(onDefense() == 0 && defenseStatus == "Crossed")
		{	
			flatSamples++;
			if(onDefense() == 0 && flatSamples == 100)
			{
				
				drive.encoderReset();
				System.out.println(defenseStatus);
				System.out.println("Encoders resetting captain");
		
				gyro.reset();
				System.out.println("Gyro's reset captain");
				
				return true;
		
			}
		}
		nanotimeOld = System.nanoTime();
		leftEncoderOld = drive.getLeftEncoder();
		rightEncoderOld = drive.getRightEncoder();
	
		return false;
}//end of method
}
