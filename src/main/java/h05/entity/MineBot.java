package h05.entity;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.Wall;
import fopbot.World;
import h05.base.game.GameSettings;
import h05.base.mineable.BasicInventory;
import h05.base.mineable.Inventory;
import h05.base.ui.InfoPopup;
import h05.equipment.*;
import h05.mineable.Mineable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.awt.Point;

/**
 * A basic implementation of a mining robot that can mine resources in the world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class MineBot extends Robot implements Miner {

    /**
     * The default capacity of the mine bot, which determines how much equipment it can hold.
     */
    @DoNotTouch
    public static final int DEFAULT_CAPACITY = 4;

    /**
     * The game settings of this mine bot, which provides access to the world and other entities.
     */
    @DoNotTouch
    private final @NotNull GameSettings settings;

    /**
     * The array of equipments that this mine bot can hold, which can include empty slots if it has not been fully
     * equipped.
     */
    @DoNotTouch
    private final @NotNull Equipment[] equipments;

    /**
     * The inventory of this mine bot, which holds the resources it has mined.
     */
    @DoNotTouch
    private final @NotNull Inventory inventory;

    /**
     * The index of the next available slot in the equipments array, which is used to add new equipment.
     */
    @DoNotTouch
    private int nextIndex;

    /**
     * The battery of this mine bot, which determines the lifetime and energy of the bot.
     */
    @DoNotTouch
    private @NotNull Battery battery;

    /**
     * The camera of this mine bot, which determines the visibility range of the bot.
     */
    @DoNotTouch
    private @NotNull Camera camera;

    /**
     * The tool of this mine bot, which can be used to mine resources.
     */
    @DoNotTouch
    private @Nullable Tool tool;

    /**
     * Constructs a new {@link MineBot} instance with the specified position, game settings, and capacity.
     *
     * @param x        the x-coordinate of the mine bot
     * @param y        the y-coordinate of the mine bot
     * @param settings the game settings of this mine bot, which provides access to the world and other entities
     * @param capacity the capacity of this mine bot, which determines how much equipment it can hold
     */
    @DoNotTouch
    public MineBot(int x, int y, @NotNull GameSettings settings, int capacity) {
        super(x, y);
        this.settings = settings;
        this.equipments = new Equipment[capacity];
        this.battery = new Battery();
        this.camera = new Camera();
        this.equipments[0] = this.battery;
        this.equipments[1] = this.camera;
        nextIndex = 2;
        this.inventory = new BasicInventory();
        for (Point point : getVision(x, y)) {
            settings.removeFog(point.x, point.y);
        }
    }

    /**
     * Constructs a new {@link MineBot} instance with the specified position and game settings, using the default
     * capacity of {@value  DEFAULT_CAPACITY}.
     *
     * @param x        the x-coordinate of the mine bot
     * @param y        the y-coordinate of the mine bot
     * @param settings the game settings of this mine bot, which provides access to the world and other entities
     */
    @DoNotTouch
    public MineBot(int x, int y, @NotNull GameSettings settings) {
        this(x, y, settings, DEFAULT_CAPACITY);
    }

    /**
     * Returns all possible coordinates that this miner can see.
     *
     * @param x the x-coordinate of the miner to get the vision for
     * @param y the y-coordinate of the miner to get the vision for
     * @return an array of points representing the vision of the miner
     */
    @StudentImplementationRequired("H5.4.2")
    @Override
    public @NotNull Point[] getVision(int x, int y) {
        int visibleRange = this.getCamera().getVisibilityRange();
        int minX = Math.max(0,x-visibleRange);
        int maxX = Math.min(World.getWidth()-1 , x + visibleRange);
        int minY = Math.max(0,y-visibleRange);
        int maxY = Math.min(World.getHeight()-1 , y + visibleRange);
        int width = maxX-minX+1;
        int length = maxY-minY+1;
        int count = 0;
        Point[] res = new Point[(width)*(length)];
        for(int i = minX; i<= maxX; i++) {
            for(int j = minY; j<= maxY; j++) {
                res[count] = new Point(i, j);
                count++;
            }
        }
        return res;
    }

    /**
     * Updates the vision of the miner based on its new position.
     *
     * @param oldX the old x-coordinate of the miner
     * @param oldY the old y-coordinate of the miner
     * @param newX the new x-coordinate of the miner
     * @param newY the new y-coordinate of the miner
     */
    @StudentImplementationRequired("H5.4.2")
    @Override
    public void updateVision(int oldX, int oldY, int newX, int newY) {
        Point[] oldFog = this.getVision(oldX, oldY);
        Point[] newFog = this.getVision(newX, newY);
        for(Point point : oldFog) {
            settings.placeFog(point.x, point.y);
        }
        for(Point point : newFog) {
            settings.removeFog(point.x, point.y);
        }
    }

    /**
     * The robot will move forward if the battery is not broken.
     * After move, the seenable will update.
     * The battery durability will be reduce = the amount of equipment that the bot has.
     */
    @StudentImplementationRequired("H5.4.3")
    @Override
    public void move() {
        if(this.getBattery().getCondition()==EquipmentCondition.BROKEN){return;}
        int oldX = this.getX();
        int oldY = this.getY();
        super.move();
        int newX = this.getX();
        int newY = this.getY();
        this.updateVision(oldX, oldY, newX, newY);
        this.getBattery().reduceDurability(this.getNumberOfEquipments());
    }

    @DoNotTouch
    @Override
    public @NotNull GameSettings getGameSettings() {
        return settings;
    }

    @DoNotTouch
    @Override
    public Equipment[] getEquipments() {
        Equipment[] equipments = new Equipment[nextIndex];
        System.arraycopy(this.equipments, 0, equipments, 0, nextIndex);
        return equipments;
    }

    @DoNotTouch
    @Override
    public int getNumberOfEquipments() {
        return nextIndex + 2 + (tool == null ? 0 : 1);
    }

    /**
     * Uses the equipment at the specified index of the.
     *
     * @param index the index of the equipment to use
     * @see UsableEquipment
     */
    @StudentImplementationRequired("H5.4.4")
    @Override
    public void use(int index) {
        Equipment[] equipments = this.getEquipments();
        int countOfUsable = 0;
        UsableEquipment foundEquipment = null;
        for(Equipment currEquipment: equipments){
            UsableEquipment tempUsable = settings.toUsableEquipment(currEquipment);
            if(tempUsable != null ){
                if(countOfUsable == index){
                    foundEquipment = tempUsable;
                    break;
                }else{
                    countOfUsable++;
                }
            }
        }
        if(foundEquipment != null){
            foundEquipment.use(this);
            if(foundEquipment.getName().equals("TelephotoLens")){
                this.updateVision(this.getX(),this.getY(),this.getX(),this.getY());
            }
        }
        settings.update();
    }

    @DoNotTouch
    @Override
    public void equip(@NotNull Equipment equipment) {
        if (equipment.getName().equals("Battery")) {
            Battery newBattery = settings.toBattery(equipment);
            if (newBattery != null) {
                battery = newBattery;
                equipments[0] = newBattery;
            }
        } else if (equipment.getName().equals("Camera")) {
            Camera newCamera = settings.toCamera(equipment);
            if (newCamera != null) {
                camera = newCamera;
                equipments[1] = newCamera;
            }
        } else {
            for (int i = 2; i < nextIndex; i++) {
                if (equipments[i].getName().equals(equipment.getName())) {
                    equipments[i] = equipment;
                    return;
                }
            }
            if (equipment.isTool()) {
                tool = settings.toTool(equipment);
                return;
            }
            if (nextIndex + (tool == null ? -1 : 0) == equipments.length) {
                return;
            }
            equipments[nextIndex] = equipment;
            nextIndex++;
        }
    }

    @DoNotTouch
    @Override
    public void unequip(int index) {
        if (index + 2 < nextIndex) {
            return;
        }
        for (int i = index + 2; i < nextIndex - 1; i++) {
            equipments[i] = equipments[i + 1];
        }
        equipments[nextIndex - 1] = null;
        nextIndex--;
    }

    @DoNotTouch
    @Override
    public @NotNull Battery getBattery() {
        return battery;
    }

    @DoNotTouch
    @Override
    public @NotNull Camera getCamera() {
        return camera;
    }

    @DoNotTouch
    @Override
    public @Nullable Tool getTool() {
        return tool;
    }

    @DoNotTouch
    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    /**
     * Performs a mining action in the direction this miner is facing if there is a mineable entity in front of it.
     */
    @StudentImplementationRequired("H5.4.1")
    @Override
    public void mine() {
        Direction direction = this.getDirection();
        Wall wall;
        Mineable mineable;
        //Check if the robot can use mine or not
        if(direction == Direction.UP) {
            if(this.getY()+1 >= World.getHeight()){return;}
            wall = settings.getWallAt(this.getX(), this.getY(), true);
            mineable = settings.getLootAt(this.getX(), this.getY() + 1);
            if (wall != null || mineable == null) {return;}
        }else if(direction == Direction.DOWN){
            if(this.getY()-1 < 0){return;}
            wall = settings.getWallAt(this.getX(), this.getY()-1, true);
            mineable = settings.getLootAt(this.getX(), this.getY()-1);
            if(wall != null || mineable == null) {return;}
        }else if(direction == Direction.LEFT){
            if(this.getX()-1 < 0){return;}
            wall = settings.getWallAt(this.getX()-1, this.getY(), false);
            mineable = settings.getLootAt(this.getX()-1,this.getY());
            if(wall != null || mineable == null) {return;}
        }else{
            if(this.getX()+1 >= World.getWidth()){return;}
            wall = settings.getWallAt(this.getX(), this.getY(), false);
            mineable = settings.getLootAt(this.getX()+1, this.getY());
            if(wall != null || mineable == null) {return;}
        }
        //check if it finished mining
        boolean status = mineable.onMined(this.getTool());
        boolean looted;
        if(status){
            //check if it is able to loot the mineable
            looted = this.getInventory().add(mineable);
            if(!looted){
                this.crash();
            }
        }

    }

    @DoNotTouch
    @Override
    public void pickGear() {
        int x = getX();
        int y = getY();
        Equipment equipment = settings.getAndRemoveGearAt(x, y);
        if (equipment != null) {
            Tool oldTool = tool;
            equip(equipment);
            if (equipment.isTool() && oldTool != null) {
                settings.placeGearAt(x, y, oldTool);
            }
        }
    }

    @DoNotTouch
    @Override
    public void handleKeyInput(
        @Nullable Direction direction,
        int selection,
        boolean isPickingGear,
        boolean isMining,
        boolean isInfo
    ) {
        if (direction != null) {
            while (getDirection() != direction) {
                turnLeft();
            }
            if (isFrontClear()) {
                move();
            }
        }
        if (selection != -1) {
            use(selection - 1);
        }
        if (isPickingGear) {
            pickGear();
        }
        if (isMining) {
            mine();
        }
        if (isInfo) {
            InfoPopup.showInfo(inventory);
        }
    }
}
