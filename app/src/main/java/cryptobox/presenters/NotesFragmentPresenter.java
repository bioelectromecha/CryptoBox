package cryptobox.presenters;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cryptobox.contracts.DataModel;
import cryptobox.contracts.NotesFragmentContract;
import cryptobox.dataobjects.Note;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * this class is the logic module, handling user input and directing model and view changes
 */
// TODO add the superclass with the start() method like you did in inventoryApp
public class NotesFragmentPresenter implements NotesFragmentContract.PresenterContract {

    private NotesFragmentContract.View mView;
    private DataModel mModel;
    private List<Note> mCheckedNoteList;

    /**
     * main and only constrcutor
     * @param view
     */
    public NotesFragmentPresenter(@NonNull NotesFragmentContract.View view, @NonNull DataModel model) {
        mView = checkNotNull(view);
        mModel = checkNotNull(model);
        mCheckedNoteList = new ArrayList<>(0);
    }

    /**
     * load the notes when the view is first created
     */
    @Override
    public void start() {
        loadNotes();
    }

    /**
     * load a list of notes from the model
     */
    private void loadNotes() {
        List<Note> noteList = mModel.getNotes();
        //if the list is empty - display the placeholder, otherwise show the recyclerview
        if (!noteList.isEmpty()) {
            mView.hidePlaceholder();
            mView.showNotes(noteList);
        } else {
            mView.showPlaceholder();
        }
    }

    /**
     * user clicked on add a new note
     */
    private void openNewNote() {
        mView.showAddNewNote();
    }

    /**
     * user clicked show details of an existing note
     *
     * @param note
     */
    private void openNoteDetails(@NonNull Note note) {
        checkNotNull(note);
        mView.showNoteDetail(note);
    }


    /**
     * receives a note from the view - adds it or removes in
     *
     * @param note
     */
    private void addOrRemoveCheckedNote(@NonNull Note note) {
        checkNotNull(note);

        if (mCheckedNoteList.contains(note)) {
            removeCheckedNote(note);
        } else {
            addCheckedNote(note);
        }
    }

    /**
     * add a note to the checked note list
     * @param note
     */
    private void addCheckedNote(@NonNull Note note) {
        checkNotNull(note);

        mView.showNoteChecked(note);

        // make the trashcan and the back arrow menu buttons visible
        if (isCheckNoteListEmpty()) {
            setViewStateMultiSelect();
        }
        mCheckedNoteList.add(note);
    }

    /**
     * remove a note from the checked note list
     *
     * @param note
     */
    private void removeCheckedNote(@NonNull Note note) {
        checkNotNull(note);

        mView.showNoteUnchecked(note);

        mCheckedNoteList.remove(note);

        // remove the trashcan button
        if (isCheckNoteListEmpty()) {
            unsetViewStateMultiSelect();
        }
    }

    /**
     * delete all the checked notes from the db
     */
    private void deleteCheckedNotes() {
        for (Note note : mCheckedNoteList) {
            mModel.deleteNote(note);
        }
        mCheckedNoteList.clear();
        loadNotes();

        unsetViewStateMultiSelect();
    }

    /**
     * uncheck all notes selected for deletion
     */
    private void clearCheckedNotes() {
        mView.uncheckSelectedNotes(mCheckedNoteList);
        mCheckedNoteList.clear();

        unsetViewStateMultiSelect();
    }

    @Override
    public void userPressedBackButton() {
        if (isCheckNoteListEmpty()) {
            mView.exitApp();
        } else {
            clearCheckedNotes();
        }
    }

    private boolean isCheckNoteListEmpty() {
        return mCheckedNoteList.isEmpty();
    }

    @Override
    public void userClickedOnNote(@NonNull Note note) {
        checkNotNull(note);

        if (isCheckNoteListEmpty()) {
            openNoteDetails(note);
        } else {
            addOrRemoveCheckedNote(note);
        }
    }

    @Override
    public void userLongClickedOnNote(@NonNull Note note) {
        checkNotNull(note);
        addOrRemoveCheckedNote(note);
    }

    @Override
    public void userClickedOnFab() {
        if (!isCheckNoteListEmpty()) {
            return;
        }
        openNewNote();
    }

    @Override
    public void userClickedOnTrashCan() {
        if (!isCheckNoteListEmpty()) {
            mView.showConfirmDeleteDialog();
        }
    }

    @Override
    public void userClickedConfirmDelete() {
        if (!isCheckNoteListEmpty()) {
            deleteCheckedNotes();
        }
    }

    @Override
    public void userClickedOnChangePassword() {
        mView.showSettings();
    }

    //    TODO implement saving of checked notes on screen rotation etc
//    /**
//     * get the IDs to save
//     * @return
//     */
//    @Override
//    public @Nullable int[] getCheckedNotes() {
//
//        if (mCheckedNoteList.isEmpty()) {
//            return null;
//        }
//
//        int[] noteIdArray = new int[mCheckedNoteList.size()];
//
//        for (int i = 0; i < mCheckedNoteList.size(); i++) {
//            noteIdArray[i] = mCheckedNoteList.get(i).getId();
//        }
//        return noteIdArray;
//    }

    private void setViewStateMultiSelect() {
        mView.showTrashCan();
        mView.showBackArrow();
        mView.showCheckBoxes();
        mView.hideFab();
    }

    private void unsetViewStateMultiSelect() {
        mView.hideTrashCan();
        mView.hideBackArrow();
        mView.hideCheckBoxes();
        mView.showFab();
    }
    @Override
    public void userClickedOnAbout() {
        mView.showAbout();
    }
}
