package cryptobox.dataobjects;

import java.util.Date;
import java.util.List;

import cryptobox.database.DataManager;
import cryptobox.encryption.hash.HashManager;
import cryptobox.utils.PasswordHandler;

/**
 * Created by avishai on 16/10/2016.
 */

public class ApiTester {

    private DataManager DB_Object;

    public ApiTester() {
        DB_Object = DataManager.getInstance();

    }

    public void runScripts() {

        //getAllNotesFromDB(true);
        //changePassword();
        //getAllNotesFromDB(false);

        //checkHash();

    }

    public void checkHash()
    {
        //String hash1To8 = HashManager.stringToHash("12345678");
    }

    public void getAllNotesFromDB(boolean Change) {
        /*List<Note> lst = DB_Object.getAllNotes();

        int listSize = lst.size();
        for (int i = 0; i < listSize; i++) {

            Note temp = lst.get(i);
            if (Change) {
                temp.setTitle("this is my new title");
                temp.setContent("This i smy new content!! Blat!!");
                Boolean ans = DB_Object.UpdateNote(temp);
            }

            int x = 10;
        }*/
    }

    //generate few notes to DB
    public void generateNotes() {


        for (int i = 0; i < 10; i++) {

            String LastModified = new Date(System.currentTimeMillis()).toString();
            double random = Math.random();

            DB_Object.addNote("This is my title - 2avishai2", LastModified, "This is my content 2 - ");
        }
    }

    public void deleteNote(long id) {
        Boolean result = DB_Object.deleteNoteById(id, id);

        String tmp = "This is my stopping the code for no reason";

    }

    public void changePassword() {
        DB_Object.changeUserPassword(PasswordHandler.SessionPassword.getSessionPassword(), "12345678");
    }

    public void simulateGetAllNotes() {
        //DB_Object = new DatabaseHandler(this);


        /*List<Note> lst =  DB_Object.getAllNotes();

        int listSize = lst.size();

        for (int i = 0; i < listSize ; i++) {

            Note temp = lst.get(i);


        }*/

        //String LastModified = new Date(System.currentTimeMillis()).toString();

       /* Note n =  DB_Object.addNote("This is my title", LastModified, "This is my content " + Math.random() );
        Toast.makeText(this, n.getTitle() + ", " + n.getId(), Toast.LENGTH_LONG).show();

        String content =  DatabaseHandler.getContentById(n.getId());

        n.setContent("This is my Content - new content");
        n.setTitle("This is my title - new title");

        String contentx = "";

        Boolean res = n.SaveToDb();
        if(res)
        {
            contentx =  DatabaseHandler.getContentById(n.getId());

        }*/


    }
}
