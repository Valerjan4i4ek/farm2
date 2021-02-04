enum Sign {

    EMPTY("_"),
    PLANT("X"),//семена
    HARVEST("0");//урожай

    private final String symbol;

    private Sign(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public Sign updateSign(){
        final Sign[] signs = Sign.values();
        return signs.length <= this.ordinal() + 1 ? signs[0] : signs[this.ordinal() + 1];
    }

}