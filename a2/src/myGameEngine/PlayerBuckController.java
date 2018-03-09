/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myGameEngine;

import myGameEngine.PlayerMovementActions;
import ray.input.action.Action;
import ray.rage.scene.controllers.*;
import ray.rage.scene.Node;
import ray.rml.Degreef;
import ray.rml.Matrix3f;
import ray.rml.Vector3f;



/**
 *
 * @author Joe
 */
public class PlayerBuckController extends AbstractController {
    float rotammount=45f;
    float roatinc = 1.0f;
    float duration1 = 5f;
    float duration2 = 5f;
    float cycle = 5f;
    float cyclen =0;
    private Degreef direction;
    boolean hit = false;
    private Matrix3f finishrot;
    private Action pm;
    public PlayerBuckController(Action pm){
        super();
        this.pm=pm;
    }
    public boolean getenabled(){
        return hit;
    }
    public void update(){
        if(hit==false){
            hit=true;
            cyclen=1;
            duration1 = 0f;
            duration2=0f;
            ((PlayerMovementActions)pm).setenabled(false);
        }
    }
    @Override
    protected void updateImpl(float f) {
        if(hit==true && cyclen != 0){
            if(cyclen==1 && duration1 <= cycle){
                direction = Degreef.createFrom(-5f);
                duration1++;
                if(duration1>=cycle)
                    cyclen=2;
            }else if(cyclen==2 && duration2 <= cycle){
                direction = Degreef.createFrom(5f);
                duration2++;
                if(duration2 >= cycle)
                    cyclen=0;
            }
            for(Node n : super.controlledNodesList){
                    n.rotate(direction, Vector3f.createFrom(1.0f,0.0f,0.0f));
            }
        }else{
            if(hit==true){
                hit = false;
           }
            ((PlayerMovementActions)pm).setenabled(true);
            
        }
        
    }
  
}
