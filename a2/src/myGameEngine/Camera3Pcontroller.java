/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myGameEngine;

import GameSetup.SetUpInputs;
import a2.MyGame;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import ray.input.InputManager;
import ray.input.action.AbstractInputAction;
import ray.input.action.Action;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3;
import ray.rml.Vector3f;

/**
 *
 * @author Joe
 */
public class Camera3Pcontroller {
    private Camera camera;
    private SceneNode cameraN,target;
    private float azimuth,elevation,radius;
    private Vector3 targetPos,worldUpVec;
    String cameraMode = "Snap";
    private MyGame game;
    private Controller controller;
    private InputManager im;
    
    public Camera3Pcontroller(Camera c,SceneNode cn, SceneNode t,Controller controller,InputManager im,MyGame g){
        camera = c;
        game =g;
        this.controller=controller;
        this.im = im;
        cameraN = cn;
        target = t;
        azimuth = 225.0f;
        elevation = 20f;
        radius = 2.0f;
        worldUpVec = Vector3f.createFrom(0.0f,1.0f,0.0f);
        setupInput(im,controller);
        updateCameraPositon();
        
        
    }
    

    public void updateCameraPositon() { 
        double theta = Math.toRadians(azimuth); // rot around target
        double phi = Math.toRadians(elevation); // altitude angle
        double x = radius * Math.cos(phi) * Math.sin(theta);
        double y = radius * Math.sin(phi);
        double z = radius * Math.cos(phi) * Math.cos(theta);
         if(cameraMode.equals("Orbit")){
             Vector3f newpos = (Vector3f)Vector3f.createFrom((float)x, (float)y, (float)z).add(target.getWorldPosition());
            cameraN.setLocalPosition(newpos);
            cameraN.lookAt(target, worldUpVec);
         }
        if(cameraMode.equals("Snap")){
            if(controller.getName().equals(im.getFirstGamepadName())){
                cameraN.setLocalPosition((Vector3f.createFrom(2f, 1f, 0f).add(target.getWorldPosition())));
                cameraN.lookAt(target);
                
            }else{
                cameraN.setLocalPosition((Vector3f.createFrom(-2f, 1f, 0f).add(target.getWorldPosition())));
                cameraN.lookAt(target);
                
            }
            
        }
 }

    private void setupInput(InputManager im, Controller controller) {
        Action orbitAAction = (Action) new OrbitAroundAction();
        if(controller.getName().equals(im.getFirstGamepadName())){
            im.associateAction(controller,
            net.java.games.input.Component.Identifier.Axis.RX, orbitAAction,
            InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(controller,
            net.java.games.input.Component.Identifier.Axis.RY, orbitAAction,
            InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(controller,
            net.java.games.input.Component.Identifier.Axis.Z, orbitAAction,
            InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(controller,
            net.java.games.input.Component.Identifier.Button._1, orbitAAction,
            InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        }else{
            im.associateAction(controller,
            net.java.games.input.Component.Identifier.Key.LEFT, orbitAAction,
            InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(controller,
            net.java.games.input.Component.Identifier.Key.RIGHT, orbitAAction,
            InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(controller,
            net.java.games.input.Component.Identifier.Key.UP, orbitAAction,
            InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(controller,
            net.java.games.input.Component.Identifier.Key.DOWN, orbitAAction,
            InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(controller,
            net.java.games.input.Component.Identifier.Key.E, orbitAAction,
            InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(controller,
            net.java.games.input.Component.Identifier.Key.Q, orbitAAction,
            InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            im.associateAction(controller,
            net.java.games.input.Component.Identifier.Key.F, orbitAAction,
            InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        }
       
// similar input set up for OrbitRadiasAction, OrbitElevationAction
}
    private class OrbitAroundAction extends AbstractInputAction {
        public void cameramode(){
            if(cameraMode.equals("Orbit")){
                cameraMode = "Snap";
            }else
                cameraMode = "Orbit";
        }
        public OrbitAroundAction() {
        }
        
        @Override
        public void performAction(float f, Event event) {
           float rotAmount;
           float eventvalue;
            if(cameraMode.equals("Orbit")){
                if(event.getComponent().toString().equals("X Rotation")||
                        event.getComponent().toString().equals("Left")||event.getComponent().toString().equals("Right")){
                    if(event.getComponent().toString().equals("Right")){
                        eventvalue = event.getValue()*-1;
                    }else
                         eventvalue = event.getValue();
                    if (eventvalue < -0.2){ 
                        rotAmount=-2.0f; 
                     }
                    else{ if (eventvalue > 0.2){ 
                        rotAmount=2.0f; 
                    }
                    else{ 
                        rotAmount=0.0f; }
                    }
                azimuth += rotAmount;
                azimuth = azimuth % 360;
                updateCameraPositon();
                }else if(event.getComponent().toString().equals("Y Rotation")||
                       event.getComponent().toString().equals("Up")||event.getComponent().toString().equals("Down")){
                    if(event.getComponent().toString().equals("Down")){
                        eventvalue = event.getValue()*-1;
                    }else
                        eventvalue = event.getValue();
                    if (eventvalue < -0.2){ 
                        rotAmount=-2.0f; 
                    }
                    else{ if (eventvalue > 0.2){ 
                        rotAmount=2.0f; 
                    }
                    else{ 
                        rotAmount=0.0f; }
                    }
                    float temp = elevation;
                    elevation += rotAmount;
                    elevation = elevation % 360;
                if(elevation>=90.0f||elevation<=-90.0f){
                    elevation = temp;
                }
                updateCameraPositon();
                }else{
                    float zoomAmount;
                    eventvalue=event.getValue();
                    if(event.getComponent().toString().equals("E")){
                        eventvalue = -eventvalue;
                    }
                    if(eventvalue<.2f){
                        zoomAmount= -.1f;
                    }else if(eventvalue>.1f){
                        zoomAmount = .1f;
                    }else
                        zoomAmount =0.0f;
                    float tempz = radius;
                    radius+=zoomAmount;
                    if(radius<=0.0f||radius>=4.0f){
                    radius=tempz;
                    }
                }
                if(event.getComponent().toString().equals("F")||event.getComponent().toString().equals("Button 1")){
                    cameramode();
                }
            }else if(cameraMode.equals("Snap")){
                if(event.getComponent().toString().equals("X Rotation")||
                        event.getComponent().toString().equals("Left")||event.getComponent().toString().equals("Right")){
                    if(event.getComponent().toString().equals("Left")||event.getComponent().toString().equals("X Rotation")){
                        eventvalue = event.getValue()*-1;
                    
                    }else
                         eventvalue = event.getValue();
                    target.setLocalPosition(target.getLocalPosition().add(Vector3f.createFrom(0f,0f,game.getdeltatime()*eventvalue*1f/100f)));
                }
                if(event.getComponent().toString().equals("F")||event.getComponent().toString().equals("Button 1")){
                    cameramode();
                }
            }    
        }
                 
    }
}


