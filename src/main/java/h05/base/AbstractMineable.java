package h05.base;

import h05.equipment.Tool;
import h05.mineable.Mineable;
import h05.mineable.MiningProgress;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMineable extends AbstractDurable implements Mineable {
    @Override
    public @NotNull MiningProgress getProgress() {
        if (this.getDurability()==100) {
            return MiningProgress.UNSTARTED;
        }else if(this.getDurability()==0) {
            return MiningProgress.COMPLETED;
        }else{
            return MiningProgress.IN_PROGRESS;
        }
    }

}
