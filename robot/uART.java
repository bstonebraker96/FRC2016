package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class uART {
	
	private SerialPort port;
	private String stringRec;
	private String dis, ang;
	public double angle, distance;
	
	
	public boolean piWriter(String piMode)
	{
		if (piMode.equalsIgnoreCase("check em"))
		{
			port.writeString("TopKek");
		}
		else if (piMode.equalsIgnoreCase("fire mah boulder"))
		{
			port.writeString("fire");
		}
		
		port.flush();
		Timer.delay(.25);
		stringRec = port.readString();
		if (piMode.equalsIgnoreCase("check em"))
		{
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
		}
		else if (piMode.equalsIgnoreCase("fire mah boulder"))
		{
			dis = stringRec.substring(0, 4);
			ang = stringRec.substring(4);
			dis = dis.substring(0, 2) + "." + dis.substring(2);
			ang = ang.substring(0, 2) + "." + ang.substring(2);
			angle = Double.parseDouble(ang);
			distance = Double.parseDouble(dis);
		}
		return true;
	}//end of piWriter method
	
	
	

}
