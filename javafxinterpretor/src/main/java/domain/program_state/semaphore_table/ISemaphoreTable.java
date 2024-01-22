package domain.program_state.semaphore_table;

import domain.program_state.AddressedTable;
import domain.program_state.IAddressedTable;
import kotlin.Pair;

import java.util.List;
import java.util.Map;

public interface ISemaphoreTable extends IAddressedTable<Pair<Integer, List<Integer>>> {

}
