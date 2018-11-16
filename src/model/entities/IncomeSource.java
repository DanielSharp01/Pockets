package model.entities;

/**
 * Represents an income source
 */
public class IncomeSource extends Item {
    /**
     * @param id Integer key
     */
    public IncomeSource(int id) {
        super(id);
    }

    @Override
    public IncomeSource clone()
    {
        IncomeSource c = new IncomeSource(getId());
        c.setName(getName());
        c.setColor(getColor());
        c.setImageResource(getImageResource());
        c.setMoney(getMoney().clone());
        c.setRecurrence(getRecurrence().clone());
        c.getTagIds().addAll(getTagIds());
        return c;
    }
}
