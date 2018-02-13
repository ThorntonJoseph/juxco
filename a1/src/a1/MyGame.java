/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a1;
import myGameEngine.ToggleCamera;
import myGameEngine.MovementAction;
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
    int score=0;
    
    
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
    /**
     * @param args the command line arguments
     */
    protected void setupInputs(){
        im = new GenericInputManager();
        String kbName = im.getKeyboardName();
        // create actions for inputs 
        Movement = new MovementAction(camera,dolphinN,this);
        toggleCamera = new ToggleCamera(camera,dolphinN);
        
        //instantiat controlls
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
        rs = (GL4RenderSystem) engine.getRenderSystem();
		dispStr = "Score = " + score;
		rs.setHUD(dispStr, 15, 15);
                deltaTime= engine.getElapsedTimeMillis();
                elapseTime += engine.getElapsedTimeMillis();
                im.update(elapseTime);      
         // detect collissions
         for(int i = 0;i<scoringN.length;i++){
             if(MovementAction.distance(camera.getPo(), (Vector3f) scoringN[i].getLocalPosition())<2.0f&& dolphinN.getChildCount()<3){
                 dolphinN.attachChild(scoringN[i]);
                 dolphinN.getChild(2).setLocalPosition(0.0f, 0.0f, 0.95f);
                 dolphinN.getChild(2).setLocalScale(0.1f, 0.1f, 0.1f);
                 System.out.println("good");
             }
             if(MovementAction.distance((Vector3f)boxN.getLocalPosition(),(Vector3f) dolphinN.getLocalPosition())<2.0f&&dolphinN.getChildCount()>2){
                Node temp = dolphinN.getChild(2);
                dolphinN.detachChild(dolphinN.getChild(2));
                boxN.attachChild(temp);
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
        
        RotationController rc = new RotationController(Vector3f.createUnitVectorY(),0.2f);
        sm.addController(rc);
        // manual objects
        ManualObject box = makeScoreObject(engine,sm); 
        ManualObject coord = makeWorldCoord(engine,sm);
        ManualObject boundry = makeWorldBoundry(engine,sm);
        // scene nodes
        SceneNode BNode = sm.getRootSceneNode().createChildSceneNode("worldboundry");
        SceneNode coordN = sm.getRootSceneNode().createChildSceneNode("coordN");
        boxN = sm.getRootSceneNode().createChildSceneNode("box");
        scoringN = new SceneNode[50];
        SceneNode dolphinObjN = sm.createSceneNode("dolphinObjN");
        // Entities
        Entity scoringE[] = new Entity[50];
        Entity dolphinE = sm.createEntity("myDolphin", "dolphinHighPoly.obj");
        
        // create nodes for score objects
        for(int i=0;i<50;i++){
            scoringE[i] = sm.createEntity("scoreE"+i, "earth.obj");
            scoringE[i].setPrimitive(Primitive.TRIANGLES);
            scoringN[i] = sm.getRootSceneNode().createChildSceneNode("scoreN" +i);
            scoringN[i].attachObject(scoringE[i]);
            int tempx = rn.nextInt(48)+1;
            int tempz = rn.nextInt(48)+1;
            if(rn.nextInt(100)%2==0){
                tempx = -tempx;
                tempz = -tempz;
                
            }
            scoringN[i].setLocalPosition(tempx, rn.nextInt(98)+1, tempz);
            rc.addNode(scoringN[i]);
        }
        // instatiate dolphin and manual objects and add rotation controller
        BNode.attachObject(boundry);
        coordN.attachObject(coord);
        boxN.attachObject(box);
        rc.addNode(boxN);

        // set up nodes and positions
         dolphinE.setPrimitive(Primitive.TRIANGLES);
        dolphinN = sm.getRootSceneNode().createChildSceneNode("dolphinN");
        dolphinCN=sm.createSceneNode("dolphinCN");
        dolphinObjN.attachObject(dolphinE);
        // set initail positions of game objects
        dolphinN.setLocalPosition(0.0f,50f,0.0f);
        boxN.setLocalPosition(5f,50f,0.0f);
        camera.setPo((Vector3f) Vector3f.createFrom(0.0f, 50f, 2.0f));
        dolphinCN.setLocalPosition(0.0f, 0.4f, -0.8f);
        dolphinCN.attachObject(camera);
        dolphinN.attachChild(dolphinCN);
        dolphinN.attachChild(dolphinObjN);
       
        // setting up the lights
        sm.getAmbientLight().setIntensity(new Color(.1f, .1f, .1f));
		Light plight = sm.createLight("testLamp1", Light.Type.POINT);
		plight.setAmbient(new Color(.3f, .3f, .3f));
        plight.setDiffuse(new Color(.7f, .7f, .7f));
		plight.setSpecular(new Color(1.0f, 1.0f, 1.0f));
        plight.setRange(5f);
		
		SceneNode plightNode = sm.getRootSceneNode().createChildSceneNode("plightNode");
        plightNode.attachObject(plight);
        setupInputs();
    }
     protected ManualObject makeWorldCoord(Engine eng, SceneManager sm)throws IOException{
        ManualObject coord = sm.createManualObject("coord");
        ManualObjectSection coordSec = coord.createManualSection("coordSec");
        coord.setGpuShaderProgram(sm.getRenderSystem()
                 .getGpuShaderProgram(GpuShaderProgram.Type.RENDERING));
        float[] verticies = new float[] {0.0f,0.0f,0.0f,
         1.0f,0.0f,0.0f };
        float[] texcoords = new float[] {0.0f,0.0f,0.0f,1.0f};
        float[] normals = new float[] {0.0f,1.0f,0.0f,
         1.0f,0.0f,0.0f,};
        int[] indices = new int[] {1,2};  
        
        FloatBuffer vertBuf = BufferUtil.directFloatBuffer(verticies);
        FloatBuffer normBuf = BufferUtil.directFloatBuffer(normals);
        FloatBuffer texBuf = BufferUtil.directFloatBuffer(texcoords);
        IntBuffer indexBuf = BufferUtil.directIntBuffer(indices);
        
        coordSec.setVertexBuffer(vertBuf);
        coordSec.setTextureCoordsBuffer(texBuf);
        coordSec.setNormalsBuffer(normBuf);
        coordSec.setIndexBuffer(indexBuf);
        
        Material mat =  sm.getMaterialManager().getAssetByPath("default.mtl");
        mat.setEmissive(Color.BLUE);
        ray.rage.asset.texture.Texture tex = eng.getTextureManager().getAssetByPath(mat.getTextureFilename());
        TextureState texState = (TextureState)sm.getRenderSystem().
        createRenderState(RenderState.Type.TEXTURE);
        texState.setTexture(tex);
        coord.setDataSource(DataSource.VERTEX_BUFFER);
        coord.setRenderState((RenderState)texState);
        coord.setMaterial(mat);
        
        return coord;
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
    
        //pyr.setMaterial(mat);
        return box;
            
     }
     
    @Override
    protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge)
    { rs.createRenderWindow(new DisplayMode(1000, 700, 24, 60), false);
    }
    protected ManualObject makeWorldBoundry(Engine eng, SceneManager sm)throws IOException{
        ManualObject pyr = sm.createManualObject("WorldBoundry");
        ManualObjectSection pyrSec = pyr.createManualSection("Worldsec");
        pyr.setGpuShaderProgram(sm.getRenderSystem().
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
        pyr.setDataSource(DataSource.INDEX_BUFFER);
        pyr.setRenderState((RenderState)texState);
        pyr.setRenderState(faceState);
        
    
        //pyr.setMaterial(mat);
        return pyr;
            
     }
    
}
