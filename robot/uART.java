package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

//TODO: Consider using query-response pattern. Methods below send query, and then another metho checks for respones.
//TODO: Need to fix lifecycle of responses that causes unecessary long waits or not long enough waits for responses.
public class Uart {
	private SerialPort port;
	public double angle;
	public double distance;
	
	public Uart() {
		//TODO: Initialize the port
	}

	public boolean checkEm()
	{
		port.writeString("TopKek");
		port.flush();
		Timer.delay(.25);

		String stringRec = port.readString();
		if (stringRec.equalsIgnoreCase("topLel"))
		{
			System.out.println("We've got 'er captain!");
			return true;
		}
		else
		{
			System.out.println("We've lost 'er captain!");
			return false;
		}
	}//end of pingPi method
	
	public void updateTargetLocation()
	{
		port.writeString("fire");
		port.flush();
		Timer.delay(.25); //TODO: Investigate if necessary

		String stringRec = port.readString();
		//TODO: Sanatize input

		//TODO: Consider using Scanner class instead

		String distanceString = stringRec.substring(0, 4);
		String angleString = stringRec.substring(4);

		distanceString = distanceString.substring(0, 2) + "." + distanceString.substring(2);
		angleString = angleString.substring(0, 2) + "." + angleString.substring(2);

		angle = Double.parseDouble(angleString);
		distance = Double.parseDouble(distanceString);
	}//end of updateTargetLocation method
	

}
