<h1>Delete Outline</h1>

1. Add an isDelete field NOT IN NODE but in assignment, because we aren't
allowed to change anything that's in RedBlackTree.java.

2. User enters the date and name of the assignment they want to delete
    coordinate with front end for that^
    
3. First check if that element is in the tree

4. If it isn't in the tree, don't worry about removing, you can throw a NoSuchElementException

5. If it does end up being in the tree:
   - it might already be deleted(but still will show up in the search, because we aren't actually removing it)
   - if its isDelete field is true, no removing, throw a NoSuchElementException
   - if isDelete field is false, then set it to true
   
6. Some additional changes-
  - add an insert method that overrides the insert from RedBlackTree.java
  - this is because the insert method won't add if it already exists in the tree
  - in your overloaded method:
    * First check if the assignment to be added exists
    * if it does exist, check if that assignment's isDelete is true
      * if true:
      * under this block, copy insert code from RedBlackTree.java
      * Don't copy the part that throws an exception(that's the whole point of this overloaded method)
      * call insert helper, etc as you would normally would, check code from RedBlackTree.java
    * if it does exist and isDelete is true/ if assignment doesn't exist then call normal insert

7. Couple of changes in toString
  - again, override this method, in a way that it skips over those assignments that are deleted
  - think about returning a String that strikes through the name of an assignment like an actual todo list


