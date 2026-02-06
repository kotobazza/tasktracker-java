package com.kotobazza.commandlineprocess;

import com.kotobazza.tasks.StateNotFoundException;
import com.kotobazza.tasks.TaskNotFoundException;
import com.kotobazza.tasks.TaskState;
import picocli.CommandLine;

import javax.swing.plaf.nimbus.State;
import java.nio.file.Path;
import java.util.Locale;


@CommandLine.Command(name = "mark", description = "Mark task state")
public class TaskTrackerMark extends TaskTrackerCommandSuperclass{

    @CommandLine.ParentCommand
    private TaskTracker tracker;

    @CommandLine.Parameters(index="0", description="Task identifier")
    private String id;

    @CommandLine.Parameters(index="1", description="Task state")
    private String state;

    @Override
    public Integer call() throws Exception {
        try{
            TaskState newState = TaskState.valueOf(state.toUpperCase(Locale.ROOT));

            if(!getService().markTaskWithState(tracker.filePath, id, newState)){
                throw new TaskNotFoundException("Didn't find a task in tasks list with this id: "+id);
            }

            System.out.println("Accepted.");
            return 0;

        } catch (IllegalArgumentException e){
            StringBuilder bld = new StringBuilder();
            for(TaskState value: TaskState.values()){
                bld.append(value.name());
                bld.append("\n");
            }
            throw new StateNotFoundException("Couldn't find a variation of state: " + state + "\nAvailable states (may be printed in lower_case):\n"+bld);
        }




    }
}
