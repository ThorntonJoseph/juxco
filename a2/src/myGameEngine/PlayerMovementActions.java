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
        private boolean enabled = true;
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
   public void setenabled(boolean enable){
       enabled = enable;
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
        
            if(((event.getComponent().toString().equals("A")||event.getComponent().toString().equals("D")
                    ||event.getComponent().toString().equals("X Axis")&& (event.getValue()>0.2f||event.getValue()<-0.2f))&&enabled==true)){
                float temp = event.getValue();
                if(event.getComponent().toString().equals("A"))
                    temp = -temp;
                playerD.rotate(Degreef.createFrom(-2f*temp),Vector3f.createFrom(0f,1f,0f));
        //playerD.rotate(Degreef.createFrom(-2f), playerD.getLocalUpAxis());

        //forward,back,left right movement on dolphin/WASD
            }else if(event.getComponent().toString().equals("W")||event.getComponent().toString().equals("S")||
                    (event.getComponent().toString().equals("Y Axis")&&(event.getValue()>0.2f||event.getValue()<-0.2f))){
                float temp = event.getValue();
                if(event.getComponent().toString().equals("S")||event.getComponent().toString().equals("Y Axis"))
                   temp = -temp;
                p = playerD.getWorldForwardAxis();
                p1 = Vector3f.createFrom(temp* game.getdeltatime()*p.x()/100f,0.0f,temp*game.getdeltatime()*p.z()/100f);
                p2 = p.add(p1);
                playerD.setLocalPosition(playerD.getWorldPosition().add(p1));
            }
            if(playerD.getLocalPosition().x()<=-24f && (event.getComponent().toString().equals("W")||event.getComponent().toString().equals("S"))){
                playerD.setLocalPosition(-24f,playerD.getLocalPosition().y(),playerD.getLocalPosition().z());
            }else if(playerD.getLocalPosition().x()>=-1f && (event.getComponent().toString().equals("W")||event.getComponent().toString().equals("S"))){
                playerD.setLocalPosition(-1f,playerD.getLocalPosition().y(),playerD.getLocalPosition().z());
            }
            if(playerD.getLocalPosition().x()>=24f && event.getComponent().toString().equals("Y Axis")){
                playerD.setLocalPosition(24f,playerD.getLocalPosition().y(),playerD.getLocalPosition().z());
            }else if(playerD.getLocalPosition().x()<=1f && event.getComponent().toString().equals("Y Axis")){
                playerD.setLocalPosition(1f,playerD.getLocalPosition().y(),playerD.getLocalPosition().z());
            }
            if(playerD.getLocalPosition().z()>=14f){
                playerD.setLocalPosition(playerD.getLocalPosition().x(),playerD.getLocalPosition().y(),14f);
            }else if(playerD.getLocalPosition().z()<=-14f){
                playerD.setLocalPosition(playerD.getLocalPosition().x(),playerD.getLocalPosition().y(),-14f);
            }
        
        }
    

    }
    
    

