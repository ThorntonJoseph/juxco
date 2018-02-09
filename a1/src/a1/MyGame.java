/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a1;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import ray.rage.game.*;
import java.io.IOException;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.input.action.Action;
import ray.rage.Engine;
import ray.rage.rendersystem.RenderSystem;
import ray.rage.rendersystem.RenderWindow;
import ray.rage.rendersystem.Renderable.Primitive;
import ray.rage.scene.SceneManager;
import ray.rage.rendersystem.gl4.GL4RenderSystem;
import ray.rage.scene.Camera;
import ray.rage.scene.*;
import ray.rage.scene.Camera.Frustum.Projection;
import ray.rage.scene.controllers.RotationController;
import ray.rml.Vector3f;

/**
 *
 * @author Joe
 */
public class MyGame extends VariableFrameRateGame{
    GL4RenderSystem rs;
    float etime;
    float elapseTime = 0.0f;
    String score,dispStr;
    Camera camera;
    private InputManager im;
    private Action moveLeft,moveRight,moveBack,moveForward;
    
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
    protected void setupInputs(){
        im = new GenericInputManager();
        String kbName = im.getKeyboardName();
        moveForward = new MoveForwardAction(camera);
        System.out.println("ok");
        im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.W, moveForward, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
    }
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
        rs = (GL4RenderSystem) engine.getRenderSystem();
		dispStr = "Time = " + score;
		rs.setHUD(dispStr, 15, 15);
                elapseTime += engine.getElapsedTimeMillis();
                im.update(elapseTime);
                
                
    }

    @Override
    protected void setupCameras(SceneManager sm, RenderWindow rw) {
       SceneNode rootNode = sm.getRootSceneNode();
        camera = sm.createCamera("MainCamera", Projection.PERSPECTIVE);
        rw.getViewport(0).setCamera(camera);
	camera.setRt((Vector3f)Vector3f.createFrom(1.0f, 0.0f, 0.0f));
	camera.setUp((Vector3f)Vector3f.createFrom(0.0f, 1.0f, 0.0f));
	camera.setFd((Vector3f)Vector3f.createFrom(0.0f, 0.0f, -1.0f));		
	camera.setPo((Vector3f)Vector3f.createFrom(0.0f, 0.0f, 0.0f));
        SceneNode cameraNode = rootNode.createChildSceneNode(camera.getName() + "Node");
        cameraNode.attachObject(camera);
     
    }

    @Override
    protected void setupScene(Engine engine, SceneManager sm) throws IOException {
        Entity dolphinE = sm.createEntity("myDolphin", "dolphinHighPoly.obj");
        dolphinE.setPrimitive(Primitive.TRIANGLES);
        setupInputs();
        SceneNode dolphinN = sm.getRootSceneNode().createChildSceneNode(dolphinE.getName() + "Node");
        dolphinN.moveBackward(2.0f);
        dolphinN.attachObject(dolphinE);

        sm.getAmbientLight().setIntensity(new Color(.1f, .1f, .1f));
		
		Light plight = sm.createLight("testLamp1", Light.Type.POINT);
		plight.setAmbient(new Color(.3f, .3f, .3f));
        plight.setDiffuse(new Color(.7f, .7f, .7f));
		plight.setSpecular(new Color(1.0f, 1.0f, 1.0f));
        plight.setRange(5f);
		
		SceneNode plightNode = sm.getRootSceneNode().createChildSceneNode("plightNode");
        plightNode.attachObject(plight);

    }
    @Override
    protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge)
    { rs.createRenderWindow(new DisplayMode(1000, 700, 24, 60), false);
    }
    
}
