/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSetup;

import java.awt.Color;
import ray.rage.rendersystem.RenderWindow;
import ray.rage.rendersystem.Viewport;

/**
 *
 * @author Joe
 */
public class SetUpViewPorts {
    public SetUpViewPorts(RenderWindow rw){
        setupWindowViewPorts(rw);
    }
    protected void setupWindowViewPorts(RenderWindow rw){
        Viewport p1View = rw.getViewport(0);
        p1View.setDimensions(.51f,.01f,.99f,.49f);
       
        Viewport p2View = rw.createViewport(.01f, .01f,.99f,.49f);
       
    }
    
}
