package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Timer;

import java.util.Scanner;

//TODO: Consider using query-response pattern. Methods below send query, and then another method checks for responses.
//TODO: Need to fix life-cycle of responses that causes unnecessary long waits or not long enough waits for responses.
//TODO: Remove this supress warnings box
@SuppressWarnings("unused")
public class Uart {
	private SerialPort port;
	public double angle;
	public double distance;
	
	public Uart() {
		port = new SerialPort(5600, Port.kMXP);
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
	}//end of checkEm method
	
	public void aquireTarget()
	{
		port.writeString("fire");
		port.flush();
		Timer.delay(.25); //TODO: Investigate if necessary

		String stringRec = port.readString();
		//TODO: Sanitize input
		if (stringRec.length() != 8)
		{
			angle = 9.11;
			distance = 420.0;
		}
		else
		{
			String distanceString = stringRec.substring(0, 4);
			String angleString = stringRec.substring(4);

			distanceString = distanceString.substring(0, 2) + "." + distanceString.substring(2);
			angleString = angleString.substring(0, 2) + "." + angleString.substring(2);

			angle = Double.parseDouble(angleString);
			distance = Double.parseDouble(distanceString);
			return;
		}
		
	}//end of aquireTarget method
	

}
