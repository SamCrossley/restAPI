package Library;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "books")
public class Book {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String bookName;

    public Book(){

    }
    public int getId(){
        return this.id;
    }
    public String getBookName(){
        return this.bookName;
    }
    public void setBookName(String bookName){
        this.bookName = bookName;
    }
}
