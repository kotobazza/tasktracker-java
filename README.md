# Task Tracker CLI
https://roadmap.sh/projects/task-tracker

Simple CLI App for managing tasks.

### Features
+ [X] Add a new task with description
+ [X] Look at tasks in different states
+ [X] Update task status (using unique taskID)
+ [X] Look when tasks got updated statuses and when they were created
+ [X] Remove tasks if needed
+ [X] Update task description

### Usage
```bash
Usage: tasktracker [COMMAND]
Simple CLI task manager
Commands:
  add     Add a new task
  list    List active tasks
  mark    Mark task state
  remove  Remove a task from list
  table   Show whole info about tasks
  edit    Edit task description
  show    Show info about user environment
```

### Quick start
```bash
mvn package
java -jar target/tasktracker.jar add "Buy some milk"
```

By default `tasktracker` uses `${XDG_DATA_HOME}/com.kotobazza.tasktracker/tasks.json` for Linux (with other OSes is incompatible)
For alternate tasks path use key `--file`/`-f` and specify custom json file


### Future features
+ [ ] Use list as "list today"
+ [ ] Add selection by day and time
+ [ ] Add due dates
+ [ ] Add "tasktracker graphical" and merge full-tui into tui-command to resolve conflict between these versions
+ [ ] Add prefix search for tasks