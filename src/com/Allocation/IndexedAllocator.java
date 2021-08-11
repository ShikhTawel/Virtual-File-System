package com.Allocation;

import com.FileSystem.File;

import java.util.ArrayList;

public class IndexedAllocator implements Allocator {
    private final ArrayList<Boolean> space;
    private final int N;

    public IndexedAllocator(int N) {
        this.N = N;
        space = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            space.add(false);
        }
    }

    @Override
    public boolean allocate(File file) {
        int size = file.getSize() + 1;
        ArrayList<Integer> temp = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < N && count < size; i++) { // search for any empty blocks and allocate them and add them to the index block
            if (!space.get(i)) {
                temp.add(i);
                space.set(i, true);
                count++;
            }
        }
        if (count < size) { // file cannot be allocated
            for (Integer integer : temp) {
                space.set(integer, false);
            }
            return false;
        }
        file.setType("Indexed");
        file.setData(temp);
        return true;
    }

    @Override
    public void deAllocate(File file) {
        for (Integer integer : file.getData()) {
            space.set(integer, false);
        }
    }

    @Override
    public int getEmptySpace() {
        int emptySpace = 0;
        for (Boolean block : space) {
            if (!block) emptySpace++;
        }
        return emptySpace;
    }

    @Override
    public int getAllocatedSpace() {
        return N - getEmptySpace();
    }

    @Override
    public String getSpace() {
        StringBuilder res = new StringBuilder();
        for (Boolean block : space) {
            if (block) res.append("1");
            else res.append("0");
        }
        return res.toString();
    }
}