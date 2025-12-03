package h05.equipment;

import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;

/**
 * Usable equipment that allows the miner to see further
 */
public class TelephotoLens implements UsableEquipment {
    /**
     * Attribute keeping count the distance that the miner can see further
     */
    private final int rangeEnhancement;

    /**
     *Create a new Attribute to keep count this lens durability
     */
    private double durability;

    /**
     * Create a new TelephotoLens to help the miner see better
     * @param rangeEnhancement keeping count the distance that the miner can see further
     */
    public TelephotoLens(int rangeEnhancement) {
        this.rangeEnhancement = rangeEnhancement;
        this.setDurability(100);
    }

    /**
     * return the range enchancement
     *
     * @return the range enchancement
     */
    public int getRangeEnhancement() {
        return this.rangeEnhancement;
    }

    /**
     * Uses this lens with the given miner
     *
     * After use, the camera will get the additional range enhancement
     * while the lens will be broken
     *
     * @param miner the miner using this equipment
     */
    @Override
    public void use(@NotNull Miner miner) {
        if(this.getCondition()!=EquipmentCondition.BROKEN){
            int currRange = miner.getCamera().getVisibilityRange();
            int newRange = currRange+this.getRangeEnhancement();
            miner.getCamera().setVisibilityRange(newRange);
            this.setDurability(0);
        }
    }

    /**
     * Returns the name of this equipment.
     *
     * @return the name of this equipment
     */
    @Override
    public @NotNull String getName() {
        return "TelephotoLens";
    }

    /**
     * Returns the condition of this lens.
     * Return NEW if durability between 100 and 81.
     * Return used if durability between 80 and 41.
     * Return damaged if durability between 40 and 1.
     * Return broken if durability = 0.
     * @return the condition of this lesn
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
     * Getter methods for to get the durability of this lens
     *
     * @return the current durability of this lens
     */
    @Override
    public double getDurability() {
        return this.durability;
    }

    /**
     * Sets the durability of this lens to the specified value
     *
     * The durability must be lens 0 and 100, if its bigger than 100,
     * the durability will be 100 and if its smaller than 0, the durability
     * will be 100
     *
     * @param durability the new durability value for this lens
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
}
