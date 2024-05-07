package newbizprocess;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

import java.util.UUID;


public class InitiateNewBizProcess {

    public static void main(String[] args) throws Exception {

        // WorkflowServiceStubs is a gRPC stubs wrapper that talks to the local Docker
        // instance of the Temporal server.
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

        String proposalID = generateProposalID();
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.INSURANCE_NEWBIZ_TASK_QUEUE)
                // A WorkflowId prevents this it from having duplicate instances, remove it to
                // duplicate.
                .setWorkflowId(proposalID)
                .build();
        // WorkflowClient can be used to start, signal, query, cancel, and terminate
        // Workflows.
        WorkflowClient client = WorkflowClient.newInstance(service);
        // WorkflowStubs enable calls to methods as if the Workflow object is local, but
        // actually perform an RPC.
        NewBizWorkflow workflow = client.newWorkflowStub(NewBizWorkflow.class, options);
        String referenceId = UUID.randomUUID().toString();
        // Asynchronous execution. This process will exit after making this call.
        WorkflowExecution we = WorkflowClient.start(workflow::newBusiness, proposalID, referenceId);
        System.out.printf("\nNew Business processing started for " + proposalID);
        System.out.printf("\nWorkflowID: %s RunID: %s", we.getWorkflowId(), we.getRunId());
        System.exit(0);
    }


    private static String generateProposalID() {
        return String.format(
            "PROPOSAL-%s-%03d",
            (char) (Math.random() * 26 + 'A')
                + ""
                + (char) (Math.random() * 26 + 'A')
                + ""
                + (char) (Math.random() * 26 + 'A'),
            (int) (Math.random() * 999));
    }
}

