/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myGameEngine;

import a2.MyGame;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Degreef;
import ray.rml.Matrix4f;
import ray.rml.Vector3;
import ray.rml.Vector3f;
import ray.rml.Vector4f;

/**
 *
 * @author Joe
 */
    public class PlayerMovementActions extends AbstractInputAction {
        private Camera playerC;
        
        private SceneNode playerD;
        private MyGame game;
        Matrix4f rx,ry,rz;
    public PlayerMovementActions(Camera c,SceneNode dN,MyGame g){
        // matrix creation vectors
        game =g;
        playerC = c;
        playerD = dN;
        rx = (Matrix4f) Matrix4f.createRotationFrom(Degreef.createFrom(1.0f), Vector3f.createUnitVectorX());
        ry = (Matrix4f) Matrix4f.createRotationFrom(Degreef.createFrom(1.0f), Vector3f.createUnitVectorY());
    }
    public static float distance(Vector3f v1, Vector3f v2){
            float x,y,z;
            double r;
            x = v1.x() - v2.x();
            y = v1.y()- v2.y();
            z = v1.z() - v2.z();
            x=x*x;
            y= y*y;
            z=z*z;
            r=x+y+z;
            r = Math.sqrt(r);
            return (float) r; 
    }
   
    @Override
    public void performAction(float f, Event event) {
        // dolphin variables
        Vector3 p,p1,p2;
        // camera variables
        Vector3f cv,cp,cp1,cp2;
        Vector4f ra1,ra2,ra3,r1,r2,r3;
        //player is camera 
        //rotating the dolphin /arrow keys
            if(event.getComponent().toString().equals("A")||event.getComponent().toString().equals("D")
                    ||(event.getComponent().toString().equals("X Axis")&& (event.getValue()>0.2f||event.getValue()<-0.2f))){
                float temp = event.getValue();
                if(event.getComponent().toString().equals("A"))
                    temp = -temp;
                playerD.yaw(Degreef.createFrom(-2f*temp));
        //playerD.rotate(Degreef.createFrom(-2f), playerD.getLocalUpAxis());

        //forward,back,left right movement on dolphin/WASD
            }else if(event.getComponent().toString().equals("W")||event.getComponent().toString().equals("S")||
                    (event.getComponent().toString().equals("Y Axis")&&(event.getValue()>0.2f||event.getValue()<-0.2f))){
                float temp = event.getValue();
                if(event.getComponent().toString().equals("S")||event.getComponent().toString().equals("Y Axis"))
                   temp = -temp;
                p = playerD.getWorldForwardAxis();
                p1 = Vector3f.createFrom(temp* game.getdeltatime()*p.x()/100f,temp*game.getdeltatime()*p.y()/100f,temp*game.getdeltatime()*p.z()/100f);
                p2 = p.add(p1);
                playerD.setLocalPosition(playerD.getWorldPosition().add(p1));
            }
            // creates boundries for dolphins
            
        }
        

    }
    
    

