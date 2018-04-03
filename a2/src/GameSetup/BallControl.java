/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSetup;

import a2.MyGame;
import myGameEngine.PlayerBuckController;
import myGameEngine.PlayerMovementActions;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3;
import ray.rml.Vector3f;

/**
 *
 * @author Joe
 */
public class BallControl  {
    private SceneNode ball;
    private Vector3f gravity =(Vector3f) Vector3f.createFrom(0f, -.0001f, 0f);
    private Vector3f velocity = (Vector3f)Vector3f.createFrom(0f, 0f, 0f);
    private MyGame game;
    PlayerBuckController bc1,bc2;
    SceneNode playerone,playertwo;
    public BallControl(PlayerBuckController bc1,PlayerBuckController bc2, SceneNode ball,MyGame game,SceneNode playerOne,SceneNode playerTwo){
        this.ball=ball;
        this.game=game;
        this.bc1=bc1;
        this.bc2=bc2;
        this.playerone = playerOne;
        this.playertwo = playerTwo;
    }
    public void update(){
        
        if(ball.getLocalPosition().y()<=1.5f){
            velocity = (Vector3f)Vector3f.createFrom(velocity.x(), Math.abs(velocity.y()) * 0.5f, velocity.z());
            Vector3 p = ball.getLocalPosition();
            ball.setLocalPosition(Vector3f.createFrom(p.x(), 1.5001f, p.z()));
            ball.setLocalPosition(ball.getLocalPosition().add(velocity));
        }else if(ball.getLocalPosition().y()>1.5f){
            velocity = (Vector3f) velocity.add(gravity.mult(game.getdeltatime()/1000f));
            ball.setLocalPosition(ball.getLocalPosition().add(velocity));
        }
        if(bc1.getenabled() == true && PlayerMovementActions.distance((Vector3f)ball.getLocalPosition(),(Vector3f)playerone.getLocalPosition())<4f){
            Vector3 p1 = playerone.getWorldForwardAxis().normalize();
            velocity = (Vector3f) Vector3f.createFrom(p1.x()*1f, p1.y()*1f, p1.z()*1f);
            
        }
    }
    
}
