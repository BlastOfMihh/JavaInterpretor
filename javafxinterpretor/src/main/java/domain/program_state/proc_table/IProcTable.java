package domain.program_state.proc_table;

import domain.my_data_structures.my_table.IMyTable;
import domain.statement.IStmt;
import kotlin.Pair;

import java.util.List;

public interface IProcTable extends IMyTable<String, Pair<List<String>, IStmt>> {
}
