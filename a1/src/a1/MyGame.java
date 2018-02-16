/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a1;
import myGameEngine.ToggleCameraAction;
import myGameEngine.PlayerMovementActions;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import ray.rage.game.*;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.input.action.Action;
import ray.rage.Engine;
import ray.rage.asset.material.Material;
import ray.rage.rendersystem.RenderSystem;
import ray.rage.rendersystem.RenderWindow;
import ray.rage.rendersystem.Renderable.DataSource;
import ray.rage.rendersystem.Renderable.Primitive;
import ray.rage.scene.SceneManager;
import ray.rage.rendersystem.gl4.GL4RenderSystem;
import ray.rage.rendersystem.shader.GpuShaderProgram;
import ray.rage.rendersystem.states.FrontFaceState;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.Camera;
import ray.rage.scene.*;
import ray.rage.scene.Camera.Frustum.Projection;
import ray.rage.scene.controllers.RotationController;
import ray.rage.util.BufferUtil;
import ray.rml.Degreef;
import ray.rml.Vector3f;
import java.util.Random;
import net.java.games.input.Controller;
import ManualObjects.WorldCoords;


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
    Camera camera;
    SceneNode dolphinN,dolphinCN,cameraDN,boxN;
    SceneNode [] scoringN;
    private InputManager im;
    private Action moveLeft,moveRight,Movement,moveForward,toggleCamera;
    int score=0,n=2;
    private boolean onedge = false;
    
    
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
        
            String cnName = im.getFirstGamepadName();
        
        
        String kbName=im.getKeyboardName();
        // create actions for inputs 
        Movement = new PlayerMovementActions(camera,dolphinN,this);
        toggleCamera = new ToggleCameraAction(camera,dolphinN);
       
        //instantiat controlls
        if(cnName!=null){
          im.associateAction(cnName, net.java.games.input.Component.Identifier.Axis.RX, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
          im.associateAction(cnName, net.java.games.input.Component.Identifier.Axis.RY, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
          im.associateAction(cnName, net.java.games.input.Component.Identifier.Button._0, toggleCamera, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
          im.associateAction(cnName, net.java.games.input.Component.Identifier.Axis.X, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
          im.associateAction(cnName, net.java.games.input.Component.Identifier.Axis.Y, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        }else
            System.out.println("plug a controller in");
         
        im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.W, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.A, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.S, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.LEFT, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.RIGHT, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.UP, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.DOWN, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.D, Movement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kbName, net.java.games.input.Component.Identifier.Key.SPACE, toggleCamera, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
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
         for(int i = 0;i<scoringN.length;i++){
             if(PlayerMovementActions.distance(camera.getPo(), (Vector3f) scoringN[i].getLocalPosition())<2.0f&& i<scoringN.length){
                 dolphinN.attachChild(scoringN[i]);
                 if(n==2)
                    dolphinN.getChild(n).setLocalPosition(0.0f, 0.0f, 0.95f);
                 else
                       dolphinN.getChild(n).setLocalPosition(0.0f,0.38f*(n-2),0.95f);
                 
                 dolphinN.getChild(n).setLocalScale(0.1f, 0.1f, 0.1f);
                 n=n+1;
             }
             if(PlayerMovementActions.distance((Vector3f)boxN.getLocalPosition(),(Vector3f) dolphinN.getLocalPosition())<2.0f&&dolphinN.getChildCount()>2){
                 float xd=1.0f,zd=1.0f;
                 if(score<6||(score>11&&score<18)||(score>23&&score<30)||(score<36&&score>41))
                 {
                     xd= xd-(score+6)%6*0.38f;
                     if(score<6||(score>17&&score<24)||(score>35&&score<42))
                        zd =1.0f;
                     else {
                         zd=-1.0f;
                     }
                         
                 }else{
                     zd=zd-score%6*0.38f;
                     if((score>5&&score<12)||(score>34&&score<36)||(score>41&&score<48))
                         xd=-1.0f;
                     else
                         xd=1.0f;
                 }
                 
                Node temp = dolphinN.getChild(2);
                dolphinN.detachChild(dolphinN.getChild(2));
                n=2;
                boxN.attachChild(temp);
                boxN.getChild(score).setLocalPosition(-1f*xd,0.19f*(((int)score/24)+1),1f*zd);
                score+=1;
             }
        }
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
        cameraDN = rootNode.createChildSceneNode(camera.getName() + "Node");
        camera.setMode('c');
    }

    @Override
    protected void setupScene(Engine engine, SceneManager sm) throws IOException {
        rn = new Random(50);
        // create manual object
         new WorldCoords(sm.getRootSceneNode(),engine,sm);
        RotationController rc = new RotationController(Vector3f.createUnitVectorY(),0.2f);
        sm.addController(rc);
        // manual objects
        ManualObject box = makeScoreObject(engine,sm); 
        
        ManualObject boundry = makeWorldBoundry(engine,sm);
        
        // scene nodes
        SceneNode BNode = sm.getRootSceneNode().createChildSceneNode("worldboundry");
        
        boxN = sm.getRootSceneNode().createChildSceneNode("box");
        scoringN = new SceneNode[50];
        SceneNode dolphinObjN = sm.createSceneNode("dolphinObjN");
        // Entities
        Entity scoringE[] = new Entity[50];
        Entity dolphinE = sm.createEntity("myDolphin", "dolphinHighPoly.obj");
        // set primitive draw style
        
        dolphinE.setPrimitive(Primitive.TRIANGLES);
        
        // create nodes for score objects
        for(int i=0;i<50;i++){
           
            scoringE[i] = sm.createEntity("scoreE"+i, "earth.obj");
            scoringE[i].setPrimitive(Primitive.TRIANGLES);
            scoringN[i] = sm.getRootSceneNode().createChildSceneNode("scoreN" +i);
            scoringN[i].attachObject(scoringE[i]);
            int tempx = rn.nextInt(48)+1;
            int tempz = rn.nextInt(48)+1;
            int tempy = rn.nextInt(48)+1;
            if(rn.nextInt(100)%2==0){
                tempx = -tempx;
                tempz = -tempz;
                tempy = -tempy;
                
            }
            scoringN[i].setLocalPosition(tempx, tempy, tempz);
            rc.addNode(scoringN[i]);
        }
        // instatiate dolphin and manual objects and add rotation controller
        BNode.attachObject(boundry);
        boxN.attachObject(box);
        rc.addNode(boxN);

        // set up nodes and positions
         
        dolphinN = sm.getRootSceneNode().createChildSceneNode("dolphinN");
        dolphinCN=sm.createSceneNode("dolphinCN");
        dolphinObjN.attachObject(dolphinE);
        // set initail positions of game objects
        BNode.setLocalPosition(0.0f, -50.0f, 0.0f);
        dolphinN.setLocalPosition(0.0f,0.0f,0.0f);
        boxN.setLocalPosition(5f,0f,0.0f);
        
        camera.setPo((Vector3f) Vector3f.createFrom(0.0f, 0.0f, 2.0f));
        dolphinCN.setLocalPosition(0.0f, 0.4f, -0.8f);
        dolphinCN.attachObject(camera);
        dolphinN.attachChild(dolphinCN);
        dolphinN.attachChild(dolphinObjN);
       
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
        setupInputs();
    }
     
      
     protected ManualObject makeScoreObject(Engine eng, SceneManager sm)throws IOException{
        ManualObject box = sm.createManualObject("Pyramid");
        ManualObjectSection boxSec = box.createManualSection("PyramidSection");
        box.setGpuShaderProgram(sm.getRenderSystem().
            getGpuShaderProgram(GpuShaderProgram.Type.RENDERING));
        
        float [] verticies = new float[] 
        { -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 2.0f, 1.0f, 
            -1.0f, 0.0f, 1.0f,1.0f, 2.0f,1.0f, -1.0f, 2.0f, 1.0f,//front
            -1.0f,0.0f,-1.0f,-1.0f,2.0f,-1.0f, 1.0f, 2.0f, -1.0f, 
            -1.0f, 0.0f, -1.0f, 1.0f, 2.0f, -1.0f,1.0f,0.0f,-1.0f, //back
            -1.0f, 0.0f, 1.0f, -1.0f, 2.0f, -1.0f, -1.0f, 0.0f, -1.0f, 
            -1.0f, 0.0f, 1.0f,-1.0f, 2.0f,1.0f, -1.0f, 2.0f, -1.0f,//left
            1.0f,0.0f,1.0f,1.0f,0.0f,-1.0f, 1.0f, 2.0f, -1.0f, 
            1.0f, 0.0f, 1.0f, 1.0f, 2.0f, -1.0f,1.0f,2.0f,1.0f, //right
            -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f, 
            -1.0f, 0.0f, 1.0f,1.0f, 0.0f,-1.0f, 1.0f, 0.0f, 1.0f,//bottom
            -1.0f, 2.0f, 1.0f, 1.0f, 2.0f, -1.0f, -1.0f, 2.0f, -1.0f, 
            -1.0f, 2.0f, 1.0f,1.0f, 2.0f,1.0f, 1.0f, 2.0f, -1.0f //top
            
        };
        float[] texcoords = new float[]
            { 0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f
        };
        float[] normals = new float[]
            { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,//front
            0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f,//front
            -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,//left
            1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,//right
            0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f,//bottom
            0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,// top
        };
           
                    
        int[] indices = new int[] { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
            17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36 };
            
        FloatBuffer vertBuf = BufferUtil.directFloatBuffer(verticies);
        FloatBuffer texBuf = BufferUtil.directFloatBuffer(texcoords);
        FloatBuffer normBuf = BufferUtil.directFloatBuffer(normals);
        IntBuffer indexBuf = BufferUtil.directIntBuffer(indices);
        
        boxSec.setVertexBuffer(vertBuf);
        boxSec.setTextureCoordsBuffer(texBuf);
        boxSec.setNormalsBuffer(normBuf);
        boxSec.setIndexBuffer(indexBuf);
        
        ray.rage.asset.texture.Texture tex = eng.getTextureManager().getAssetByPath("moon.jpeg");
        TextureState texState = (TextureState)sm.getRenderSystem().
        createRenderState(RenderState.Type.TEXTURE);
        texState.setTexture(tex);
        FrontFaceState faceState = (FrontFaceState) sm.getRenderSystem().
        createRenderState(RenderState.Type.FRONT_FACE);
        box.setDataSource(DataSource.INDEX_BUFFER);
        box.setRenderState((RenderState)texState);
        box.setRenderState(faceState);
        Material mat =  sm.getMaterialManager().getAssetByPath("default.mtl");
        mat.setEmissive(Color.YELLOW);
    
        box.setMaterial(mat);
        return box;
            
     }
     
    @Override
    protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge)
    { rs.createRenderWindow(new DisplayMode(1000, 700, 24, 60), false);
    }
    protected ManualObject makeWorldBoundry(Engine eng, SceneManager sm)throws IOException{
        ManualObject worldBoundry = sm.createManualObject("WorldBoundry");
        ManualObjectSection pyrSec = worldBoundry.createManualSection("Worldsec");
        worldBoundry.setGpuShaderProgram(sm.getRenderSystem().
            getGpuShaderProgram(GpuShaderProgram.Type.RENDERING));
        
        float [] verticies = new float[] 
        { -400.0f, 0.0f, 400.0f, 400.0f, 0.0f, 400.0f, 400.0f, 800.0f, 400.0f, 
            -400.0f, 0.0f, 400.0f,400.0f, 800.0f,400.0f, -400.0f, 800.0f, 400.0f,//front
            -400.0f,0.0f,-400.0f,-400.0f,800.0f,-400.0f, 400.0f, 800.0f, -400.0f, 
            -400.0f, 0.0f, -400.0f, 400.0f, 800.0f, -400.0f,400.0f,0.0f,-400.0f, //back
            -400.0f, 0.0f, 400.0f, -400.0f, 800.0f, -400.0f, -400.0f, 0.0f, -400.0f, 
            -400.0f, 0.0f, 400.0f,-400.0f, 800.0f,400.0f, -400.0f, 800.0f, -400.0f,//left
            400.0f,0.0f,400.0f,400.0f,0.0f,-400.0f, 400.0f, 800.0f, -400.0f, 
            400.0f, 0.0f, 400.0f, 400.0f, 800.0f, -400.0f,400.0f,800.0f,400.0f, //right
            -400.0f, 0.0f, 400.0f, -400.0f, 0.0f, -400.0f, 400.0f, 0.0f, -400.0f, 
            -400.0f, 0.0f, 400.0f,400.0f, 0.0f,-400.0f, 400.0f, 0.0f, 400.0f,//bottom
            -400.0f, 800.0f, 400.0f, 400.0f, 800.0f, -400.0f, -400.0f, 800.0f, -400.0f, 
            -400.0f, 800.0f, 400.0f,400.0f, 800.0f,400.0f, 400.0f, 800.0f, -400.0f //top
            
        };
        int i=0;
        while(i<verticies.length){
            verticies[i]=verticies[i]/8f;
            i++;
            
        }
            
        float[] texcoords = new float[]
            { 0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f
                
        };
        float[] normals = new float[]
            { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,//front
            0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f,//front
            -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,//left
            1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,//right
            0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f,//bottom
            0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,// top
        };
           
                    
        int[] indices = new int[] { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
            17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36 };
            
        FloatBuffer vertBuf = BufferUtil.directFloatBuffer(verticies);
        FloatBuffer texBuf = BufferUtil.directFloatBuffer(texcoords);
        FloatBuffer normBuf = BufferUtil.directFloatBuffer(normals);
        IntBuffer indexBuf = BufferUtil.directIntBuffer(indices);
        
        pyrSec.setVertexBuffer(vertBuf);
        pyrSec.setTextureCoordsBuffer(texBuf);
        pyrSec.setNormalsBuffer(normBuf);
        pyrSec.setIndexBuffer(indexBuf);
        
        ray.rage.asset.texture.Texture tex = eng.getTextureManager().getAssetByPath("earth-day.jpeg");
        TextureState texState = (TextureState)sm.getRenderSystem().
        createRenderState(RenderState.Type.TEXTURE);
        texState.setTexture(tex);
        FrontFaceState faceState = (FrontFaceState) sm.getRenderSystem().
        createRenderState(RenderState.Type.FRONT_FACE);
        faceState.setVertexWinding(FrontFaceState.VertexWinding.CLOCKWISE);
        worldBoundry.setDataSource(DataSource.INDEX_BUFFER);
        worldBoundry.setRenderState((RenderState)texState);
        worldBoundry.setRenderState(faceState);
        Material mat =  sm.getMaterialManager().getAssetByPath("earth.mtl");
       
    
        worldBoundry.setMaterial(mat);
        return worldBoundry;
            
     }
    
}
