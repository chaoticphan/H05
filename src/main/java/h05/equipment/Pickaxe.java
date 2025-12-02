package h05.equipment;

import h05.base.AbstractEquipment;
import org.jetbrains.annotations.NotNull;

public class Pickaxe extends AbstractEquipment implements Tool {
    /**
     *Attribute to keep count this pickaxe durability
     */

    /**
     * Create a new pickaxe that has a durability and inherited all the methods of UsableEquipment
     */
    public Pickaxe() {
    }

    /**
     * Returns the mining power of this pickaxe.
     *
     * @return the mining power of this pickaxe
     */
    @Override
    public double getMiningPower(){
        return 15;
    }


}
