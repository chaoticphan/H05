package h05.equipment;

import h05.base.AbstractDurable;
import h05.base.AbstractEquipment;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A battery is a type of equipment that defines the lifetime of an entity.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class Battery extends AbstractEquipment implements Equipment {
    /**
     * Create a new Attribute to keep count this battery durability
     */
    /**
     * Constructs a new {@link Battery} instance.
     */
    public Battery() {
        super();
    }



    @Override
    public boolean isUsable() {
        return false;
    }

    @Override
    public boolean isTool() {
        return false;
    }






}
