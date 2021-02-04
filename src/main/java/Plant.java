public class Plant{
    private String name;
    private int seedPrice;
    private int harvestPrice;
    private long time;

    public Plant(String name, int seedPrice, int harvestPrice, long time) {
        this.name = name;
        this.seedPrice = seedPrice;
        this.harvestPrice = harvestPrice;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeedPrice() {
        return seedPrice;
    }

    public void setSeedPrice(int seedPrice) {
        this.seedPrice = seedPrice;
    }

    public int getHarvestPrice() {
        return harvestPrice;
    }

    public void setHarvestPrice(int harvestPrice) {
        this.harvestPrice = harvestPrice;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", seedPrice=" + seedPrice +
                ", harvestPrice=" + harvestPrice +
                ", time=" + time +
                '}';
    }
}
