
/**
 *
 * @author sjb56
 */
public class FoodCompany extends Company {

    public FoodCompany(String companyName, int startingShares, double startingPrice) {
        super(companyName, startingShares, startingPrice);
        riskFactor = RiskLevels.Low;
    }

    @Override
    public Company clone() {
        FoodCompany comp = new FoodCompany(super.getName(), super.getNumberOfShares(), super.getSharePrice());
        comp.setRisk(getRiskFactor());
        return comp;
    }

}
