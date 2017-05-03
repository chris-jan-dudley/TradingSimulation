
/**
 *
 * @author sjb56
 */
public class FoodCompany extends Company {

    /**
     *
     * @param companyName
     * @param startingShares
     * @param startingPrice
     */
    public FoodCompany(String companyName, int startingShares, double startingPrice) {
        super(companyName, startingShares, startingPrice);
        riskFactor = RiskLevels.Low;
    }

    /**
     *
     * @return
     */
    @Override
    public Company clone() {
        FoodCompany comp = new FoodCompany(super.getName(), super.getNumberOfShares(), super.getSharePrice());
        comp.setRisk(getRiskFactor());
        return comp;
    }

}
