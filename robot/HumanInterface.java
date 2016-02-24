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
	
	//private boolean reverseControls;
	//private boolean manualShoot;
	//private boolean shootPlatformRaised;	
	//private boolean altControlsEnabled;
	
	//private boolean altControlChecker;
	//private boolean reverseControlChecker;
	//private boolean manualShootChecker;
	//private boolean angleChecker;
	//private boolean ballFeedFastChecker;
	//private boolean ballFeedSlowChecker;
	//private boolean runBallShooter;
	
	public HumanInterface(Drive drive, BallShoot shoot){
		
		this.drive = drive;
		this.shoot = shoot;
			
		leftStick = new Joystick(PortMap.leftJoystick);
		rightStick = new Joystick(PortMap.rightJoystick);
		altStick = new Joystick(PortMap.altJoystick);
		feed = new BallFeed();
		piston = new Pneumatics();
		//REVERSE IS 7 LEFT
		//ALT IS 8 ALT
		//MANUAL SHOOT IS ALT 3
		//PNEUMATICS IS ALT 5
	}
	public void getAllButtons() {
		//altControlChecker = false;//altStick.getRawButton(8);
		//reverseControlChecker = false;//leftStick.getRawButton(7);
		//shootPlatformRaised = ;
		
	}
	
	private boolean IsBallShooterButtonPressed()
	{ return altStick.getRawButton(4); }
	
	private boolean DoManualShoot()
	{ return altStick.getRawButton(3); }
	
	private BallFeedStates GetBallFeedState()
	{
		if (altStick.getRawButton(10))
		{ return BallFeedStates.FAST; }
		else if (altStick.getRawButton(11))
		{ return BallFeedStates.SLOW; }
		else
		{ return BallFeedStates.STOPPED; }
	}
	
	private boolean GetPlatformRaised()
	{ return altStick.getRawButton(2); }
	
	public enum BallFeedStates {
		FAST, SLOW, STOPPED
	}
	
	public void buttonControls(){
		
		getAllButtons();
		
		if(IsBallShooterButtonPressed())
		{
			shoot.turnOnShooter();
		}
		else 
		{
			shoot.turnOffShooter();
		}
		
		if(GetPlatformRaised())
		{
			piston.togglePlatformAngle();
		}
		
		feed.ballFeed(GetBallFeedState());
		
	}//end of method
	
	public void joystickControls() {
		/*if(altControlsEnabled)
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
		else*/
		{
			//if(reverseControls)
			{
				drive.humanDrive(-1.0 * leftStick.getY(), rightStick.getY());
			}
			//else
			//{
			//	drive.humanDrive(leftStick.getY(), -1.0 * rightStick.getY());
			//}
		}
	}//end of method
	

}
