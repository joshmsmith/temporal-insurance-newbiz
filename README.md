# temporal-insurance-newbiz
This is a workflow that demonstrates a sample new business process for insurance.

## Execute tests
```bash
./gradlew build
```
## Execute workflow

1. Start your Temporal server
```bash
temporal server start-dev
```

2. Start the worker
```bash
./gradlew startWorker
```

3. Initiate the workflow
```bash
./gradlew initiateWorkflow
```

## Related
Credit to https://learn.temporal.io/getting_started/java/first_program_in_java/ for the idea and structure.