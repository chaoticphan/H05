package h05.mineable;

import h05.base.AbstractDurable;
import h05.base.AbstractMineable;
import h05.equipment.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Represents a rock that can be mined using tools.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class Rock extends AbstractMineable implements Mineable {
    /**
     * Create a new Attribute to keep count this rock durability
     */


    /**
     * Constructs a new {@link Rock} instance.
     */
    public Rock() {
        super();
    }

    /**
     * Return whether the rock has been completely mined or not
     *
     * @param tool the tool used for mining, or {@code null} if no tool is used
     * @return true if the rock has been fully mined, false if not
     */
    @StudentImplementationRequired("H5.3")
    @Override
    public boolean onMined(@Nullable Tool tool) {
        if(tool == null) {
            this.reduceDurability(5);
        }else if(tool.getName().equals("Pickaxe")){
            this.reduceDurability(tool.getMiningPower()*2);
        }else{
            this.reduceDurability(tool.getMiningPower()*1.5);
        }
        if(this.getDurability()>0){
            return false;
        }else{
            return true;
        }
    }




}
