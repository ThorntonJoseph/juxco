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
public class WorldCoords {
    public WorldCoords(SceneNode location,Engine eng,SceneManager sm){
        try {
            //create coord objects
            ManualObject xcoord = makeWorldCoordX(eng,sm);
            ManualObject ycoord =  makeWorldCoordY(eng,sm);
            ManualObject zcoord =  makeWorldCoordZ(eng,sm);
            // set drawing style
            xcoord.setPrimitive(Renderable.Primitive.LINES);
            ycoord.setPrimitive(Renderable.Primitive.LINES);
            zcoord.setPrimitive(Renderable.Primitive.LINES);
            // attach to node
            location.attachObject(zcoord);
            location.attachObject(xcoord);
            location.attachObject(ycoord);
        } catch (IOException ex) {
            Logger.getLogger(WorldCoords.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected ManualObject makeWorldCoordY(Engine eng, SceneManager sm)throws IOException{
        ManualObject coord = sm.createManualObject("coordY");
        ManualObjectSection coordSec = coord.createManualSection("coordSec");
        coord.setGpuShaderProgram(sm.getRenderSystem()
                 .getGpuShaderProgram(GpuShaderProgram.Type.RENDERING));
        float[] verticies = new float[] {0.0f,0.0f,0.0f,
         0.0f,1.0f,0.0f };
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
        
        Material mat =  sm.getMaterialManager().createManualAsset("YaxisM");
        mat.setEmissive(Color.YELLOW);
     
        TextureState texState = (TextureState)sm.getRenderSystem().
        createRenderState(RenderState.Type.TEXTURE);
        coord.setDataSource(Renderable.DataSource.VERTEX_BUFFER);
        coord.setRenderState((RenderState)texState);
        coord.setMaterial(mat);
        return coord;
     }
     protected ManualObject makeWorldCoordZ(Engine eng, SceneManager sm)throws IOException{
        ManualObject coord = sm.createManualObject("coordZ");
        ManualObjectSection coordSec = coord.createManualSection("coordSec");
        coord.setGpuShaderProgram(sm.getRenderSystem()
                 .getGpuShaderProgram(GpuShaderProgram.Type.RENDERING));
        float[] verticies = new float[] {0.0f,0.0f,0.0f,
         0.0f,0.0f,1.0f };
        float[] texcoords = new float[] {0.0f,0.0f,0.0f,1.0f};
        float[] normals = new float[] {0.0f,1.0f,0.0f,
         0.0f,1.0f,0.0f,};
        int[] indices = new int[] {1,2};  
        
        FloatBuffer vertBuf = BufferUtil.directFloatBuffer(verticies);
        FloatBuffer normBuf = BufferUtil.directFloatBuffer(normals);
        FloatBuffer texBuf = BufferUtil.directFloatBuffer(texcoords);
        IntBuffer indexBuf = BufferUtil.directIntBuffer(indices);
        
        coordSec.setVertexBuffer(vertBuf);
        coordSec.setTextureCoordsBuffer(texBuf);
        coordSec.setNormalsBuffer(normBuf);
        coordSec.setIndexBuffer(indexBuf);
        
        Material mat =  sm.getMaterialManager().createManualAsset("ZaxisM");
        mat.setEmissive(Color.BLUE);
        TextureState texState = (TextureState)sm.getRenderSystem().
        createRenderState(RenderState.Type.TEXTURE);
        coord.setDataSource(Renderable.DataSource.VERTEX_BUFFER);
        coord.setRenderState((RenderState)texState);
        coord.setMaterial(mat);
        
        return coord;
     }
     protected ManualObject makeWorldCoordX(Engine eng, SceneManager sm)throws IOException{
        ManualObject coord = sm.createManualObject("coordX");
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
        
        Material mat =  sm.getMaterialManager().createManualAsset("XaxisM");
        mat.setEmissive(Color.RED);
        TextureState texState = (TextureState)sm.getRenderSystem().
        createRenderState(RenderState.Type.TEXTURE);
        coord.setDataSource(Renderable.DataSource.VERTEX_BUFFER);
        coord.setRenderState((RenderState)texState);
        coord.setMaterial(mat);
        
        return coord;
     }
    
}
