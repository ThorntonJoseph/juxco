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
/**
 *
 * @author Joe
 */
public class PlayerBuckAction extends AbstractInputAction{
    private PlayerBuckController ps;
    public PlayerBuckAction(PlayerBuckController ps){
        this.ps=ps;
    }
    @Override
    public void performAction(float f, Event event) {
        
        if(event.getComponent().toString().equals(" ")||event.getComponent().toString().equals("Button 0")){
                ps.update();
            }
       
           
     
      
    }
   
}
