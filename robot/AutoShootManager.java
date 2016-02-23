package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Timer;

public class AutoShootManager {
	public double angle_MAX, angle_MIN, distance_MIN, distance_MAX, adjDist;
	private long nanotime, nanotimeOld;
	private double leftRate;
	private double rightRate;
	private Uart uart;
	private AutoManager driveMan;
	
	public AutoShootManager() {
		uart = new Uart();
		driveMan = new AutoManager();
	}
	
	public void ballShootComputer() 
	{
		
		if(uart.checkEm())
		{
			uart.aquireTarget();;
			if (!angleChecker())
			{
				System.out.print("She won't make it captain! Try again!");
			}
			else
			{
				distanceChecker();
				//ballShootHuman();
			}
			
				
		}
		
		else
		{
			//ballShootHuman();
		}
		
	}//end of another method
	public boolean angleChecker()
	{
		if ((uart.angle < angle_MAX) && (uart.angle > angle_MIN))
		{
			System.out.println("Nice angle");
			return true;
		}
		else
		{
			//scooter.scootAngle();
			System.out.println("Angle function not available yet");
			return true;
		}
		
	}
	public void distanceChecker()
	{
		if ((PortMap.distance > distance_MIN) && (PortMap.distance < distance_MAX))
		{
			return; 
		}
		else
		{
			if (PortMap.distance < distance_MIN)
			{
				adjDist = Math.abs(PortMap.distance - distance_MIN);
				driveMan.drive.scootUp(adjDist);
				return;		
			}
			else
			{
				driveMan.drive.scootBack(adjDist);
				return;
			}
		}
	}//end of Distance Checker
	

}
