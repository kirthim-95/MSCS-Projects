package rpg;

public class RpgCharacterBuilder {
    private int baseAttack = 0;
    private int baseDefense = 0;

    public RpgCharacterBuilder setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
        return this;
    }

    public RpgCharacterBuilder setBaseDefense(int baseDefense) {
        this.baseDefense = baseDefense;
        return this;
    }

    public RpgCharacterImpl build() {
        return new RpgCharacterImpl(baseAttack, baseDefense);
    }
}
