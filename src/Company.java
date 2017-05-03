
/**
 *
 * @author sjb56
 */
public abstract class Company {

    private String name;
    private int numberOfShares;
    private double sharePrice;

    /**
     *
     */
    protected RiskLevels riskFactor;

    /**
     *
     */
    public enum RiskLevels {

        /**
         *
         */
        High,

        /**
         *
         */
        Medium,

        /**
         *
         */
        Low
    }

    /**
     *
     * @param name
     * @param startingShares
     * @param startingPrice
     */
    public Company(String name, int startingShares, double startingPrice) {
        this.name = name;
        numberOfShares = startingShares;
        sharePrice = startingPrice;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param newPrice
     * @return
     */
    public boolean setSharePrice(double newPrice) {
        if (newPrice >= 0) {
            sharePrice = newPrice;
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return
     */
    public double getSharePrice() {
        return sharePrice;
    }

    /**
     *
     * @param newAmount
     * @return
     */
    public boolean setNumberOfShares(int newAmount) {
        if (newAmount >= 0) {
            numberOfShares = newAmount;
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     *
     * @param risk
     */
    public void setRisk(RiskLevels risk) {
        riskFactor = risk;
    }

    /**
     *
     * @return
     */
    public RiskLevels getRiskFactor() {
        return riskFactor;
    }

    /**
     *
     * @return
     */
    @Override
    protected abstract Company clone();

}
