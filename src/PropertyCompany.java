
/**
 *
 * @author sjb56
 */
public class PropertyCompany extends Company {

    /**
     *
     * @param companyName
     * @param startingShares
     * @param startingPrice
     */
    public PropertyCompany(String companyName, int startingShares, double startingPrice) {
        super(companyName, startingShares, startingPrice);
        riskFactor = RiskLevels.Low;
    }

    /**
     *
     * @return
     */
    @Override
    public Company clone() {
        PropertyCompany comp = new PropertyCompany(super.getName(), super.getNumberOfShares(), super.getSharePrice());
        comp.setRisk(getRiskFactor());
        return comp;
    }

}
