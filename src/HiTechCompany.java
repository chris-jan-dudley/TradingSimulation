
/**
 *
 * @author sjb56
 */
public class HiTechCompany extends Company {

    /**
     *
     * @param companyName
     * @param startingShares
     * @param startingPrice
     */
    public HiTechCompany(String companyName, int startingShares, double startingPrice) {
        super(companyName, startingShares, startingPrice);
        riskFactor = RiskLevels.Low;
    }

    /**
     *
     * @return
     */
    @Override
    public Company clone() {
        HiTechCompany comp = new HiTechCompany(super.getName(), super.getNumberOfShares(), super.getSharePrice());
        comp.setRisk(getRiskFactor());
        return comp;
    }
}
