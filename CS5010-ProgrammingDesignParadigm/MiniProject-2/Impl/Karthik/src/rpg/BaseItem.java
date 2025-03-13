package rpg;

public abstract class BaseItem implements Item {
    protected final ItemType type;
    protected String adjective;
    protected String noun;
    protected int attack;
    protected int defense;

    public BaseItem(ItemType type, String adjective, String noun, int attack, int defense) {
        if ((attack < 0) || (defense < 0)) {
            throw new IllegalArgumentException("Attack and defense must be non-negative!");
        }


        this.type = type;
        this.adjective = adjective;
        this.noun = noun;
        this.attack = attack;
        this.defense = defense;
    }

    @Override
    public String getType() {
        return type.getName();
    }

    @Override
    public String getAdjective() {
        return adjective;
    }

    @Override
    public String getNoun() {
        return noun;
    }

    @Override
    public String getName() {
        return adjective + " " + noun;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        BaseItem otherItem = (BaseItem) obj;

        return type == otherItem.type &&
                attack == otherItem.attack &&
                defense == otherItem.defense &&
                adjective.equals(otherItem.adjective) &&
                noun.equals(otherItem.noun);
    }
}
