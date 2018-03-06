/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManualObjects;

import java.awt.Color;
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
public class BoxObject {
    public BoxObject(SceneNode location, Engine eng, SceneManager sm){
        try {
            ManualObject box = makeScoreObject(eng,sm);
            box.setPrimitive(Renderable.Primitive.TRIANGLES);
            location.attachObject(box);
        } catch (IOException ex) {
            Logger.getLogger(BoxObject.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        box.setDataSource(Renderable.DataSource.INDEX_BUFFER);
        box.setRenderState((RenderState)texState);
        box.setRenderState(faceState);
        Material mat =  sm.getMaterialManager().getAssetByPath("default.mtl");
        mat.setEmissive(Color.YELLOW);
    
        box.setMaterial(mat);
        return box;
            
     }
}
