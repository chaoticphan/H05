package h05.equipment;

import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;

/**
 * Usable equipment that allows the miner to store power
 */
public class Powerbank implements UsableEquipment {

    /**
     * Attribute keeping count the capacity of the power bank
     */
    private final double capacity;

    /**
     *Create a new Attribute to keep count this powerbank durability
     */
    private double durability;

    /**
     * Create a new powerbank that has a durability and inherited all the methods of UsableEquipment
     *
     * @param capacity keeping count the capacity of the power bank
     */
    public Powerbank(double capacity) {
        this.capacity = capacity;
        this.setDurability(100);
    }

    /**
     * Return the capacity of the powerbank
     *
     * @return capacity of the powerbank
     */
    public double getCapacity() {
        return this.capacity;
    }

    /**
     * Returns the name of this equipment.
     *
     * @return the name of this equipment
     */
    @Override
    public @NotNull String getName() {
        return "Powerbank";
    }

    /**
     * Returns the condition of this powerbank.
     *
     * Return NEW if durability between 100 and 81.
     * Return used if durability between 80 and 41.
     * Return damaged if durability between 40 and 1.
     * Return broken if durability = 0.
     *
     * @return the condition of this powerbank
     */
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
     * Getter methods for to get the durability of this powerbank
     *
     * @return the current durability of this powerbank
     */
    @Override
    public double getDurability() {
        return this.durability;
    }

    /**
     * Sets the durability of this powerbank to the specified value
     *
     * The durability must be inbetween 0 and 100, if its bigger than 100,
     * the durability will be 100 and if its smaller than 0, the durability
     * will be 100
     *
     * @param durability the new durability value for this powerbank
     */
    @Override
    public void setDurability(double durability) {
        this.durability = (durability > 100) ? 100
                        : (durability < 0) ? 0
                        : durability;
    }

    /**
     * Reduces the durability of this powerbank by the specified amount
     *
     * If the resulting durability is less than 0, it will be set to 0
     *
     * @param amount the amount to reduce the durability by
     */
    @Override
    public void reduceDurability(double amount) {
        double currDurability = this.getDurability();
        double afterDurability = currDurability - amount;
        this.setDurability(afterDurability);
    }

    /**
     * Uses this powerbank with the given miner.
     *
     * If the powerbank is not broken, charge the battery the new
     * will be reduced by half of it capacity
     *
     * @param miner the miner using this powerbank
     */
    @Override
    public void use(@NotNull Miner miner) {
        if(this.getCondition()!= EquipmentCondition.BROKEN){
            miner.getBattery().setDurability(100);
            double halfCapacity = this.getCapacity() / 2;
            this.reduceDurability(halfCapacity);
        }
    }

}
