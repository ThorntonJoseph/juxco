/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a1;
import ray.rage.game.*;
import java.io.IOException;
import ray.rage.Engine;
import ray.rage.rendersystem.RenderWindow;
import ray.rage.scene.SceneManager;
import ray.rage.rendersystem.gl4.GL4RenderSystem;
import ray.rage.scene.Camera;
import ray.rage.scene.*;

/**
 *
 * @author Joe
 */
public class MyGame extends VariableFrameRateGame{
    GL4RenderSystem rs;
    float etime;
    String score;
    
    public MyGame(){
        super();
        // moves the camera arround
        System.out.println("Movement keys keys");
        System.out.println("\t press A to move left");
        System.out.println("\t press D to move right");
        System.out.println("\t press S to move backward");
        System.out.println("\t press W to move forward");
        System.out.println("\t press spacebar to get on and off the dolphin");
        System.out.println("Orientation keys");
        System.out.println("\t press left arrow to rotate left arround vertical axis");
        System.out.println("\t press right arrow to rotate right arround vertical axis");
        System.out.println("\t press down arrow to rotate down arround horizontal axis");
        System.out.println("\t press up arrow to rotate up arround horizontal axis");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new MyGame();
        try{
            game.startup();
            game.run();
        }
        catch(Exception e){
            e.printStackTrace(System.err);
        }
        finally
        {
            game.shutdown();
            game.exit();
        }
    }

    @Override
    protected void update(Engine engine) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setupCameras(SceneManager sm, RenderWindow rw) {
        SceneNode rootNode = sm.getRootSceneNode();
        Camera camera = sm.createCamera("Player camera", Camera.Frustum.Projection.PERSPECTIVE);
        rw.getViewport(0).setCamera(camera);
        SceneNode cameraNode = rootNode.createChildSceneNode(camera.getName()+"Node");
        cameraNode.attachObject(camera);
    }

    @Override
    protected void setupScene(Engine engine, SceneManager sm) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
