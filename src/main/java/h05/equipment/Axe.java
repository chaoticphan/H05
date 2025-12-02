package h05.equipment;

import org.jetbrains.annotations.NotNull;

public class Axe implements Tool {
    /**
     *Attribute to keep count this axe durability
     */
    private double durability;

    /**
     * Create a new axe that has a durability and inherited all the methods of UsableEquipment
     */
    public Axe() {
        this.setDurability(100);
    }

    /**
     * Returns the mining power of this axe.
     *
     * @return the mining power of this axe
     */
    @Override
    public double getMiningPower(){
        return 5;
    }

    /**
     * Returns the name of this equipment.
     *
     * @return the name of this equipment
     */
    @Override
    public @NotNull String getName() {
        return "Axe";
    }

    /**
     * Returns the condition of this Axe.
     *
     * Return NEW if durability between 100 and 81.
     * Return used if durability between 80 and 41.
     * Return damaged if durability between 40 and 1.
     * Return broken if durability = 0.
     *
     * @return the condition of this Axe
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
     * Getter methods for to get the durability of this axe
     *
     * @return the current durability of this axe
     */
    @Override
    public double getDurability() {
        return this.durability;
    }

    /**
     * Sets the durability of this axe to the specified value
     *
     * The durability must be inbetween 0 and 100, if its bigger than 100,
     * the durability will be 100 and if its smaller than 0, the durability
     * will be 100
     *
     * @param durability the new durability value for this rock
     */
    @Override
    public void setDurability(double durability) {
        this.durability = (durability > 100) ? 100
                        : (durability < 0) ? 0
                        : durability;
    }

    /**
     * Reduces the durability of this pickaxe by the specified amount
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
}
