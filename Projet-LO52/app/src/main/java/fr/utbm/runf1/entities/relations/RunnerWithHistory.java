package fr.utbm.runf1.entities.relations;

import java.util.Comparator;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.RunnerHistory;

/**
 * Created by Yosef B.I.
 */
public class RunnerWithHistory {

    @Embedded private Runner runner;
    @Relation(parentColumn = "runner_id",
            entityColumn = "runner_id")
    private List<RunnerHistory> runnerHistory;

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public List<RunnerHistory> getRunnerHistory() {
        return runnerHistory;
    }

    public void setRunnerHistory(List<RunnerHistory> runnerHistory) {
        this.runnerHistory = runnerHistory;
    }

    public RunnerHistory getLastHistory() {
        return this.runnerHistory.stream().max(Comparator.comparingInt(RunnerHistory::getId)).orElse(null);
    }
}
