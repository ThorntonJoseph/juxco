/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSetup;

import a2.MyGame;
import myGameEngine.PlayerBuckController;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3f;

/**
 *
 * @author Joe
 */
public class BallControl  {
    private SceneNode ball;
    private Vector3f gravity =(Vector3f) Vector3f.createFrom(0f, -.001f, 0f);
    private Vector3f velocity = (Vector3f)Vector3f.createFrom(0f, 0f, 0f);
    private MyGame game;
    public BallControl(PlayerBuckController bc1,PlayerBuckController bc2, SceneNode ball,MyGame game){
        this.ball=ball;
        this.game=game;
    }
    public void update(){
        System.out.println(game.getdeltatime()/100f);
        if(ball.getLocalPosition().y()<=1.5f){
            ball.setLocalPosition(ball.getLocalPosition().);
        }else if(ball.getLocalPosition().y()>1.5f){
            velocity = (Vector3f) velocity.add(gravity.mult(game.getdeltatime()/100));
            ball.setLocalPosition(ball.getLocalPosition().add(velocity));
        }
    }
    
}
