/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myGameEngine;

import a1.MyGame;
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
        if(playerC.getMode()=='c'){
            //roating the camera
            if(event.getComponent().toString().equals("Up")||event.getComponent().toString().equals("Down")
                    ||(event.getComponent().toString().equals("Y Rotation")&& (event.getValue()>0.2f||event.getValue()<-0.2f))){
                    float temp = event.getValue();
                    if(event.getComponent().toString().equals("Down")||event.getComponent().toString().equals("Y Rotation"))
                        temp = -temp;
                    
                rx = (Matrix4f) Matrix4f.createRotationFrom(Degreef.createFrom(2.0f*temp*game.getdeltatime()/10f), playerC.getRt());
                ra1 =(Vector4f) Vector4f.createNormalizedFrom(playerC.getUp());
                r1 = (Vector4f) rx.mult(ra1);
                playerC.setUp((Vector3f) Vector3f.createFrom(r1.x(), r1.y(), r1.z()));
                ra2 = (Vector4f) Vector4f.createNormalizedFrom(playerC.getFd());
                r2 = (Vector4f) rx.mult(ra2);
                playerC.setFd((Vector3f) Vector3f.createFrom(r2.x(), r2.y(), r2.z()));
               
            }else if(event.getComponent().toString().equals("Left")||event.getComponent().toString().equals("Right")
                    ||(event.getComponent().toString().equals("X Rotation")&& (event.getValue()>0.2f||event.getValue()<-0.2f))){
                float temp = event.getValue();
                if(event.getComponent().toString().equals("Right")||event.getComponent().toString().equals("X Rotation"))
                    temp = -temp;
                ry = (Matrix4f) Matrix4f.createRotationFrom(Degreef.createFrom(temp*game.getdeltatime()/10f*2.0f), playerC.getUp());
                ra1 =(Vector4f) Vector4f.createNormalizedFrom(playerC.getFd());
                r1 = (Vector4f) ry.mult(ra1);
                playerC.setFd((Vector3f) Vector3f.createFrom(r1.x(), r1.y(), r1.z()));
                ra2 = (Vector4f) Vector4f.createNormalizedFrom(playerC.getRt());
                r2 = (Vector4f) ry.mult(ra2);
                playerC.setRt((Vector3f) Vector3f.createFrom(r2.x(), r2.y(), r2.z()));
            }
            // forward, backward left right movement/ WASD
            else if(event.getComponent().toString().equals("A")||event.getComponent().toString().equals("D")
                    ||(event.getComponent().toString().equals("X Axis")&&(event.getValue()>0.2f||event.getValue()<-0.2f))){
                float temp = event.getValue();
                if(event.getComponent().toString().equals("A"))
                    temp =-temp;
                cv = playerC.getRt();
                cp = playerC.getPo();
                cp1 = (Vector3f) Vector3f.createFrom(temp * game.getdeltatime()*cv.x()/100f, temp * game.getdeltatime()*cv.y()/100f, temp * game.getdeltatime()*cv.z()/100f);
                cp2 = (Vector3f) cp.add((Vector3)cp1);
                playerC.setPo((Vector3f)Vector3f.createFrom(cp2.x(),cp2.y(),cp2.z()));
            }else if(event.getComponent().toString().equals("W")||event.getComponent().toString().equals("S")||
                    (event.getComponent().toString().equals("Y Axis")&&(event.getValue()>0.2f||event.getValue()<-0.2f))){
                float temp = event.getValue();
                if(event.getComponent().toString().equals("S")||event.getComponent().toString().equals("Y Axis"))
                   temp = -temp;
                cv = playerC.getFd();
                cp = playerC.getPo();
                cp1 = (Vector3f) Vector3f.createFrom((temp*game.getdeltatime()*cv.x())/100f, (temp*game.getdeltatime()*cv.y())/100f, (temp*game.getdeltatime()*cv.z())/100f);
                cp2 = (Vector3f) cp.add((Vector3)cp1);
                playerC.setPo((Vector3f)Vector3f.createFrom(cp2.x(),cp2.y(),cp2.z()));
            }
            // create boundries for movement for camera
            if(playerC.getPo().x()>49.9f){
                playerC.setPo((Vector3f) Vector3f.createFrom(49.9f,playerC.getPo().y(),playerC.getPo().z()));
            }
            if(playerC.getPo().x()< -49.9f){
                playerC.setPo((Vector3f) Vector3f.createFrom(-49.9f,playerC.getPo().y(),playerC.getPo().z()));
            }
            if(playerC.getPo().y()>99.9f){
                playerC.setPo((Vector3f) Vector3f.createFrom(playerC.getPo().x(),99.9f,playerC.getPo().z()));
            }
            if(playerC.getPo().y()<0.1f){
                playerC.setPo((Vector3f) Vector3f.createFrom(playerC.getPo().x(),0.1f,playerC.getPo().z()));
            }
            if(playerC.getPo().z()>49.9f){
                playerC.setPo((Vector3f) Vector3f.createFrom(playerC.getPo().x(),playerC.getPo().y(),49.9f));
            }
            if(playerC.getPo().z()<-49.9f){
                playerC.setPo((Vector3f) Vector3f.createFrom(playerC.getPo().x(),playerC.getPo().y(),-49.9f));
            }
            // keeps player from moving too far away from the dolphin
            Vector3f relloc;
            float dist = distance((Vector3f) playerD.getLocalPosition(),playerC.getPo());
            if( dist >= 5.0f){
                Vector3f temp = (Vector3f) Vector3f.createFrom((playerC.getPo().x()-playerD.getLocalPosition().x())/dist, 
                        (playerC.getPo().y()-playerD.getLocalPosition().y())/dist, (playerC.getPo().z()-playerD.getLocalPosition().z())/dist);
                temp= (Vector3f) Vector3f.createFrom(temp.x()*5.0f, temp.y() *5.0f, temp.z()*5.0f);
              
                playerC.setPo((Vector3f) Vector3f.createFrom(playerD.getLocalPosition().x()+temp.x(), playerD.getLocalPosition().y()+temp.y(), 
                        playerD.getLocalPosition().z()+temp.z()));
               game.setedge(true);
            }else{
               game.setedge(false);
            }
           
        // player is on the dolphin
        }else{
        //rotating the dolphin /arrow keys
            if(event.getComponent().toString().equals("Left")||event.getComponent().toString().equals("Right")
                    ||(event.getComponent().toString().equals("X Rotation")&& (event.getValue()>0.2f||event.getValue()<-0.2f))){
                float temp = event.getValue();
                if(event.getComponent().toString().equals("Left"))
                    temp = -temp;
                playerD.yaw(Degreef.createFrom(-2f*temp));
        //playerD.rotate(Degreef.createFrom(-2f), playerD.getLocalUpAxis());
            }else if(event.getComponent().toString().equals("Up")||event.getComponent().toString().equals("Down")
                    ||(event.getComponent().toString().equals("Y Rotation")&& (event.getValue()>0.2f||event.getValue()<-0.2f))){
                float temp = event.getValue();
                if(event.getComponent().toString().equals("Up"))
                    temp = -temp;
                playerD.pitch(Degreef.createFrom(2f*temp));
            

        //forward,back,left right movement on dolphin/WASD
            }else if(event.getComponent().toString().equals("A")||event.getComponent().toString().equals("D")
                    ||(event.getComponent().toString().equals("X Axis")&&(event.getValue()>0.2f||event.getValue()<-0.2f))){
                float temp = event.getValue();
                if(event.getComponent().toString().equals("A"))
                    temp =-temp;
                p = playerD.getWorldRightAxis();
                p1 = Vector3f.createFrom(temp*game.getdeltatime()*p.x()/100f,temp*game.getdeltatime()*p.y()/100f,temp*game.getdeltatime()*p.z()/100f);
                p2 = p.add(p1);
                playerD.setLocalPosition(playerD.getWorldPosition().add(p1));
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
            if(playerD.getLocalPosition().x()>49.9f){
                playerD.setLocalPosition((Vector3f) Vector3f.createFrom(49.9f,playerD.getLocalPosition().y(),playerD.getLocalPosition().z()));
            }
            if(playerD.getLocalPosition().x()< -49.9f){
                playerD.setLocalPosition((Vector3f) Vector3f.createFrom(-49.9f,playerD.getLocalPosition().y(),playerD.getLocalPosition().z()));
            }
            if(playerD.getLocalPosition().y()>99.9f){
                playerD.setLocalPosition((Vector3f) Vector3f.createFrom(playerD.getLocalPosition().x(),99.9f,playerD.getLocalPosition().z()));
            }
            if(playerD.getLocalPosition().y()<0.1f){
                playerD.setLocalPosition((Vector3f) Vector3f.createFrom(playerD.getLocalPosition().x(),0.1f,playerD.getLocalPosition().z()));
            }
            if(playerD.getLocalPosition().z()>49.9f){
                playerD.setLocalPosition((Vector3f) Vector3f.createFrom(playerD.getLocalPosition().x(),playerD.getLocalPosition().y(),49.9f));
            }
            if(playerD.getLocalPosition().z()<-49.9f){
                playerD.setLocalPosition((Vector3f) Vector3f.createFrom(playerD.getLocalPosition().x(),playerD.getLocalPosition().y(),-49.9f));
            }
        }
        

    }
    public void Movecamera(float howmuch){
        
    }
    public void MoveDolphin(float howmuch){
        
    }
    
}
