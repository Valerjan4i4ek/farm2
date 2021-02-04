public class Field {
    private Sign sign;
    private Plant plant;

    public Field() {
        this.sign = Sign.EMPTY;
    }

    public boolean isEmpty(){
        return this.sign == Sign.EMPTY;
    }

    public boolean isFull(){
        return this.sign == Sign.PLANT;
    }

    public Field updateField(Plant plant) {
        this.sign = this.sign.updateSign();
        this.plant = plant;
        return this;
    }

    public Field updateField() {
        return updateField(sign==Sign.EMPTY ? null : plant);
    }

    public int getHarvestPrice() {
        return plant.getHarvestPrice();
    }

    @Override
    public String toString() {
        return sign.toString();
    }

}