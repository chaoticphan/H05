package h05.mineable;

import h05.base.AbstractMineable;
import h05.equipment.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Represents a tree that can be mined using tools.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class Tree extends AbstractMineable implements Mineable {


    /**
     * Create a new Attribute to keep count this tree durability
     */
    private double durability;

    /**
     * Constructs a new {@link Tree} instance.
     */
    public Tree() {
        this.setDurability(100);
    }





    /**
     * Return whether the tree has been completely mined or not
     *
     * @param tool the tool used for mining, or {@code null} if no tool is used
     * @return true if the tree has been fully mined, false if not
     */
    @StudentImplementationRequired("H5.3")
    @Override
    public boolean onMined(@Nullable Tool tool) {
        if(tool == null){
            this.reduceDurability(7.5);
        }else if(tool.getName().equals("Pickaxe")){
            this.reduceDurability(tool.getMiningPower()*3);
        }else{
            this.reduceDurability(tool.getMiningPower()*4);
        }
        if(this.getDurability()>0){
            return false;
        }else{
            return true;
        }
    }


}
