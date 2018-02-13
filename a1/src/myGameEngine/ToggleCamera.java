/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myGameEngine;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import javax.vecmath.*;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;
/**
 *
 * @author Joe
 */
public class ToggleCamera extends AbstractInputAction{
    private Camera camera;
    private SceneNode dolphinN,cameraN;
    public ToggleCamera(Camera c ,SceneNode dN){
        camera=c;
        dolphinN =dN;
    }
    @Override
    public void performAction(float f, Event event) {
        System.out.println(event.getComponent().getName());
       if(camera.getMode()=='c'){
           camera.setMode('n');
       }else{       
           
           camera.setMode('c');
           camera.setPo((Vector3f)Vector3f.createFrom(dolphinN.getLocalPosition().x(), dolphinN.getLocalPosition().y(), dolphinN.getLocalPosition().z()-1.0f));
           camera.setFd((Vector3f)dolphinN.getLocalForwardAxis());
            camera.setRt((Vector3f)dolphinN.getLocalRightAxis().negate());
             camera.setUp((Vector3f)dolphinN.getLocalUpAxis());
       }
           
     
      
    }
   
}
