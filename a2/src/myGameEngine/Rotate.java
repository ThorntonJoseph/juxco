/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myGameEngine;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.controllers.RotationController;
import ray.rml.Vector3f;

/**
 *
 * @author Joe
 */
public class Rotate extends AbstractInputAction{

    @Override
    public void performAction(float f, Event event) {
        
    }
    public void handlerotation(int direction){
        //direction 0 = left 1 = right 2 = up 3 = down
        RotationController rc = new RotationController(Vector3f.createUnitVectorX(),-.02f);
        
    }
    
}
