package h05.equipment;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Wall;
import fopbot.World;
import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Usable equipment that allows the miner entity to break walls in the world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class WallBreaker implements UsableEquipment {
    /**
     * Create a new Attribute to keep count this WallBreaker durability
     */
    private double durability;

    /**
     * Constructs a new {@link WallBreaker} instance.
     */
    public WallBreaker() {
        this.setDurability(100);
    }

    /**
     * The use method for the miner to destroy walls.
     * If the wall breaker is broken or there are no wall at the place he is looking, it wont be use.
     * In case there are walls:
     * If he is looking up or right, then the wall belongs to his field
     * If he is looking down, the wall belongs to the field below
     * If he is looking left, the wall belongs to the left field
     *
     * @param miner the miner using this equipment
     */
    @StudentImplementationRequired("H5.2.5")
    @Override
    public void use(@NotNull Miner miner) {
        if(this.getCondition() == EquipmentCondition.BROKEN){return;}
        Direction direction = miner.getDirection();
        if(direction == Direction.UP){
            if(miner.getY()+1 >= World.getHeight()){return;}
            Wall wall = miner.getGameSettings().getWallAt(miner.getX(), miner.getY(), true);
            if (wall ==null){
                return;
            }else{
                miner.getGameSettings().removeEntity(wall);
            }
        }else if (direction == Direction.DOWN){
            if(miner.getY()-1 < 0){return;}
            Wall wall = miner.getGameSettings().getWallAt(miner.getX(), miner.getY()-1, true);
            if (wall ==null){
                return;
            }else{
                miner.getGameSettings().removeEntity(wall);
            }
        }else if (direction == Direction.LEFT){
            if(miner.getX()-1 < 0){return;}
            Wall wall = miner.getGameSettings().getWallAt(miner.getX()-1, miner.getY(), false);
            if (wall ==null){
                return;
            }else{
                miner.getGameSettings().removeEntity(wall);
            }
        }else{
            if(miner.getX()+1 >= World.getWidth()){return;}
            Wall wall = miner.getGameSettings().getWallAt(miner.getX(), miner.getY(), false);
            if (wall ==null){
                return;
            }else{
                miner.getGameSettings().removeEntity(wall);
            }
        }
        this.reduceDurability(100);
    }

    /**
     * Returns the name of this equipment.
     *
     * @return the name of this equipment
     */
    @StudentImplementationRequired("H5.2")
    @Override
    public @NotNull String getName() {
        return "WallBreaker";
    }

    /**
     * Returns the condition of this wallbreaker.
     * Return NEW if durability between 100 and 81.
     * Return used if durability between 80 and 41.
     * Return damaged if durability between 40 and 1.
     * Return broken if durability = 0.
     * @return the condition of this wallbreaker
     */
    @StudentImplementationRequired("H5.2.1")
    @Override
    public @NotNull EquipmentCondition getCondition() {
        if(this.getDurability() > 80){
            return EquipmentCondition.NEW;
        }else if(this.getDurability() > 40){
            return EquipmentCondition.USED;
        }else if(this.getDurability() > 0){
            return EquipmentCondition.DAMAGED;
        }else{
            return EquipmentCondition.BROKEN;
        }
    }

    /**
     * Getter methods for to get the durability of this wallbreaker
     *
     * @return the current durability of this wallbreaker
     */
    @StudentImplementationRequired("H5.1")
    @Override
    public double getDurability() {
        return this.durability;
    }

    /**
     * Sets the durability of this wallbreaker to the specified value
     *
     * The durability must be inbetween 0 and 100, if its bigger than 100,
     * the durability will be 100 and if its smaller than 0, the durability
     * will be 100
     *
     * @param durability the new durability value for this rock
     */
    @StudentImplementationRequired("H5.1")
    @Override
    public void setDurability(double durability) {
        this.durability = (durability > 100) ? 100
            : (durability < 0) ? 0
            : durability;
    }

    /**
     * Reduces the durability of this wallbreakeer by the specified amount
     *
     * If the resulting durability is less than 0, it will be set to 0
     *
     * @param amount the amount to reduce the durability by
     */
    @StudentImplementationRequired("H5.1")
    @Override
    public void reduceDurability(double amount) {
        double currDurability = this.getDurability();
        double afterDurability = currDurability - amount;
        this.setDurability(afterDurability);
    }
}
