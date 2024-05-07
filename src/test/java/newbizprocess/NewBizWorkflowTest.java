package newbizprocess;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NewBizWorkflowTest {

    private TestWorkflowEnvironment testEnv;
    private Worker worker;
    private WorkflowClient workflowClient;

    @Before
    public void setUp() {
        testEnv = TestWorkflowEnvironment.newInstance();
        worker = testEnv.newWorker(Shared.INSURANCE_NEWBIZ_TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(NewBizWorkflowImpl.class);
        workflowClient = testEnv.getWorkflowClient();
    }

    @After
    public void tearDown() {
        testEnv.close();
    }

    @Test
    public void testNewBizFlow() {
        NewBizActivity activities = mock(NewBizActivityImpl.class);
        worker.registerActivitiesImplementations(activities);
        testEnv.start();
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.INSURANCE_NEWBIZ_TASK_QUEUE)
                .build();
        NewBizWorkflow workflow = workflowClient.newWorkflowStub(NewBizWorkflow.class, options);
        workflow.newBusiness("Proposal-123", "reference1");
        // todo testing
        verify(activities).gatherInfo(eq("Proposal-123"), eq("reference1"));
        // verify(activities).withdraw(eq("account1"), eq("reference1"), eq(1.23));
        // verify(activities).deposit(eq("account2"), eq("reference1"), eq(1.23));
    }
}
