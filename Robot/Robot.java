
package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private Drive drive;
	private AutoManager auto;
	private AutoShootManager autoShootManager;
	private HumanInterface humanInterface;
	private BallShoot shoot;
	
    public void robotInit() {
    	
    	drive = new Drive();
    	shoot = new BallShoot();
    	autoShootManager = new AutoShootManager(drive, shoot);
    	auto = new AutoManager(autoShootManager, drive);
    	humanInterface = new HumanInterface(drive, shoot);
    	
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
