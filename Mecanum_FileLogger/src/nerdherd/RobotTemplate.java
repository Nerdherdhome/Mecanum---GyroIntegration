/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package nerdherd;

import nerdherd.util.NerdyBot;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;






/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends NerdyBot{
    CANJaguar ltFt, rtFt, ltBk, rtBk;
    Joystick Joy1, Joy2;

    double ltFront  = 0.0,
           ltBack   = 0.0,
           rtFront  = 0.0,
           rtBack   = 0.0,
           forward  = 0.0,
           strafe   = 0.0,
           turn     = 0.0,
           inval    = 0.0,
           maxLimit = 0.0,
           minLimit = 0.0;
           
           
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    
    try{
    
     ltFt = new CANJaguar(3);
     rtFt = new CANJaguar(4);
     ltBk = new CANJaguar(2);
     rtBk = new CANJaguar(5);
    
    }catch (Exception e){
    
    System.out.println(e);
    
    }
    
    Joy1 = new Joystick(1);
    
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }
    /**
     * This function is called periodically during operator control
     * @param inVal
     * @param maxLimit
     * @param minLimit
     * @return 
     */
    private static double constrain(double inVal, double maxLimit, double minLimit){
        if(inVal > maxLimit){
            return maxLimit;
        } else if(inVal < minLimit){
            return minLimit;
        } 
            return inVal;
    }
    
    public void teleopContinous() {
        turn    = 0.0;
        strafe  = 0.0;
        forward = -Joy1.getY();
            if(Joy1.getRawButton(2)){
                strafe = Joy1.getX();
            } else {
                turn   = Joy1.getX();
            }
        ltFront = forward   +  strafe + turn;
        ltBack  = forward   -  strafe + turn;
        rtFront = -forward  +  strafe + turn;
        rtBack  = -forward  -  strafe + turn;
        System.out.println("FIRST: "+ltFront+"\t"+ltBack+"\t"+rtFront+"\t"+rtBack+"\t");
        ltFront = constrain(ltFront, 1.0, -1.0);
        ltBack = constrain(ltBack, 1.0, -1.0);
        rtFront = constrain(rtFront, 1.0, -1.0);
        rtBack = constrain(rtBack, 1.0, -1.0);
        System.out.println("SECOND: "+ltFront+"\t"+ltBack+"\t"+rtFront+"\t"+rtBack+"\t");
        
        try{
        ltFt.set(ltFront);
        ltBk.set(ltBack);
        rtFt.set(rtFront);
        rtBk.set(rtBack);
        } catch (Exception e){
            System.out.print(e);
        }
      
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
