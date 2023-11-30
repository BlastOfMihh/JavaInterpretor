package domain.program_state.garbage_collector;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.heap.Heap;
import domain.program_state.sym_table.SymTable;
import domain.value.IValue;
import domain.value.RefValue;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GarbageCollector {
    Heap heap;
    SymTable symTable;
    public GarbageCollector (Heap heap, SymTable symTable){
        this.heap=heap;
        this.symTable=symTable;
    }
    static List<Integer> getAddressesFromCollection(Collection<IValue> collection){
        return collection
                .stream()
                .filter(element -> element instanceof RefValue)
                .map(element -> ((RefValue) element).getAdress())
                .collect(Collectors.toList());
    }
    // static Heap constructCleanHeap()
    public void cleanHeap(){

    }
}
