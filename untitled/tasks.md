
To implement:
- [x] Log the repository
- [x] string value
- [?] File Table 
  - [x] data structure for it
  - [x] assume that the file only contains text
    - [x] only one integer per line
  - [x] dictionary maps: String Value to file Descriptor(which inherits from bufferReader)
    - [x] string value: fine name and path
  
- [x] closeRFile ( exp )
  - [x] fileDesc.close()
  - [ ] evaluate exp:string -> fileDesc
  - [ ] fileTAble . delete fileDesc
- [x] openRFile (exp)
  - [ ] evaluate exp:string -> fileDesc
  - [ ] new fileDesc in fileTableA
- [x] readFile 
- 


    
