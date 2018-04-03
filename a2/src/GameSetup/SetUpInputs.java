/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSetup;

import myGameEngine.PlayerBuckController;
import a2.MyGame;
import java.util.Iterator;
import myGameEngine.PlayerBuckAction;
import net.java.games.input.Controller;
import ray.input.InputManager;
import myGameEngine.PlayerMovementActions;
import ray.input.action.Action;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;

/**
 *
 * @author Joe
 */
public class SetUpInputs {
    private InputManager im;
    private SceneNode playerOneNode,playerTwoNode;
    private Camera playerOneView,playerTwoView;
    private MyGame game;
    private Action pmkb,pmcon;
    private Controller kb,controller;
    private PlayerBuckController ps1,ps2;
      public SetUpInputs(InputManager im,MyGame g,SceneNode p1n, Camera p1c,SceneNode p2n,Camera p2c){
        this.im = im;
        this.ps1 = ps1;
        this.ps2=ps2;
        playerOneNode = p1n;
        playerOneView = p1c;
        playerTwoNode = p2n;
        playerTwoView = p2c;
        game=g;
        setupPlayerOneController();
        if(im.getFirstGamepadName()!=null){
            setupPlayerTwoController();
        } 
    }
    public Controller getKeboard(){
        return kb;
    }
    public Controller getGamepad(){
        return controller;
    }
    public PlayerBuckController getPB1(){
        return ps1;
    }
    public PlayerBuckController getPB2(){
        return ps2;
    }
    private void setupPlayerOneController(){
        
        Iterator<Controller> itr = im.getControllers().iterator();
        //itr.next();
        //itr.next();
        kb = itr.next();
        // create actions for inputs 
        pmkb = new PlayerMovementActions(playerOneView,playerOneNode,game);
        ps1 = new PlayerBuckController(pmkb);
        Action playerHit = new PlayerBuckAction(ps1);
        //player2 inputs
       
         //player 1 inputs
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.W, pmkb, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.A, pmkb, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.S, pmkb, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.LEFT, pmkb, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.RIGHT, pmkb, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.UP, pmkb, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.DOWN, pmkb, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.D, pmkb, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.SPACE, playerHit, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        
    }
    private void setupPlayerTwoController(){
        pmcon  = new PlayerMovementActions(playerTwoView,playerTwoNode,game);
        ps2 = new PlayerBuckController(pmcon);
        Action playerHit = new PlayerBuckAction(ps2);
        controller = im.getGamepadController(1);
        if(controller!=null){
          im.associateAction(controller, net.java.games.input.Component.Identifier.Axis.RX, pmcon, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
          im.associateAction(controller, net.java.games.input.Component.Identifier.Axis.RY, pmcon, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
          im.associateAction(controller, net.java.games.input.Component.Identifier.Axis.X, pmcon, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
          im.associateAction(controller, net.java.games.input.Component.Identifier.Axis.Y, pmcon, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
          im.associateAction(controller, net.java.games.input.Component.Identifier.Button._0, playerHit, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        }else
            System.out.println("plug a controller in");
    }
}
