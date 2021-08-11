package com.Allocation;

import com.FileSystem.File;

public interface Allocator {
    boolean allocate(File file);

    void deAllocate(File file);

    int getEmptySpace();

    int getAllocatedSpace();

    String getSpace();
}