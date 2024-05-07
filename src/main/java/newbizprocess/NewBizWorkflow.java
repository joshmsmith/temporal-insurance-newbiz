package newbizprocess;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface NewBizWorkflow {

    // The Workflow method is called by the initiator either via code or CLI.
    @WorkflowMethod
    void newBusiness(String proposalID, String referenceId);
}
