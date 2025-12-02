package h05.entity;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.RobotFamily;
import fopbot.World;
import h05.Durable;
import h05.base.game.GameSettings;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.Equipment;
import h05.equipment.EquipmentCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.awt.Point;

/**
 * A skeleton implementation of the {@link Durable} interface used to simplify the implementation of
 * repair bot in the world which only differ in the way they move to the repair location.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public abstract class AbstractRepairBot extends Robot implements Repairer {

    /**
     * The game settings of this repair bot, which provides access to the world and other entities.
     */
    @DoNotTouch
    private final @NotNull GameSettings settings;

    /**
     * The radius of this repair bot, which determines how far it can scan for entities to repair.
     */
    @DoNotTouch
    private final int radius;

    /**
     * Constructs a new {@link AbstractRepairBot} instance with the specified position, game settings, and radius.
     *
     * @param x        the x-coordinate of the repair bot
     * @param y        the y-coordinate of the repair bot
     * @param settings the game settings of this repair bot, which provides access to the world and other entities
     * @param radius   the radius of this repair bot, which determines how far it can scan for entities to repair
     */
    @DoNotTouch
    public AbstractRepairBot(int x, int y, @NotNull GameSettings settings, int radius) {
        super(x, y, Direction.UP, 0, RobotFamily.SQUARE_RED);
        this.settings = settings;
        this.radius = radius;
    }

    @DoNotTouch
    @Override
    public @NotNull GameSettings getGameSettings() {
        return settings;
    }

    @DoNotTouch
    @Override
    public int getRadius() {
        return radius;
    }

    /**
     * Scans the area around this repairer to find an entity that can be repaired.
     *
     * @return the point of the entity to be repaired, or {@code null} if no entity can be repaired
     */
    @StudentImplementationRequired("H5.5")
    @Override
    public @Nullable Point scan() {
        int minX = Math.max(0,this.getX()-this.getRadius());
        int maxX = Math.min(World.getWidth()-1, this.getX()+this.getRadius());
        int minY = Math.max(0,this.getY()-this.getRadius());
        int maxY = Math.min(World.getHeight()-1, this.getY()+this.getRadius());
        for(int i = minX; i <= maxX; i++){
            for(int j = minY; j <= maxY; j++){
                if(this.getGameSettings().getMinerAt(i,j)!=null ){
                    Miner miner = this.getGameSettings().getMinerAt(i,j);
                    Equipment[] equipments = miner.getEquipments();
                    if(equipments!=null ){
                        for(Equipment item : equipments){
                            if(item!= null && item.getCondition() == EquipmentCondition.BROKEN){
                                return new Point(i,j);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Repairs the entity at the specified point if it is within the repairer's radius and can be repaired.
     *
     * <p>A broken battery or camera will be replaced by a new one and all broken usable equipment will be removed.
     *
     * @param point the point of the entity to be repaired
     */
    @StudentImplementationRequired("H5.5")
    @Override
    public void repair(@NotNull Point point) {
        this.move(point);
        Miner currMiner = this.getGameSettings().getMinerAt(point.x, point.y);
        if (currMiner == null) {
            return;
        }
        Equipment[] inventory = currMiner.getEquipments();
        if (inventory == null || inventory.length == 0) {
            return;
        }
        for (int i = inventory.length - 1; i >= 0; i--) {
            if (inventory[i] == null) {
                continue;
            }else if (inventory[i].getCondition() == EquipmentCondition.BROKEN) {
                if (inventory[i].getName().equals("Battery")) {
                    currMiner.equip(new Battery());
                } else if (inventory[i].getName().equals("Camera")) {
                    currMiner.equip(new Camera());
                } else {
                    currMiner.unequip(i);

                }
            }
        }
    }

    /**
     * Moves this repair bot to the specified point in the world.
     *
     * @param point the point to move to
     */
    @DoNotTouch
    protected abstract void move(@NotNull Point point);
}
