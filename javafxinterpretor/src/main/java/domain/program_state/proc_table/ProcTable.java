package domain.program_state.proc_table;

import domain.my_data_structures.my_list.IMyList;
import domain.my_data_structures.my_table.MyTable;
import domain.statement.IStmt;
import kotlin.Pair;

import java.util.List;

public class ProcTable extends MyTable<String, Pair<List<String>, IStmt>> implements IProcTable{
}
