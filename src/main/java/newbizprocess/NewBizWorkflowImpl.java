package newbizprocess;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import io.temporal.common.RetryOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class NewBizWorkflowImpl implements NewBizWorkflow {
    private static final String GENERATEFORMS = "generateForms";
    // RetryOptions specify how to automatically handle retries when Activities
    // fail.
    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();
    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            // Timeout options specify when to automatically timeout Activities if the
            // process is taking too long.
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            // Optionally provide customized RetryOptions.
            // Temporal retries failures by default, this is simply an example.
            .setRetryOptions(retryoptions)
            .build();
    // ActivityStubs enable calls to methods as if the Activity object is local, but
    // actually perform an RPC.
    private final Map<String, ActivityOptions> perActivityMethodOptions = new HashMap<String, ActivityOptions>() {
        {
            put(GENERATEFORMS, ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
        }
    };
    private final NewBizActivity newBizActivities = Workflow.newActivityStub(NewBizActivity.class, defaultActivityOptions,
    perActivityMethodOptions);


    @Override
    public void newBusiness(String proposalID, String referenceId) {
        String referenceToData = newBizActivities.gatherInfo(proposalID, referenceId);
        boolean isValid = newBizActivities.validateProposalData(proposalID, referenceId, referenceToData);
        if(!isValid) {
            newBizActivities.notify(proposalID, referenceId); // notify about the denied proposal
            return;
        }

        String approvalStatus = newBizActivities.underwritingAnalysis(proposalID, referenceId, referenceToData);
        if(approvalStatus != "APPROVED") {
            newBizActivities.notify(proposalID, referenceId); // notify about the denied proposal
            return;
        }
        newBizActivities.applyDiscounts(proposalID, referenceId, referenceToData);
        newBizActivities.calculateRates(proposalID, referenceId, referenceToData);
        newBizActivities.generateForms(proposalID, referenceId, referenceToData);
        newBizActivities.notify(proposalID, referenceId);

    }
}

