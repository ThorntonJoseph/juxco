/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSetup;

import ray.rage.rendersystem.RenderWindow;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3f;

/**
 *
 * @author Joe
 */
public class SetUpCameras {
    private Camera firstCamera,secondCamera;
    private SceneNode firstCameraN,secondCameraN;
    
    // getters for player cameras and nodes
    public Camera getPlayerOneCamera(){
        return firstCamera;
    }
    public SceneNode getPlayerOneCameraN(){
        return firstCameraN;
    }
     public Camera getPlayerTwoCamera(){
        return secondCamera;
    }
    public SceneNode getPlayerTwoCameraN(){
        return secondCameraN;
    }
    public SetUpCameras(SceneManager sm, RenderWindow rw){
        setupCameras(sm,rw);
    }
    
    protected void setupCameras(SceneManager sm, RenderWindow rw){
        //first camera
        SceneNode rootNode = sm.getRootSceneNode();
        firstCamera = sm.createCamera("Player1Camera", Camera.Frustum.Projection.PERSPECTIVE);
        rw.getViewport(0).setCamera(firstCamera);
	firstCamera.setRt((Vector3f)Vector3f.createFrom(1.0f, 0.0f, 0.0f));
	firstCamera.setUp((Vector3f)Vector3f.createFrom(0.0f, 1.0f, 0.0f));
	firstCamera.setFd((Vector3f)Vector3f.createFrom(0.0f, 0.0f, -1.0f));
	firstCamera.setPo((Vector3f)Vector3f.createFrom(0.0f, 0.0f, 0.0f));
        firstCameraN = rootNode.createChildSceneNode(firstCamera.getName() + "Node");
        firstCameraN.attachObject(firstCamera);
        firstCamera.setMode('n');
        firstCamera.getFrustum().setFarClipDistance(1000f);
       
        secondCamera = sm.createCamera("Player2Camera", Camera.Frustum.Projection.PERSPECTIVE);
        rw.getViewport(1).setCamera(secondCamera);
        secondCamera.setRt((Vector3f)Vector3f.createFrom(1.0f, 0.0f, 0.0f));
	secondCamera.setUp((Vector3f)Vector3f.createFrom(0.0f, 1.0f, 0.0f));
	secondCamera.setFd((Vector3f)Vector3f.createFrom(0.0f, 0.0f, -1.0f));
	secondCamera.setPo((Vector3f)Vector3f.createFrom(0.0f, 0.0f, 0.0f));
        secondCameraN = rootNode.createChildSceneNode(secondCamera.getName()+"Node");
        secondCameraN.attachObject(secondCamera);
        secondCamera.setMode('n');
        secondCamera.getFrustum().setFarClipDistance(1000f);

    }
}
