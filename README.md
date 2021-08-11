# Virtual-File-System
A virtual file systems that simulates the files allocation and deallocation processes using different allocation algorithms and a protection layer.

The system allows a user to create and delete files and folders just like any file system, and also allows the user to choose the allocation technique for the file system from one these algorithms:

## 1- Contiguous Allocation
in this allocation algorithm, the system chooses a contiguous subset of the available memory to allocate for the specified file, and if no empty contiguous memory is sufficient in the virtual drive then the system cannot allocate the file.

## 2- Linked Allocation
in this allocation algorithm, the system can choose any cells to allocate for the file in a linked-list fashion, every cell has a link (pointer) to another cell so they don't have to be contiguous, as long as the virtual disk has enough empty space for the specified file to be allocated.

## 3- Indexed Allocation
in this allocation algorithm, the system also can choose any cells to allocate for the file, but also allocate an extra space for the "Index block", which is a block that stores the cells that the file will allocate in the virtual disk, which don't have to be contiguous.

# Protection Layer
Also, the system allows for the creation and deletion of users in the system, and the admin can grant a user creation access or deletion access on any directory in the file system, so that a user can't manipulate files and folders unless they're granted access to do so by the admin.

# Collaboration
The project is built in collaboration with [Mahmoud Abdelazeem](https://github.com/MahmoudAbdelazim) for the advanced operating systems course.
