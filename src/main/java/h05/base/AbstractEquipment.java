package h05.base;

import h05.equipment.Equipment;
import h05.equipment.EquipmentCondition;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractEquipment extends AbstractDurable implements Equipment {
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
}
