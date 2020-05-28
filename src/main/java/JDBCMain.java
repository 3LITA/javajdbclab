import ru.dao.MusicItem;
import ru.dao.mongo.MongoDriver;
import ru.dao.postgres.PostgresDriver;

import java.util.ArrayList;
import java.util.Date;

public class JDBCMain {
    public static void main(String[] args) {

        PostgresDriver postgres = new PostgresDriver("itmo", 5432, "localhost", "postgres", "mysecretpassword");
        postgres.initConnection();

        System.out.println("\nSEARCH BY ID 2:");
        MusicItem item = postgres.searchById(2);
        System.out.println(item);

        System.out.println("\nSEARCH BY ID 100:");
        item = postgres.searchById(100);
        System.out.println(item);

        System.out.println("\nSEARCH BY KEYWORD 'OF':");
        ArrayList<MusicItem> musicItems = postgres.searchByKeyword("of");
        System.out.println(musicItems);

        System.out.println("\nSEARCH BY KEYWORD 'GAY':");
        musicItems = postgres.searchByKeyword("Gay");
        System.out.println(musicItems);

        System.out.println("\nINSERTING ROW:");
        postgres.create("Here comes the sun", "Beatles", new Date(), 9.99, 5.99, 1);
        System.out.println("\nSUCCESSFULLY INSERTED!");

        System.out.println("\nGETTING ALL:");
        musicItems = postgres.getAll();
        System.out.println(musicItems);

        postgres.close();

        MongoDriver mongo = new MongoDriver();
        mongo.initConnection();

        System.out.println("\nMIGRATING ALL OBJECTS TO MONGO ATLAS:");
        for (MusicItem musicItem: musicItems) {
            System.out.println(mongo.create(musicItem));
        }

        mongo.close();
    }
}
