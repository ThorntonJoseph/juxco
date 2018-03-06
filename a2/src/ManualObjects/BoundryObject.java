/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManualObjects;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import ray.rage.Engine;
import ray.rage.asset.material.Material;
import ray.rage.rendersystem.Renderable;
import ray.rage.rendersystem.shader.GpuShaderProgram;
import ray.rage.rendersystem.states.FrontFaceState;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.ManualObject;
import ray.rage.scene.ManualObjectSection;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rage.util.BufferUtil;

/**
 *
 * @author Joe
 */
public class BoundryObject {
    
    public BoundryObject(SceneNode location,Engine eng,SceneManager sm){
        try {
            ManualObject Boundry = makeWorldBoundry(eng,sm);
            Boundry.setPrimitive(Renderable.Primitive.TRIANGLES);
            location.attachObject(Boundry);
        } catch (IOException ex) {
            Logger.getLogger(BoundryObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     protected ManualObject makeWorldBoundry(Engine eng, SceneManager sm)throws IOException{
        ManualObject worldBoundry = sm.createManualObject("WorldBoundry");
        ManualObjectSection pyrSec = worldBoundry.createManualSection("Worldsec");
        worldBoundry.setGpuShaderProgram(sm.getRenderSystem().
            getGpuShaderProgram(GpuShaderProgram.Type.RENDERING));
        
        float [] verticies = new float[] 
        { 
            -400.0f, 0.0f, 400.0f, -400.0f, 0.0f, -400.0f, 400.0f, 0.0f, -400.0f, 
            -400.0f, 0.0f, 400.0f,400.0f, 0.0f,-400.0f, 400.0f, 0.0f, 400.0f,//bottom  
        };
        int i=0;
        while(i<verticies.length){
            verticies[i]=verticies[i]/8f;
            i++;
            
        }
            
        float[] texcoords = new float[]
            { 
                0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f        
            };
        float[] normals = new float[]
            { 
            0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f,//bottom
            };
           
                    
        int[] indices = new int[] { 0,1,2,3,4,5,6 };
            
        FloatBuffer vertBuf = BufferUtil.directFloatBuffer(verticies);
        FloatBuffer texBuf = BufferUtil.directFloatBuffer(texcoords);
        FloatBuffer normBuf = BufferUtil.directFloatBuffer(normals);
        IntBuffer indexBuf = BufferUtil.directIntBuffer(indices);
        
        pyrSec.setVertexBuffer(vertBuf);
        pyrSec.setTextureCoordsBuffer(texBuf);
        pyrSec.setNormalsBuffer(normBuf);
        pyrSec.setIndexBuffer(indexBuf);
        
        ray.rage.asset.texture.Texture tex = eng.getTextureManager().getAssetByPath("hexagons.jpeg");
        TextureState texState = (TextureState)sm.getRenderSystem().
        createRenderState(RenderState.Type.TEXTURE);
        texState.setTexture(tex);
        FrontFaceState faceState = (FrontFaceState) sm.getRenderSystem().
        createRenderState(RenderState.Type.FRONT_FACE);
        faceState.setVertexWinding(FrontFaceState.VertexWinding.CLOCKWISE);
        worldBoundry.setDataSource(Renderable.DataSource.INDEX_BUFFER);
        worldBoundry.setRenderState((RenderState)texState);
        worldBoundry.setRenderState(faceState);
        Material mat =  sm.getMaterialManager().getAssetByPath("earth.mtl");
       
    
        worldBoundry.setMaterial(mat);
        return worldBoundry;
            
     }
}
