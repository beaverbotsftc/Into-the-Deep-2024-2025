package org.beaverbots.BeaverCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class CommandGroup implements Command {
    protected final List<Command> commands;

    public CommandGroup(Command... commands) {
        this.commands = new ArrayList<>(Arrays.asList(commands));
        checkDependencies();
    }

    private void checkDependencies() {
        Set<Subsystem> dependencies = new HashSet<>();
        for (Command command : commands) {
            Set<Subsystem> commandDependencies = Command.calculateDependencies(command);
            if (!Collections.disjoint(dependencies, commandDependencies)) {
                throw new ConflictingCommandsException(String.format("Command '%s' has dependencies not disjoint with the rest of the command group.", command));
            }
            dependencies.addAll(commandDependencies);
        }
    }

    public final Set<Subsystem> getDependencies() {
        Set<Subsystem> dependencies = new HashSet<>();
        for (Command command : commands) {
            // Command.calculateDependencies will still work, but this provides a more minimal set.
            dependencies.addAll(command.getDependencies());
        }
        return dependencies;
    }
}
