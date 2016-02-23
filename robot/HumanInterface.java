package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Joystick;

public class HumanInterface {
	private Joystick leftStick;
	private Joystick rightStick;
	private Joystick altStick;
	
	private boolean reverseControls;
	private boolean manualShoot;
	private boolean toggleShootAngle;	
	private boolean altControlsEnabled;
	
	private boolean instanceChecker = false;
	private boolean altControlChecker;
	private boolean reverseControlChecker;
	private boolean manualShootChecker;
	private boolean angleChecker;
	
	public HumanInterface(){
		
		if(!instanceChecker)
		{
			leftStick = new Joystick(PortMap.leftJoystick);
			rightStick = new Joystick(PortMap.rightJoystick);
			altStick = new Joystick(PortMap.altJoystick);
			//REVERSE IS 7 LEFT
			//ALT IS 8 ALT
			//MANUAL SHOOT IS ALT 3
			//PNEUMATICS IS ALT 5
			instanceChecker = true;
		}
	}
	public void getAllButtons() {
		altControlChecker = altStick.getRawButton(8);
		reverseControlChecker = leftStick.getRawButton(7);
		manualShootChecker = altStick.getRawButton(3);
		angleChecker = altStick.getRawButton(5);
		
	}
	
	public void controllerPhase(){
		getAllButtons();
		if(altControlChecker)
		{
			if(altControlsEnabled)
			{
				altControlsEnabled = false;
			}
			else
			{
				altControlsEnabled = true;
			}
		}
		if(reverseControlChecker)
		{
			if(reverseControls)
			{
				reverseControls =  false;
			}
			else
			{
				reverseControls = true;
			}
		}
		if(manualShootChecker)
		{
			if(manualShoot)
			{
				manualShoot = false;
			}
			else
			{
				manualShoot = true;
			}
		}
		if(angleChecker)
		{
			if(toggleShootAngle)
			{
				toggleShootAngle = false;
			}
			else
			{
				toggleShootAngle = true;
			}
		}
	
		
		/*if(altControlsEnabled)
		{
			
			if(altStick.getDirectionRadians() <= Math.PI/2)
			{
				leftMotor.set(0);
				rightMotor.set(altStick.getY());
			}
			else if (altStick.getDirectionRadians() <= Math.PI)
			{
				rightMotor.set(0);
				leftMotor.set(altStick.getY());
			}
			else if (altStick.getDirectionRadians() <= (3*Math.PI)/2)
			{
				leftMotor.set(-altStick.getY());
				rightMotor.set(0);
			}
			else
			{
				leftMotor.set(0);
				rightMotor.set(-altStick.getY());
			}
			System.out.println(leftMotor.get() + " " + rightMotor.get());
		}*/
		
		
		
		
	}//end of method
	

}
