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
import nerdherd.util.NerdyPIDController;
import nerdherd.util.NerdyTimer;






/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends NerdyBot{
    private CANJaguar ltFt, rtFt, ltBk, rtBk;
    private Joystick joy1;
    private double ltPower  = 0.0, rtPower   = 0.0;
    private NerdyPIDController pidController;
    private NerdyTimer autonomousTimer;
    
                 
    
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
    
    autonomousTimer = new NerdyTimer(3);
    pidController = new NerdyPIDController();
    joy1 = new Joystick(1);
    
    pidController.setHeadingTolerance(5);
    
    }

    public void autonomousInit(){
        autonomousTimer.reset();
        autonomousTimer.start();
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        /*
            Line 1
        */
        pidController.moveAndRotate(5, 0.75);
        ltPower = pidController.getLtPower();
        rtPower  = pidController.getRtPower();
        
        try{
            ltFt.set(ltPower);
            ltBk.set(ltPower);
            rtFt.set(rtPower);
            rtBk.set(rtPower);
        } catch (Exception e){
            System.out.print(e);
        }
        
        /*
            Line 2
        */
//        if(pidController.isHeadingTolerable(5)){
//            pidController.moveAndRotate(5, 0.75);
//        }else{
//            pidController.rotate(pidController.getPIDOutputAngular(5));
//        }
//        
//        ltPower = pidController.getLtPower();
//        rtPower  = pidController.getRtPower();
//        
//        try{
//            ltFt.set(ltPower);
//            ltBk.set(ltPower);
//            rtFt.set(rtPower);
//            rtBk.set(rtPower);
//        } catch (Exception e){
//            System.out.print(e);
//        }
        
        /*
            Square
        */
//        for (int i = 0; i < 4;){
//            pidController.moveAndRotate(90*i, 0.75);
//            ltPower = pidController.getLtPower();
//            rtPower  = pidController.getRtPower();
//            
//            if(autonomousTimer.hasPeriodPassed()){
//                i++;
//            }
//            try{
//                ltFt.set(ltPower);
//                ltBk.set(ltPower);
//                rtFt.set(rtPower);
//                rtBk.set(rtPower);
//            } catch (Exception e){
//                System.out.print(e);
//            }
//        }
        /*
            Set Motors
        */
    }
    
    public void teleopInit(){
        autonomousTimer.stop();
    }
    
    public void teleopContinous() {
        double desiredAngle = (360-joy1.getDirectionDegrees())%360;
        double linearPower  = -sign(joy1.getY()) * Math.abs(joy1.getMagnitude());
        pidController.moveAndRotate(desiredAngle, linearPower);
        ltPower = pidController.getLtPower();
        rtPower  = pidController.getRtPower();
        System.out.println("FIRST: "+ltPower+"\t"+rtPower);
        try{
            ltFt.set(ltPower);
            ltBk.set(ltPower);
            rtFt.set(rtPower);
            rtBk.set(rtPower);
        } catch (Exception e){
            System.out.print(e);
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    private int sign(double number){
        if (number > 0){
            return 1;
        }else if (number < 0){
            return -1;
        } else {
            return 0;
        }
    }
    
}
