package ru.dao.mongo;

import org.bson.Document;
import ru.dao.MusicItem;

import java.util.Date;

public class MusicItemDto {

    public static Document musicItemToDocument(MusicItem item){
        if (item == null) return null;
        return new Document()
                .append("title", item.getTitle())
                .append("artist", item.getArtist())
                .append("listPrice", item.getListPrice())
                .append("price", item.getPrice())
                .append("version", item.getVersion());
    }

    public static MusicItem documentToMusicItem(Document document){
        if (document == null) return null;
        return new MusicItem(
                document.get("title").toString(),
                document.get("artist").toString(),
                new Date(),
                (double) document.get("listPrice"),
                (double) document.get("price"),
                (int) document.get("version")
        );
    }
}
