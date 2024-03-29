package com.pangff.mycanvas.interfaces;


public interface IUndoCommand
{
    public void undo();
    public void redo();
    public boolean canUndo();
    public boolean canRedo();
    public void onDeleteFromUndoStack();
    public void onDeleteFromRedoStack();
}
