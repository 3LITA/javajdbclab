package ru.dao.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import ru.dao.MusicItem;

import java.util.ArrayList;

public class MongoDriver {
    private final String url = "mongodb+srv://mguser:mspass@cluster0-jyfva.mongodb.net/test?retryWrites=true&w=majority";

    private MongoClient mongoClient;
    private MongoCollection mongoCollection;

    public MongoDriver() { }

    public void initConnection() {
        mongoClient  = MongoClients.create(this.url);
        MongoDatabase database = mongoClient.getDatabase("music");
        System.out.println("Connected to the database successfully.");
        mongoCollection = database.getCollection("item");
        System.out.println("Collection selected successfully");
    }

    public MusicItem searchById(String id) {
        Document doc = (Document) mongoCollection.find(Filters.eq("_id", new ObjectId(id))).first();
        return MusicItemDto.documentToMusicItem(doc);
    }

    public MusicItem create(MusicItem item) {
        Document doc = MusicItemDto.musicItemToDocument(item);
        mongoCollection.insertOne(doc);
        Document document = (Document) mongoCollection.find().sort(new Document("_id", -1)).limit(1).first();
        return MusicItemDto.documentToMusicItem(document);
    }

    public ArrayList<MusicItem> searchByKeyword(String keyword) {
        return null;
    }

    public void close(){}

}
