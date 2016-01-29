package org.usfirst.frc.team5968.robot;

/* USB Ports
 * 0 - left joy stick
 * 1 - right joy stick
 * 2 - alt joy stick
 */

/* PWM Ports
 * 0 - left drive motor
 * 1 - right drive motor
 * 2 - feed motor
 * 3 - left shoot motor
 * 4 - right shoot motor
 */

/* DIO Ports
 * 0 - left encoder1
 * 1 - left encoder2
 * 2 - right encoder1
 * 3 - right encoder2
 */

/* ANALOG IN
 * 0 - ball compressor
 * 1 - platform compressor
 */

public class PortReference {
	
	public int getLeftJoystick()
	{
		return 0;
	}
	
	public int getRightJoystick()
	{
		return 1;
	}
	
	public int getAltJoystick()
	{
		return 2;
	}
	
	public int getLeftMotor()
	{
		return 0;
	}
	
	public int getRightMotor()
	{
		return 1;	
	}
	
	public int getFeedMotor()
	{
		return 2;
	}
	
	public int getLeftEncoder1()
	{
		return 0;
	}
	
	public int getLeftEncoder2()
	{
		return 1;
	}
	
	public int getRightEncoder1()
	{
		return 2;
	}
	
	public int getRightEncoder2()
	{
		return 3;
	}
	
	public int getBallCompressor()
	{
		return 0;
	}
	
	public int getPlatformCompressor()
	{
		return 1;
	}
	
	public int getLeftShootMotor()
	{
		return 3;
	}
	
	public int getRightShootMotor()
	{
		return 4;
	}
	
	public int getCountsPerRevolution()
	{
		 return 2048;
	}
	
}
