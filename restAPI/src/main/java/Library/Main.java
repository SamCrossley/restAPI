package Library;

import static spark.Spark.get;
import static spark.Spark.post;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        get("/books/:id",new Route(){
                @Override
            public Object handle(Request request, Response response) throws Exception {
                return "User: username=test, email=test@test.net";
            }
        });
        String databaseUrl = "jdbc:mysql://localhost/spark";

        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        ((JdbcConnectionSource)connectionSource).setUsername("spark");
        ((JdbcConnectionSource)connectionSource).setPassword("spark");

        final Dao<Book,String> bookDao = DaoManager.createDao(connectionSource, Book.class);
        TableUtils.createTableIfNotExists(connectionSource, Book.class);

        post("/books",new Route(){
            @Override
            public Object handle(Request request, Response response) throws SQLException {
                String bookName = request.queryParams("bookName");

                Book book = new Book();
                book.setBookName(bookName);

                bookDao.create(book);

                response.status(201); //201 created
                return book;
            }
        });

        get("/books/:id", new Route(){

            @Override
            public Object handle(Request request, Response response) throws Exception {
                Book book = bookDao.queryForId(request.params(":id"));
                if (book != null){
                    return "book name:" + book.getBookName();
                }else {
                    response.status(404);
                    return "book not found";
                }
            }
        });
    }
}
