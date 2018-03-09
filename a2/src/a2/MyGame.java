/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a2;

import GameSetup.BallControl;
import myGameEngine.PlayerBuckController;
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
import myGameEngine.PlayerMovementActions;
import net.java.games.input.Controller;
import ray.rage.asset.material.Material;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rml.Degreef;

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
            playerOneAvitarNode,playerTwoAvitarNode,sball;
    BallControl ballcontrol;
    SceneNode [] scoringN = new SceneNode[50];
    private InputManager im;
    private Action Movement,toggleCamera;
    int p1score=0,p2score=0,n=2;
    private boolean onedge = false;
    private Camera3Pcontroller orbitController,orbitController2;
    private Controller controller1,controller2;
    private SetUpInputs inputs;
    private PlayerBuckController ps1,ps2;
    
    
    
    
    public MyGame(){
        super();
        
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
        inputs = new SetUpInputs(im,this,playerOneAvitarNode,playerOneView,playerTwoAvitarNode,playerTwoView);
        controller2 = inputs.getGamepad();
        controller1 = inputs.getKeboard();
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
		scoreS = "Score = " + p1score;
		rs.setHUD(scoreS, 25, 15);
                scoreS = "Score = " + p2score;
                rs.setHUD2(scoreS, 25, rs.getRenderWindow().getViewport(0).getActualHeight()+25);
                System.out.println();
                deltaTime= engine.getElapsedTimeMillis();
                elapseTime += engine.getElapsedTimeMillis();
                im.update(elapseTime);      
         // detect collissions
         orbitController.updateCameraPositon();
         if(orbitController2 !=null){
             orbitController2.updateCameraPositon();
         } 
         for(SceneNode n: scoringN){
             if(PlayerMovementActions.distance((Vector3f)playerOneAvitarNode.getLocalPosition(),(Vector3f) n.getLocalPosition())<.95f){
                 System.out.println("delete");
                 if(n.getAttachedObjectCount()>0){
                     p1score++;
                 }
                 n.detachAllObjects();
             }
             if(im.getFirstGamepadName()!=null && PlayerMovementActions.distance((Vector3f)playerTwoAvitarNode.getLocalPosition(),(Vector3f) n.getLocalPosition())<.95f){
                 System.out.println("delete2");
                 if(n.getAttachedObjectCount()>0){
                     p2score++;
                 }
                 n.detachAllObjects();
             }
             ballcontrol.update();
         }
    }
    

    @Override
    protected void setupCameras(SceneManager sm, RenderWindow rw) {
        
        
       SetUpCameras sc = new SetUpCameras(sm,rw,im);
       playerOneView = sc.getPlayerOneCamera();
       playerOneCameraNode = sc.getPlayerOneCameraN();
       playerTwoView = sc.getPlayerTwoCamera();
       playerTwoCameraNode = sc.getPlayerTwoCameraN();
       
    }
   @Override
    protected void  setupWindowViewports(RenderWindow rw){
        
        im = new GenericInputManager();
        new SetUpViewPorts(rw,im);
    }
    protected void setupOrbitCamera(Engine eng, SceneManager sm){
            orbitController =
        new Camera3Pcontroller(playerOneView, playerOneCameraNode, playerOneAvitarNode, controller1,im,this);
            if(inputs.getGamepad()!=null){
                orbitController2 = new Camera3Pcontroller(playerTwoView,playerTwoCameraNode,
            playerTwoAvitarNode,controller2,im,this);
            }
            
    }
    
    @Override
    protected void setupScene(Engine engine, SceneManager sm) throws IOException {
        Random rnz = new Random();
        Random rnx = new Random();
        Random side = new Random();
        // create manual object
        new WorldCoords(sm.getRootSceneNode(),engine,sm);
        RotationController rc = new RotationController(Vector3f.createUnitVectorY(),0.2f);
        sm.addController(rc);
        Entity[] scoringE = new Entity[50];
        
        // point nodes
       for(int i =0;i<50;i++){
           int sidet = side.nextInt(2);
           int tempx = rnx.nextInt(24);
           int tempz = rnz.nextInt(14);
           scoringE[i] = sm.createEntity("scoringE"+i, "cube.obj");
           scoringN[i] = sm.getRootSceneNode().createChildSceneNode("scoreobject"+i);
           scoringN[i].attachObject(scoringE[i]);
           if(sidet == 0){
               System.out.println(i);
               tempx = -tempx;
           }
           sidet = side.nextInt(2);
           if(sidet == 0){
               tempz = -tempz;
           }
           scoringN[i].setLocalPosition(tempx,.1f,tempz);
           scoringN[i].scale(.25f, .25f, .25f);
           rc.addNode(scoringN[i]);   
       }
        
        
       
        // Entities
        Entity playerOneAvitar = sm.createEntity("playerOneAvitar", "dolphinHighPoly.obj");
        Entity playerTwoAvitar = sm.createEntity("playerTwoAvitar", "dolphinHighPoly.obj");
        Entity goal1m = sm.createEntity("Gaol1", "Goal.obj");
        Entity goal2m = sm.createEntity("Gaol2", "Goal2.obj");
        Entity fieldm = sm.createEntity("field", "Field.obj");
        Entity linesm = sm.createEntity("lines", "Lines.obj");
        Entity goal1Boarderm = sm.createEntity("goal1Boarder", "Goal1Boarder.obj");
        Entity goal2Boarderm = sm.createEntity("goal2Boarder", "Goal2Boarder.obj");
        Entity ball = sm.createEntity("soccerball", "soccerballobj.obj");
        // set primitive draw style
        
        playerOneAvitar.setPrimitive(Primitive.TRIANGLES);
        playerTwoAvitar.setPrimitive(Primitive.TRIANGLES);
     
        //setup field
        
        SceneNode field = sm.getRootSceneNode().createChildSceneNode("field");
        SceneNode lines = sm.getRootSceneNode().createChildSceneNode("lines");
        SceneNode goal1Boarder = sm.getRootSceneNode().createChildSceneNode("goal1boarder");
        SceneNode goal2Boarder = sm.getRootSceneNode().createChildSceneNode("goal2boarder");
        fieldm.setPrimitive(Primitive.TRIANGLES);
        linesm.setPrimitive(Primitive.TRIANGLES);
        goal1Boarderm.setPrimitive(Primitive.TRIANGLES);
        Material fieldmtl = sm.getMaterialManager().getAssetByPath("Field.mtl"); 
        Material linesmtl = sm.getMaterialManager().getAssetByPath("Lines.mtl"); 
        Material goal1Boardermtl = sm.getMaterialManager().getAssetByPath("Goal1Boarder.mtl"); 
        Material goal2Boardermtl = sm.getMaterialManager().getAssetByPath("Goal2Boarder.mtl"); 
        fieldm.setMaterial(fieldmtl);
        linesm.setMaterial(linesmtl);
        goal1Boarderm.setMaterial(goal1Boardermtl);
        goal2Boarderm.setMaterial(goal2Boardermtl);
        field.attachObject(fieldm);
        lines.attachObject(linesm);
        goal1Boarder.attachObject(goal1Boarderm);
        goal2Boarder.attachObject(goal2Boarderm);
        field.setLocalPosition(0.0f,-1.0f,0.0f);
        lines.setLocalPosition(0.0f,-1.0f,0.0f);
        goal1Boarder.setLocalPosition(0.0f,-1.0f,0.0f);
        goal2Boarder.setLocalPosition(0.0f,-1.0f,0.0f);
        
       
        // set up goals
        SceneNode goal1n = sm.getRootSceneNode().createChildSceneNode("goal1");
        SceneNode goal2n = sm.getRootSceneNode().createChildSceneNode("goal2");
        goal1m.setPrimitive(Primitive.TRIANGLES);
        goal2m.setPrimitive(Primitive.TRIANGLES);
        Material goal2mtl = sm.getMaterialManager().getAssetByPath("Goal2.mtl");
        goal1m.setMaterial(goal1Boardermtl);
        goal2m.setMaterial(goal2mtl);
        goal1n.attachObject(goal1m);
        goal2n.attachObject(goal2m);
        goal2n.setLocalPosition(21f,-1.0f,0f);
        goal1n.setLocalPosition(-21f,-1.0f,0f);
        goal2n.rotate(Degreef.createFrom(180f), Vector3f.createUnitVectorY());
        goal1n.scale(2.0f, 2.0f, 2.0f);
        goal2n.scale(2.0f, 2.0f, 2.0f);
        // set up players
        
        playerOne = sm.getRootSceneNode().createChildSceneNode("playerOne");
        playerOneAvitarNode = sm.createSceneNode("Player1Obj");
        playerOneAvitarNode.attachObject(playerOneAvitar);
        playerOne.attachChild(playerOneAvitarNode);
        playerOne.attachChild(playerOneCameraNode);
        playerOneAvitarNode.rotate(Degreef.createFrom(90f), playerOne.getLocalUpAxis());
        playerOneAvitarNode.setLocalPosition(-21f, 0f, 0f);
        
        
        
        if(im.getFirstGamepadName() != null){
            playerTwo = sm.getRootSceneNode().createChildSceneNode("playerTwo");
            playerTwoAvitarNode = sm.createSceneNode("Player2Obj");
            playerTwoAvitarNode.attachObject(playerTwoAvitar);
            playerTwo.attachChild(playerTwoAvitarNode);
            playerTwo.attachChild(playerTwoCameraNode);
            playerTwoAvitarNode.rotate(Degreef.createFrom(-90f), playerTwoAvitarNode.getLocalUpAxis());
            playerTwoAvitarNode.setLocalPosition(21f, 0f, 0f);
        }
        setupInputs();
        ps1 = inputs.getPB1();
        ps1.addNode(playerOneAvitarNode);
        if(im.getFirstGamepadName()!=null){
            ps2 = inputs.getPB2();
            sm.addController(ps2);
            ps2.addNode(playerTwoAvitarNode);
        }
        sm.addController(ps1);
        // set initail positions of game objects
        // setup ball
        sball = sm.getRootSceneNode().createChildSceneNode("ball");
        ballcontrol = new BallControl(ps1,ps2,sball,this);
        Material sballmtl = sm.getMaterialManager().getAssetByPath("soccerballobj.mtl");
        ball.setMaterial(sballmtl);
        sball.attachObject(ball);
        sball.setLocalPosition(0f,200f,0f);
        sball.scale(2f, 2f, 2f);
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
        
        
        setupOrbitCamera(engine,sm);
        
    }   
    @Override
    protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge)
    { rs.createRenderWindow(new DisplayMode(1000, 700, 24, 60), false);
    }
}
