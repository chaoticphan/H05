package h05.equipment;

import h05.base.AbstractEquipment;
import h05.mineable.Rock;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A camera is a type of equipment that defines the vision of an entity.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class Camera extends AbstractEquipment implements Equipment {
    /**
     * Create a new Attribute to keep count this camera durability and set it = 100
     */
    private double durability = 100;
    /**
     * The default visibility range of the camera.
     */
    @DoNotTouch
    public static final int DEFAULT_VISIBILITY_RANGE = 1;

    @DoNotTouch
    private int visibilityRange;

    /**
     * Constructs a new {@link Camera} instance with the specified visibility range.
     *
     * @param visibilityRange the visibility range of the camera, must be at least 1
     */
    @DoNotTouch
    public Camera(int visibilityRange) {
        this.visibilityRange = visibilityRange;
    }

    /**
     * Constructs a new {@link Camera} instance with the default visibility range of {@value DEFAULT_VISIBILITY_RANGE}.
     */
    @DoNotTouch
    public Camera() {
        this(DEFAULT_VISIBILITY_RANGE);
    }

    /**
     * Returns the visibility range of this camera, which defines how far an entity attached to this camera can see.
     *
     * @return the visibility range of this camera
     */
    @StudentImplementationRequired("H5.2.2")
    public int getVisibilityRange() {
        EquipmentCondition currCondition = this.getCondition();
        if(currCondition == EquipmentCondition.DAMAGED){
            return this.visibilityRange/2;
        }else if(currCondition == EquipmentCondition.BROKEN){
            return 0;
        }else{
            return this.visibilityRange;
        }
    }

    /**
     * Sets the visibility range of this camera, which defines how far an entity attached to this camera can see.
     *
     * <p>The visibility range must be at least 1. If a value less than 1 is provided, it will be set to 1.
     *
     * @param visibilityRange the new visibility range for this camera
     */
    @StudentImplementationRequired("H5.2.2")
    public void setVisibilityRange(int visibilityRange) {
        this.visibilityRange = (visibilityRange > 1) ? visibilityRange : 1;
    }

    /**
     * Returns the name of this equipment.
     *
     * @return the name of this equipment
     */
    @Override
    @StudentImplementationRequired("H5.2")
    public @NotNull String getName() {
        return "Camera";
    }

    /**
     * Returns the condition of this camera.
     * Return NEW if durability between 100 and 81.
     * Return used if durability between 80 and 41.
     * Return damaged if durability between 40 and 1.
     * Return broken if durability = 0.
     * @return the condition of this camera
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

    @Override
    @DoNotTouch
    public boolean isUsable() {
        return false;
    }

    @Override
    @DoNotTouch
    public boolean isTool() {
        return false;
    }

    /**
     * Getter methods for to get the durability of this camera
     *
     * @return the current durability of this camera
     */
    @Override
    @StudentImplementationRequired("H5.1")
    public double getDurability() {
        return this.durability;
    }

    /**
     * Sets the durability of this camera to the specified value
     *
     * The durability must be inbetween 0 and 100, if its bigger than 100,
     * the durability will be 100 and if its smaller than 0, the durability
     * will be 100
     *
     * @param durability the new durability value for this camera
     */
    @Override
    @StudentImplementationRequired("H5.1")
    public void setDurability(double durability) {
        this.durability = (durability > 100) ? 100
            : (durability < 0) ? 0
            : durability;
    }

    /**
     * Reduces the durability of this camera by the specified amount
     *
     * If the resulting durability is less than 0, it will be set to 0
     *
     * @param amount the amount to reduce the durability by
     */
    @Override
    @StudentImplementationRequired("H5.1")
    public void reduceDurability(double amount) {
        double currDurability = this.getDurability();
        double afterDurability = currDurability - amount;
        this.setDurability(afterDurability);
    }
}
