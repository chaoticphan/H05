package h05;

import fopbot.World;
import h05.base.entity.Gear;
import h05.base.entity.Loot;
import h05.base.game.GameLoopBase;
import h05.entity.MineBot;
import h05.entity.TeleportRepairBot;
import h05.entity.WallBreakerRepairBot;
import h05.equipment.*;
import h05.mineable.Rock;
import h05.mineable.Tree;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * The game loop for simulating the MineBot world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class GameLoop extends GameLoopBase {

    /**
     * Constructs a new {@link GameLoop} instance for simulating the MineBot world.
     */
    @DoNotTouch
    public GameLoop() {
    }

    /**
     * Sets up the world with specific entities and structures after its initialization.
     */
    @StudentImplementationRequired("H5.6")
    @Override
    protected void setupWorld() {
        //Horizontal walls
        World.placeHorizontalWall(0,0);
        //y=1
        for(int x = 4; x<=6; x++){
            World.placeHorizontalWall(x,1);
        }
        //y=2
        World.placeHorizontalWall(1,2);
        for(int x = 3; x<=5; x++){
            World.placeHorizontalWall(x,2);
        }
        //y=3
        World.placeHorizontalWall(0,3);
        World.placeHorizontalWall(2,3);
        World.placeHorizontalWall(4,3);
        World.placeHorizontalWall(5,3);
        //y=4
        World.placeHorizontalWall(1,4);
        World.placeHorizontalWall(2,4);
        World.placeHorizontalWall(3,4);
        World.placeHorizontalWall(5,4);
        World.placeHorizontalWall(6,4);
        //y=5
        World.placeHorizontalWall(0,5);
        World.placeHorizontalWall(1,5);
        World.placeHorizontalWall(2,5);
        World.placeHorizontalWall(4,5);
        World.placeHorizontalWall(5,5);
        //Vertical walls
        World.placeVerticalWall(1,0);
        //y=1
        World.placeVerticalWall(4,0);
        for(int x = 0; x<=3; x++){
            World.placeVerticalWall(x,1);
        }
        World.placeVerticalWall(5,1);
        World.placeVerticalWall(5,1);
        //y=2
        World.placeVerticalWall(2,2);
        //y=3
        World.placeVerticalWall(1,3);
        World.placeVerticalWall(2,3);
        World.placeVerticalWall(5,3);
        //y=4
        World.placeVerticalWall(1,4);
        World.placeVerticalWall(3,4);
        //y=5
        World.placeVerticalWall(3,5);

        Rock rock1 = new Rock();
        World.getGlobalWorld().placeFieldEntity(new Loot(0,0, rock1));
        Rock rock2 = new Rock();
        World.getGlobalWorld().placeFieldEntity(new Loot(3,0, rock2));
        Rock rock3 = new Rock();
        World.getGlobalWorld().placeFieldEntity(new Loot(6,5, rock3));
        Tree tree1 = new Tree();
        World.getGlobalWorld().placeFieldEntity(new Loot(6,0, tree1));
        Tree tree2 = new Tree();
        World.getGlobalWorld().placeFieldEntity(new Loot(0,3, tree2));
        Tree tree3 = new Tree();
        World.getGlobalWorld().placeFieldEntity(new Loot(3,4, tree3));
        Pickaxe pickaxe = new Pickaxe();
        World.getGlobalWorld().placeFieldEntity(new Gear(2,0,pickaxe));
        WallBreaker wallBreaker1 = new WallBreaker();
        World.getGlobalWorld().placeFieldEntity(new Gear(5,0, wallBreaker1));
        WallBreaker wallBreaker2 = new WallBreaker();
        World.getGlobalWorld().placeFieldEntity(new Gear(6,6, wallBreaker2));
        TelephotoLens lens1 = new TelephotoLens(1);
        World.getGlobalWorld().placeFieldEntity(new Gear(2,2,lens1));
        TelephotoLens lens2 = new TelephotoLens(1);
        World.getGlobalWorld().placeFieldEntity(new Gear(5,3,lens2));
        Powerbank powerbank1 = new Powerbank(200);
        World.getGlobalWorld().placeFieldEntity(new Gear(6,4, powerbank1));
        Powerbank powerbank2 = new Powerbank(200);
        World.getGlobalWorld().placeFieldEntity(new Gear(0,6,powerbank2));
        Axe axe = new Axe();
        World.getGlobalWorld().placeFieldEntity(new Gear(6,3,axe));
    }

    /**
     * Initializes the robots in the game, adding specific robot types to the world.
     */
    @StudentImplementationRequired("H5.6")
    @Override
    protected void initRobots() {
        MineBot miner = new MineBot(1,0,getGameSettings());
        WallBreakerRepairBot bombHelper = new WallBreakerRepairBot(3,2,getGameSettings(),6);
        TeleportRepairBot tpHelper = new TeleportRepairBot(4,3,getGameSettings(),6);
    }
}
