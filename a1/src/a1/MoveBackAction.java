/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a1;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;

/**
 *
 * @author Joe
 */
public class MoveBackAction extends AbstractInputAction {
    private Camera camera;
    public MoveBackAction(Camera c){
        camera=c;
    }
    @Override
    public void performAction(float f, Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
