/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a1;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rml.Vector3;
import ray.rml.Vector3f;

/**
 *
 * @author Joe
 */
public class MoveForwardAction extends  AbstractInputAction{
    private Camera camera;
    public MoveForwardAction(Camera c){
        camera=c;
    }
    @Override
    public void performAction(float f, Event event) {
       Vector3f v = camera.getFd();
       Vector3f p = camera.getPo();
        Vector3f p1 = (Vector3f) Vector3f.createFrom(0.01f*v.x(), 0.01f*v.y(), 0.01f*v.z());
        Vector3f p2 = (Vector3f) p.add((Vector3)p1);
        camera.setPo((Vector3f)Vector3f.createFrom(p2.x(),p2.y(),p2.z()));
    }
    
}
