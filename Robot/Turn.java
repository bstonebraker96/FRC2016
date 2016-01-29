package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;



public class Turn {

	private TeleopDrive td;
	private PortReference ref;
	private AutoDrive ad;

	private JoystickButton leftStop;
	private JoystickButton rightStop;
	
	private double c;
	
	public void turn(int leftEncoderStart, int rightEncoderStart, double turnAngle, JoystickButton leftStop, JoystickButton rightStop) {
		
		td = new TeleopDrive();
		ref = new PortReference();
		ad = new AutoDrive();
		
		long nanotime = System.nanoTime();
		long nanotimeOld = nanotime;
		
		double leftDistance;
		double rightDistance;
		
		IterativeRobot robot = new IterativeRobot();
		
		if(robot.isAutonomous())
		{
			
			leftDistance = ad.getLeftEncoder().get() * ref.getCountsPerRevolution() * 7.65 * Math.PI;
			rightDistance = ad.getRightEncoder().get() * ref.getCountsPerRevolution() * 7.65 * Math.PI;
			
			c = 22.4 * Math.PI * (turnAngle / 360);
			
			while(leftDistance != c || rightDistance != c){
				nanotime = System.nanoTime();
				
				ad.getLeftMotor().set(.5);
				ad.getRightMotor().set(.5);
				
				ad.fixDirection(nanotime, nanotimeOld, true);
				
				nanotimeOld = nanotime;
			}
			
			ad.getLeftMotor().set(0);
			ad.getRightMotor().set(0);
			
		}
		
		else if(!robot.isAutonomous())
		{
			
			leftDistance = td.getLeftEncoder().get() * ref.getCountsPerRevolution() * 7.65 * Math.PI;
			rightDistance = td.getRightEncoder().get() * ref.getCountsPerRevolution() * 7.65 * Math.PI;
			
			c = 22.4 * Math.PI * (turnAngle / 360);
			
			while(leftDistance != c || rightDistance != c){
				nanotime = System.nanoTime();
				
				td.getLeftMotor().set(.5);
				td.getRightMotor().set(.5);
				
				ad.fixDirection(nanotime, nanotimeOld, true);
				
				if(leftStop.get() || rightStop.get())
				{
					
					td.getLeftMotor().set(0);
					td.getRightMotor().set(0);
					break;
					
				}
				
				nanotimeOld = nanotime;
				
			}
			
			td.getLeftMotor().set(0);
			td.getRightMotor().set(0);
			
		}
 
	}
	
}
