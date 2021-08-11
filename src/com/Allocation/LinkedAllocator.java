package com.Allocation;

import com.FileSystem.File;

import java.util.ArrayList;

public class LinkedAllocator implements Allocator {
    private final ArrayList<Node> space;
    private final int N;

    public LinkedAllocator(int N) {
        this.N = N;
        space = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            space.add(new Node());
        }
    }

    @Override
    public boolean allocate(File file) {
        int size = file.getSize();
        int cur = 0, previous = -1;
        ArrayList<Integer> temp = new ArrayList<>();
        int i;
        for (i = 0; i < N; i++) { // search for any empty blocks and allocate them
            if (cur >= size) {
                temp.add(previous);
                break;
            }
            if (!space.get(i).allocated) {
                if (previous != -1)
                    space.get(previous).next = space.get(i);
                space.get(i).allocated = true;
                if (cur == 0)
                    temp.add(i);
                space.get(i).cur = i;
                cur++;
                previous = i;
            }
        }
        if (i == N && cur >= size) { // file can be allocated
            temp.add(previous);
        }
        if (cur < size) { // file cannot be allocated, then revert the changes
            if (!temp.isEmpty()) {
                Node start = space.get(temp.get(0));
                while (start != null) {
                    Node next = start.next;
                    start.next = null;
                    start.allocated = false;
                    start = next;
                }
            }
            return false;
        }
        Node start = space.get(temp.get(0));
        while (start != null) {
            temp.add(start.cur);
            start = start.next;
        }
        file.setType("Linked");
        file.setData(temp);
        return true;
    }

    @Override
    public void deAllocate(File file) {
        Node start = space.get(file.getData().get(0));
        while (start != null) {
            Node next = start.next;
            start.next = null;
            start.allocated = false;
            start = next;
        }
    }

    @Override
    public int getEmptySpace() {
        int emptySpace = 0;
        for (Node node : space) {
            if (!node.allocated) emptySpace++;
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
        for (Node node : space) {
            if (node.allocated) res.append("1");
            else res.append("0");
        }
        return res.toString();
    }
}

class Node {
    public int cur = -1; // Used in Printing
    public boolean allocated = false;
    public Node next = null;

    public Node() {
    }
}

