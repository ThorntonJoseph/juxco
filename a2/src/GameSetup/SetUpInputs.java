/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSetup;

import a2.MyGame;
import java.util.Iterator;
import myGameEngine.PlayerMovementActions;
import myGameEngine.ToggleCameraAction;
import myGameEngine.ToggleCameraAction;
import net.java.games.input.Controller;
import ray.input.GenericInputManager;
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
    private Controller kb,controller;
      public SetUpInputs(InputManager im,MyGame g,SceneNode p1n, Camera p1c,SceneNode p2n,Camera p2c){
        this.im = im;
        playerOneNode = p1n;
        playerOneView = p1c;
        playerTwoNode = p2n;
        playerTwoView = p2c;
        game=g;
        setupPlayerOneController();
        setupPlayerTwoController();
    }
    public Controller getKeboard(){
        return kb;
    }
    public Controller getGamepad(){
        return controller;
    }
    private void setupPlayerOneController(){
        
        Iterator<Controller> itr = im.getControllers().iterator();
        kb = itr.next();
        kb=itr.next();
        kb=itr.next();

        // create actions for inputs 
        Action playerMovement = new PlayerMovementActions(playerOneView,playerOneNode,game);
        Action toggleCamera = new ToggleCameraAction(playerOneView,playerOneNode);
       
        //player2 inputs
       
         //player 1 inputs
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.W, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.A, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.S, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.LEFT, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.RIGHT, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.UP, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.DOWN, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateAction(kb, net.java.games.input.Component.Identifier.Key.D, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    }
    private void setupPlayerTwoController(){
        Action playerMovement = new PlayerMovementActions(playerTwoView,playerTwoNode,game);
        controller = im.getGamepadController(1);
        if(controller!=null){
          im.associateAction(controller, net.java.games.input.Component.Identifier.Axis.RX, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
          im.associateAction(controller, net.java.games.input.Component.Identifier.Axis.RY, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
          im.associateAction(controller, net.java.games.input.Component.Identifier.Axis.X, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
          im.associateAction(controller, net.java.games.input.Component.Identifier.Axis.Y, playerMovement, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        }else
            System.out.println("plug a controller in");
    }
}
