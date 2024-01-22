package domain.program_state.garbage_collector;

import domain.my_data_structures.my_table.IMyTable;
import domain.program_state.heap.IHeap;
import domain.value.IValue;
import domain.value.RefValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GarbageCollector {
    IHeap heap;
    IMyTable<String, IValue> symTable;
    public GarbageCollector (IHeap heap, IMyTable<String, IValue> symTable){
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
    static Map<Integer, IValue> getCleanHeap(List<Integer> adressesList, IHeap heap){
        return heap.entrySet()
                .stream()
                .filter(element -> adressesList.contains(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, element->element.getValue()));
    }
    // static Heap constructCleanHeap()
    //public void cleanHeap(){
    //    //if(true) return; //disable it please do not forget to re-enable it
    //    Map<Integer, IValue> newHeap=getCleanHeap(
    //            Stream.concat(getAddressesFromCollection(symTable.values()).stream(),
    //                getAddressesFromCollection(heap.values()).stream()).toList()
    //            , heap
    //        );
    //    heap.clear();
    //    newHeap.entrySet().stream().forEach(element->heap.put(element.getKey(), element.getValue()));
    //}
    public  Map<Integer, IValue> getNewCleanedHeap(){
        //if(true) return; //disable it please do not forget to re-enable it
        Map<Integer, IValue> newHeap=getCleanHeap(
                Stream.concat(getAddressesFromCollection(symTable.values()).stream(),
                        getAddressesFromCollection(heap.values()).stream()).toList()
                , heap
        );
        return newHeap;
        //heap.clear();
        //newHeap.entrySet().stream().forEach(element->heap.put(element.getKey(), element.getValue()));
    }
    public void setHeap(IHeap heap){
        this.heap=heap;
    }
}
