package newbizprocess;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface NewBizActivity {

    // pull data from various sources into a file to be processed
    @ActivityMethod
    String gatherInfo(String proposalID, String referenceId);

    @ActivityMethod
    boolean validateProposalData(String proposalID, String referenceId, String referenceToData);

    @ActivityMethod
    String underwritingAnalysis(String proposalID, String referenceId, String referenceToData);

    
    @ActivityMethod
    void applyDiscounts(String proposalID, String referenceId, String referenceToData);

    @ActivityMethod
    void calculateRates(String proposalID, String referenceId, String referenceToData);

    @ActivityMethod
    void generateForms(String proposalID, String referenceId, String referenceToData);

    // notify appropriate parties about the status of the proposal and its information
    @ActivityMethod
    void notify(String proposalID, String referenceId);
    
}
