
/**
 *
 * @author sjb56
 */
public class HardCompany extends Company {

    public HardCompany(String companyName, int startingShares, double startingPrice) {
        super(companyName, startingShares, startingPrice);
        riskFactor = RiskLevels.Low;
    }

    @Override
    public Company clone() {
        HardCompany comp = new HardCompany(super.getName(), super.getNumberOfShares(), super.getSharePrice());
        comp.setRisk(getRiskFactor());
        return comp;
    }

}
