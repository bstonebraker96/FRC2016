
package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private PortMap robot;
	private Drive drive;
	private AutoManager auto;
	private AutoShootManager asm;
	private HumanInterface humanInterface;
	
	int mode;
	
    public void robotInit() {
    	
    	asm = new AutoShootManager();
    	robot = new PortMap();
    	drive = new Drive();
    	auto = new AutoManager(asm);
    	humanInterface = new HumanInterface(asm);
    	
    }
    
    public void autonomousInit() {
    	
    }

   
    public void autonomousPeriodic() {
    	
    	auto.autonomousMain();
    	
    }
    
    public void teleopInit(){
    	
    	
    	
    }
    
    public void teleopPeriodic() {
    	
    	humanInterface.buttonControls();
    	
    	
    	
    }
    
    
    public void testPeriodic() {
    	humanInterface.joystickControls();
    	humanInterface.buttonControls();
    }
    
}
