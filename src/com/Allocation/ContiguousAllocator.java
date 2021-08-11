package com.Allocation;

import com.FileSystem.File;

import java.util.ArrayList;

public class ContiguousAllocator implements Allocator {
    private final ArrayList<Boolean> space;
    private final int N;

    public ContiguousAllocator(int N) {
        this.N = N;
        space = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            space.add(false);
        }
    }

    @Override
    public boolean allocate(File file) {
        int mx = 0;
        int mxIdx = -1;
        for (int i = 0; i < N; i++) { // search for the maximum number of contiguous empty blocks (worst fit)
            if (!space.get(i)) {
                int cur = 1;
                int j;
                for (j = i + 1; j < N; j++) {
                    if (!space.get(j)) {
                        cur++;
                    } else {
                        break;
                    }
                }
                if (cur > mx) {
                    mx = cur;
                    mxIdx = i;
                }
                i = j;
            }
        }
        if (mx >= file.getSize()) { // file can be allocated
            for (int i = mxIdx; i < mxIdx + file.getSize(); i++) {
                space.set(i, true);
            }
            file.setType("Contiguous");
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(mxIdx);
            temp.add(file.getSize());
            file.setData(temp);
            return true;
        }
        return false;
    }

    @Override
    public void deAllocate(File file) {
        int idx = file.getData().get(0);
        int size = file.getSize();
        for (int i = idx; i < idx + size; i++) {
            space.set(i, false);
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
