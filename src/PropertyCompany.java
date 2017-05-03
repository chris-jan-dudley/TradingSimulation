
/**
 *
 * @author sjb56
 */
public class PropertyCompany extends Company {

    public PropertyCompany(String companyName, int startingShares, double startingPrice) {
        super(companyName, startingShares, startingPrice);
        riskFactor = RiskLevels.Low;
    }

    @Override
    public Company clone() {
        PropertyCompany comp = new PropertyCompany(super.getName(), super.getNumberOfShares(), super.getSharePrice());
        comp.setRisk(getRiskFactor());
        return comp;
    }

}
