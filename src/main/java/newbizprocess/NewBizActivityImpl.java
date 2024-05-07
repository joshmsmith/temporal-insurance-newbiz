package newbizprocess;

//todo do we want to pass the output of any activity into the next one?

public class NewBizActivityImpl implements NewBizActivity {

    @Override
    public String gatherInfo(String proposalID, String referenceId) {
        System.out.printf(
                "\nGathering proposal information for %s. ReferenceId: %s\n",
                proposalID, referenceId);
        return "DATA-" + proposalID + "-" + referenceId+".dat";
    }

    @Override
    public boolean validateProposalData(String proposalID, String referenceId, String referenceToData) {
        System.out.printf(
                "\nValidating proposal information for %s. ReferenceId: %s at %s \n",
                proposalID, referenceId, referenceToData);

        return true; // for demo purposes data is valid
    }

    @Override
    public String underwritingAnalysis(String proposalID, String referenceId, String referenceToData) {
        System.out.printf(
                "\nAnalyzing Underwriting approval for proposal for %s. ReferenceId: %s at %s\n",
                proposalID, referenceId, referenceToData);
        return "APPROVED";
    }

    @Override
    public void applyDiscounts(String proposalID, String referenceId, String referenceToData) {
        System.out.printf(
                "\nApplying/updating appropriate discounts for proposal %s. ReferenceId: %s at %s\n",
                proposalID, referenceId, referenceToData);
    }

    @Override
    public void calculateRates(String proposalID, String referenceId, String referenceToData) {
        System.out.printf(
                "\nGenerating forms for %s. ReferenceId: %s at %s\n",
                proposalID, referenceId, referenceToData);
    }
    
    @Override
    public void generateForms(String proposalID, String referenceId, String referenceToData) {
        System.out.printf(
                "\nGenerating forms for %s. ReferenceId: %s at %s\n",
                proposalID, referenceId, referenceToData);
    }

    @Override
    public void notify(String proposalID, String referenceId) {
        System.out.printf(
                "\nNotifying appropriate parties about the information and status of %s. ReferenceId: %s\n",
                proposalID, referenceId);
    }
}

