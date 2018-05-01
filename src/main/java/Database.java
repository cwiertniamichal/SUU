import com.mongodb.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Database {

    private void createCollection(String collectionName, DB db){
        db.createCollection(collectionName, null);
    }

    private DBCollection getCollection(String collectionName, DB db){
        DBCollection collection = db.getCollection(collectionName);
        return collection;
    }

    private void displayCollections(DB db){
        Set<String> collectionNames = db.getCollectionNames();
        for(String collectionName: collectionNames){
            System.out.println(collectionName);
        }
    }

    private void saveInsert(DBCollection collection, Map<String, String> fields){
        /*
        If element with given id is present perform update else perform insert.
         */
        BasicDBObject document = new BasicDBObject();
        fields.forEach((k,v) -> document.put(k,v));
        collection.insert(document);
    }

    private void saveUpdate(DBCollection collection, Map<String, String> searchArgs,
                            Map<String, String> newArgs){
        BasicDBObject searchQuery = new BasicDBObject();
        searchArgs.forEach((k,v) -> searchQuery.put(k,v));

        BasicDBObject newDocument = new BasicDBObject();
        newArgs.forEach((k,v) -> newDocument.put(k,v));

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        collection.update(searchQuery, updateObject);
    }

    private void findDocument(DBCollection collection, Map<String, String> queryArgs){
        BasicDBObject searchQuery = new BasicDBObject();
        queryArgs.forEach((k,v) -> searchQuery.put(k,v));
        DBCursor cursor = collection.find(searchQuery);

        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private void deleteDocument(DBCollection collection, Map<String, String> queryArgs){
        BasicDBObject searchQuery = new BasicDBObject();
        queryArgs.forEach((k,v) -> searchQuery.put(k,v));
        collection.remove(searchQuery);
    }


    private void clearDb(DB db){
        for(String collectionName: db.getCollectionNames()){
            getCollection(collectionName, db).drop();
        }
    }
}
