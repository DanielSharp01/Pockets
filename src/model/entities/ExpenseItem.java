package model.entities;

/**
 * Represents an expense item
 */
public class ExpenseItem extends Item {
    /**
     * @param id Integer key
     */
    public ExpenseItem(int id) {
        super(id);
    }

    @Override
    public ExpenseItem clone()
    {
        ExpenseItem c = new ExpenseItem(getId());
        c.setName(getName());
        c.setColor(getColor());
        c.setImageResource(getImageResource());
        c.setMoney(getMoney().clone());
        c.setRecurrence(getRecurrence().clone());
        c.getTagIds().addAll(getTagIds());
        return c;
    }
}