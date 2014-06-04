/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package nerdherd.util;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class NerdyPIDController{

    private double headingTolerance;
    private double error;
    private double heading;
    private double KpAngular = 0.33333;
    private double KiAngular = 0.1;
    private double KdAngular = 1;
    private double angularScaleFactor = 0.03333333;
    private Gyro gyro;
    private double ltPower = 0, rtPower = 0;

            
    public NerdyPIDController(){
        gyro = new Gyro(1);
    }
    
/********************************
Setter / Getter / Logic Functions
*******************************/
    
    
    public void setHeadingTolerance(double degree){
        headingTolerance = degree;
    }
 
    public boolean isHeadingTolerable(double desiredAngle){
        heading = gyro.getAngle();
        return ((desiredAngle < heading+headingTolerance) && (desiredAngle > heading-headingTolerance));
    }
    
    
    private double calcShortestRotation(double desiredAngle){
        heading = gyro.getAngle();
        error = desiredAngle - heading;
        if (Math.abs(error) >  180){
            error = (360 - Math.abs(error));
        }
        return error;
    }   
    
    public double getLtPower(){
        return ltPower;
    }
    
    public double getRtPower(){
        return rtPower;
    }
    
/********************************
PID Functions
*******************************/
    
    public double getPIDOutputAngular(double desiredAngle){

        double err = calcShortestRotation(desiredAngle);
        //double rateCommand = (err * KpAngular) + (HeadingIntegrator.updateAccumulation(err) * KiAngular);
        //double PIDOutputAngular = ((rateCommand - gyroRate) * KdAngular*angularScaleFactor);
        double PIDOutputAngular = ((err * KpAngular) * KdAngular*angularScaleFactor);
   
        PIDOutputAngular = constrain(PIDOutputAngular,1);
        //angularScaleFactor is to normalize the output to -1 - 1
        
        SmartDashboard.putDouble("Angular Error", err);
        return PIDOutputAngular;
    }
    
/********************************
Movement Functions
*******************************/
    
    public void moveAndRotate(double desiredAngle, double linearPower){

        double angularPower = getPIDOutputAngular(desiredAngle);
        //double scaleFactor = 1-Math.abs(angularPower);//scaleFactor is to favor the rotation 
        ltPower = angularPower*0.5 + linearPower*0.5;
        rtPower = angularPower*0.5 - linearPower*0.5;
        
        ltPower = constrain(ltPower, 1);
        rtPower = constrain(rtPower, 1);
        

    }
    
    public void rotate(double angularPower){
        ltPower = angularPower;
        rtPower = angularPower;
    }
    
    public void move(double linearPower){
        ltPower = linearPower;
        rtPower = -linearPower;
    }
        
/********************************
Helper Functions
*******************************/

    private double constrain (double value, double m_limit){
        if(value > m_limit){
            value = m_limit;
        }else if (value < -m_limit){
            value = -m_limit;
        }
        return value; 
    }
    
    public void reset(){
        gyro.reset();
    }
} 
