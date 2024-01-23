package domain.program_state.garbage_collector;

import domain.my_data_structures.my_stack.IMyStack;
import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.heap.IHeap;
import domain.value.IValue;
import domain.value.RefValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GarbageCollector {
    IHeap heap;
    IMyStack<IMyTable<String, IValue>> symTableStack;
    public GarbageCollector (IHeap heap, IMyStack<IMyTable<String, IValue>> symTableStack){
        this.heap=heap;
        this.symTableStack =symTableStack;
    }
    static List<Integer> getAddressesFromCollection(Collection<IValue> collection){
        return collection
                .stream()
                .filter(element -> element instanceof RefValue)
                .map(element -> ((RefValue) element).getAdress())
                .collect(Collectors.toList());
    }
    static Map<Integer, IValue> getCleanHeap(List<Integer> adressesList, IHeap heap){
        return heap.entrySet()
                .stream()
                .filter(element -> adressesList.contains(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, element->element.getValue()));
    }
    public  Map<Integer, IValue> getNewCleanedHeap(){
        var addressesListStream= getAddressesFromCollection(heap.values()).stream();
        for(var symTable:symTableStack){
            addressesListStream=Stream.concat(
                    addressesListStream,
                    getAddressesFromCollection(symTable.values()).stream()
            );
        }
        Map<Integer, IValue> newHeap=getCleanHeap(
                addressesListStream.toList(),
                heap
        );
        return newHeap;
    }
    public void setHeap(IHeap heap){
        this.heap=heap;
    }
}
