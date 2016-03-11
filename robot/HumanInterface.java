package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Joystick;

public class HumanInterface {
	private Joystick leftStick;
	private Joystick rightStick;
	private Joystick altStick;
	
	private Drive drive;
	private BallFeed feed;
	private Pneumatics piston;
	private BallShoot shoot;
	
	private boolean controlsReversed = true;
	private boolean altControlsEnabled = false;
	private boolean driving = false;
	
	private boolean oldPistonButtonValue = false;
	private boolean oldReverseButtonValue = false;
	private boolean oldAltControlButtonValue = false;
	private AutoShootManager teleShoot;
	
	public HumanInterface(Drive drive, BallShoot shoot, AutoShootManager teleShoot){
		
		this.drive = drive;
		this.shoot = shoot;
		this.teleShoot = teleShoot;
		
		leftStick = new Joystick(PortMap.leftJoystick);
		rightStick = new Joystick(PortMap.rightJoystick);
		altStick = new Joystick(PortMap.altJoystick);
		feed = new BallFeed();
		piston = new Pneumatics();
	}
	
	public enum BallFeedStates {
		FAST, SLOW, STOPPED
	}
	
	public enum Buttons {
		REVERSE_CONTROLS, ALTERNATE_CONTROLS, TOGGLE_MANUAL_SHOOT, TOGGLE_SHOOT_PLATFORM, SHOOT, FEED_SLOW, FEED_FAST, ALIGN_TO_SHOOT
	}
	
	public boolean getButtonValue(Buttons button) {
		
		switch(button)
		{
			case REVERSE_CONTROLS:
				return leftStick.getRawButton(7);
				
			case ALTERNATE_CONTROLS:
				return altStick.getRawButton(8);
					
			case TOGGLE_MANUAL_SHOOT:
				return altStick.getRawButton(3);
				
			case TOGGLE_SHOOT_PLATFORM:
				return altStick.getRawButton(1);
				
			case SHOOT:
				return altStick.getRawButton(4);
				
			case FEED_SLOW:
				return altStick.getRawButton(10);
			
			case FEED_FAST:
				return altStick.getRawButton(11);
			case ALIGN_TO_SHOOT:
				return altStick.getRawButton(9);
			default:
				return false;
		}
		
	}
	
	public void buttonControls(){
		
		boolean reverseButton = getButtonValue(Buttons.REVERSE_CONTROLS);
		if(reverseButton && !oldReverseButtonValue)
		{
			controlsReversed = !controlsReversed;
		}
		
		oldReverseButtonValue = reverseButton;
		
		boolean altButton = getButtonValue(Buttons.ALTERNATE_CONTROLS);
		if (altButton && !oldAltControlButtonValue)
		{
			altControlsEnabled = !altControlsEnabled;
		}
		
		oldAltControlButtonValue = altButton;
		
		if(getButtonValue(Buttons.TOGGLE_SHOOT_PLATFORM) && !oldPistonButtonValue)
		{
			piston.togglePlatformAngle();
			oldPistonButtonValue = true;
		}
		else if(!getButtonValue(Buttons.TOGGLE_SHOOT_PLATFORM))
		{
			oldPistonButtonValue = false;
		}
		
		
		if(getButtonValue(Buttons.SHOOT))
		{
			shoot.turnOnShooter();
		}
		else 
		{
			shoot.turnOffShooter();
		}
		if(getButtonValue(Buttons.ALIGN_TO_SHOOT) || driving)
		{
			//teleShoot.teleShootComputer();
			
		}

		
		if(getButtonValue(Buttons.FEED_FAST))
		{
			feed.ballFeed(BallFeedStates.FAST);
		}
		else if(getButtonValue(Buttons.FEED_SLOW))
		{
			feed.ballFeed(BallFeedStates.SLOW);
		}
		else
		{
			feed.ballFeed(BallFeedStates.STOPPED);
		}
	}//end of method
	
	public void joystickControls() {
		if(altControlsEnabled)
		{
			
			if(altStick.getX() > 0)
			{
				drive.humanDrive(altStick.getY() - altStick.getX(), altStick.getY());
			}
			
			if(altStick.getX() < 0)
			{
				drive.humanDrive(altStick.getY(), altStick.getY() - altStick.getX());
			}
			
			if(altStick.getX() == 0)
			{
				drive.humanDrive(altStick.getY(), altStick.getY());
			}
		}
		else
		{
			if(controlsReversed)
			{
				drive.humanDrive(Math.pow(-1.0 * leftStick.getY(), 1), Math.pow(rightStick.getY(), 1));
			}
			else
			{
				drive.humanDrive(Math.pow(leftStick.getY(), 1), Math.pow(-1.0 * rightStick.getY(), 1));
			}
		}
	}//end of method
	
	public boolean getControlsReversed() {
		return controlsReversed;
	}
	public boolean getAltControlsEnabled() {
		return altControlsEnabled;
	}
	public boolean getDriving() {
		return driving;
	}
}
