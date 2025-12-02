package h05.base;

import h05.Durable;

public abstract class AbstractDurable implements Durable {
    private double durability;

    public AbstractDurable() {
        this.setDurability(100);
    }

    @Override
    public double getDurability() {
        return this.durability;
    }

    @Override
    public void setDurability(double durability) {
        this.durability = (durability > 100) ? 100
            : (durability < 0) ? 0
            : durability;
    }

    @Override
    public void reduceDurability(double amount) {
        double currDurability = this.getDurability();
        double afterDurability = currDurability - amount;
        this.setDurability(afterDurability);
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
