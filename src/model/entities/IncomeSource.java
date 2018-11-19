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
        if (getMoney() != null)
            c.setMoney(getMoney().clone());
        else
            c.setMoney(null);

        c.setRecurrence(getRecurrence().clone());
        c.getTagIds().addAll(getTagIds());
        return c;
    }
}
