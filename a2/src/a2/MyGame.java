/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a2;
import ManualObjects.BoundryObject;
import myGameEngine.Camera3Pcontroller;
import GameSetup.SetUpViewPorts;
import GameSetup.SetUpCameras;
import ManualObjects.BoxObject;
import GameSetup.SetUpInputs;
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
import ray.rage.scene.controllers.RotationController;
import ray.rml.Vector3f;
import java.util.Random;
import ManualObjects.WorldCoords;
import net.java.games.input.Controller;


/**
 *
 * @author Joe
 */
public class MyGame extends VariableFrameRateGame{
    GL4RenderSystem rs;
    public static final float speedco = 0.05f;
    float etime;
    Random rn;
    float elapseTime = 0.0f;
    float deltaTime =0.0f;
    String scoreS,dispStr;
    Camera playerOneView,playerTwoView;
    SceneNode playerOneCameraNode,playerTwoCameraNode,boxN,playerOne,playerTwo,
            playerOneAvitarNode,playerTwoAvitarNode;
    SceneNode [] scoringN;
    private InputManager im;
    private Action Movement,toggleCamera;
    int score=0,n=2;
    private boolean onedge = false;
    private Camera3Pcontroller orbitController,orbitController2;
    private Controller controller1,controller2;
    
    
    
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
    //allows functions to access delta time
    public float getdeltatime(){
        return deltaTime;
    }
    
    // set HUD edge for player
    public void setedge(boolean set){
        onedge = set;
    }
    public boolean getedge(){
        return onedge;
    }
    /**
     * @param args the command line arguments
     */
    protected void setupInputs(){
        im = new GenericInputManager();
        SetUpInputs si = new SetUpInputs(im,this,playerOneAvitarNode,playerOneView,playerTwoAvitarNode,playerTwoView);
        controller2 = si.getGamepad();
        controller1 = si.getKeboard();
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
        if(onedge == true){
            dispStr = scoreS + "  Edge of boundry";
            scoreS.toUpperCase();
        }else
            dispStr = scoreS + "  Within boundry";
        rs = (GL4RenderSystem) engine.getRenderSystem();
		scoreS = "Score = " + score;
		rs.setHUD(dispStr, 15, 15);
                deltaTime= engine.getElapsedTimeMillis();
                elapseTime += engine.getElapsedTimeMillis();
                im.update(elapseTime);      
         // detect collissions
         orbitController.updateCameraPositon();
         orbitController2.updateCameraPositon();
         
    }
    

    @Override
    protected void setupCameras(SceneManager sm, RenderWindow rw) {
        System.out.println("call1");
       SetUpCameras sc = new SetUpCameras(sm,rw);
       playerOneView = sc.getPlayerOneCamera();
       playerOneCameraNode = sc.getPlayerOneCameraN();
       playerTwoView = sc.getPlayerTwoCamera();
       playerTwoCameraNode = sc.getPlayerTwoCameraN();
       
    }
   @Override
    protected void  setupWindowViewports(RenderWindow rw){
        new SetUpViewPorts(rw);
    }
    protected void setupOrbitCamera(Engine eng, SceneManager sm){
            orbitController =
        new Camera3Pcontroller(playerOneView, playerOneCameraNode, playerOneAvitarNode, controller1,im);
            orbitController2 = new Camera3Pcontroller(playerTwoView,playerTwoCameraNode,
            playerTwoAvitarNode,controller2,im);
        
    }
    
    @Override
    protected void setupScene(Engine engine, SceneManager sm) throws IOException {
        rn = new Random(50);
        // create manual object
        
        new WorldCoords(sm.getRootSceneNode(),engine,sm);
        RotationController rc = new RotationController(Vector3f.createUnitVectorY(),0.2f);
        sm.addController(rc);
        // manual objects
        
        // scene nodes
        playerOne = sm.getRootSceneNode().createChildSceneNode("playerOne");
        playerTwo = sm.getRootSceneNode().createChildSceneNode("playerTwo");
        SceneNode BNode = sm.getRootSceneNode().createChildSceneNode("worldboundry");
        new BoundryObject(BNode,engine,sm);
        boxN = sm.getRootSceneNode().createChildSceneNode("box");
        new BoxObject(boxN,engine,sm);
        playerOneAvitarNode = sm.createSceneNode("Player1Obj");
        playerTwoAvitarNode = sm.createSceneNode("Player2Obj");
        // Entities
        Entity playerOneAvitar = sm.createEntity("playerOneAvitar", "Gaol.obj");
        Entity playerTwoAvitar = sm.createEntity("playerTwoAvitar", "dolphinHighPoly.obj");
        // set primitive draw style
        
        playerOneAvitar.setPrimitive(Primitive.TRIANGLES);
        playerTwoAvitar.setPrimitive(Primitive.TRIANGLES);
            
        // instatiate dolphin and manual objects and add rotation controller
       
        rc.addNode(boxN);

        // set up nodes and positions
        playerOneAvitarNode.attachObject(playerOneAvitar);
        playerTwoAvitarNode.attachObject(playerTwoAvitar);
        playerOne.attachChild(playerOneAvitarNode);
        playerTwo.attachChild(playerTwoAvitarNode);
        playerOne.attachChild(playerOneCameraNode);
        playerTwo.attachChild(playerTwoCameraNode);
        // set initail positions of game objects
        BNode.setLocalPosition(0.0f, -2.0f, 0.0f);
        boxN.setLocalPosition(5f,0f,0.0f);

        // setting up the lights
        sm.getAmbientLight().setIntensity(new Color(.1f, .1f, .1f));
        Light plight = sm.createLight("testLamp1", Light.Type.POINT);
        plight.setAmbient(new Color(.3f, .3f, .3f));
        plight.setDiffuse(new Color(.7f, .7f, .7f));
        plight.setSpecular(new Color(0.7f, 0.7f, 0.7f));
        plight.setRange(200f);
        plight.setConstantAttenuation(0f);
        plight.setQuadraticAttenuation(0.0f);
        plight.setLinearAttenuation(0.01f);
		
	SceneNode plightNode = sm.getRootSceneNode().createChildSceneNode("plightNode");
        plightNode.setLocalPosition(49.0f, 49f, 49.0f);
        plightNode.attachObject(plight);
        //call functions to do other computations
        
        setupInputs();
        setupOrbitCamera(engine,sm);
        
    }   
    @Override
    protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge)
    { rs.createRenderWindow(new DisplayMode(1000, 700, 24, 60), false);
    }
}
