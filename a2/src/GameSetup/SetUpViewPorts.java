/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSetup;

import java.awt.Color;
import ray.input.InputManager;
import ray.rage.rendersystem.RenderWindow;
import ray.rage.rendersystem.Viewport;

/**
 *
 * @author Joe
 */
public class SetUpViewPorts {
    private RenderWindow rw;
    public SetUpViewPorts(RenderWindow rw,InputManager im){
        this.rw=rw;
        setupWindowViewPorts(rw,im);
    }
    
    protected void setupWindowViewPorts(RenderWindow rw,InputManager im){
        if(im.getFirstGamepadName()!= null){
            Viewport p1View = rw.getViewport(0);
            p1View.setDimensions(.51f,.01f,.99f,.49f);
            Viewport p2View = rw.createViewport(.01f, .01f,.99f,.49f);
        }
        
        }
        
       
    }
    

