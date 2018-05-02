import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Database {
    private static final String MongoURI = "mongodb://localhost:27017";
    private static final String DatabaseName = "MyDB";
    private static DB db;

    Database(){
        MongoClient mongoClient = new MongoClient(new MongoClientURI(MongoURI));
        db = mongoClient.getDB(DatabaseName);
    }

    public DBCollection createCollection(String collectionName){
        return db.createCollection(collectionName, null);
    }

    public DBCollection getCollection(String collectionName){
        DBCollection collection = db.getCollection(collectionName);
        return collection;
    }

    public void displayCollections(){
        Set<String> collectionNames = db.getCollectionNames();
        for(String collectionName: collectionNames){
            System.out.println(collectionName);
        }
    }

    public ObjectId saveInsert(DBCollection collection, Map<String, Object> fields){
        /*
        If element with given id is present perform update else perform insert.
         */
        BasicDBObject document = new BasicDBObject();
        fields.forEach((k,v) -> document.put(k,v));
        collection.insert(document);
        return (ObjectId)document.get("_id");
    }

    public void saveUpdate(DBCollection collection, Map<String, Object> searchArgs,
                            Map<String, Object> newArgs){
        BasicDBObject searchQuery = new BasicDBObject();
        searchArgs.forEach((k,v) -> searchQuery.put(k,v));

        BasicDBObject newDocument = new BasicDBObject();
        newArgs.forEach((k,v) -> newDocument.put(k,v));

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        collection.update(searchQuery, updateObject);
    }

    public void listMatchingDocuments(DBCollection collection, Map<String, Object> queryArgs){
        BasicDBObject searchQuery = new BasicDBObject();
        queryArgs.forEach((k,v) -> searchQuery.put(k,v));
        DBCursor cursor = collection.find(searchQuery);

        while(cursor.hasNext()) {
            DBObject obj = cursor.next();
            printOffer(obj);
        }
    }

    public void printOffer(DBObject obj){
        System.out.println("Address: " + obj.get("address") + "\n" +
                "AvailableFrom: " + obj.get("availableFrom") + "\n" +
                "AvailableTo: " + obj.get("availableTo") + "\n" +
                "PricePerDay: " + obj.get("pricePerDay") + "\n");
    }

    public DBObject findOneDocument(DBCollection collection, Map<String, Object> queryArgs){
        BasicDBObject searchQuery = new BasicDBObject();
        queryArgs.forEach((k,v) -> searchQuery.put(k,v));
        DBObject obj = collection.findOne(searchQuery);

        printOffer(obj);

        return obj;
    }

    public void deleteDocument(DBCollection collection, Map<String, Object> queryArgs){
        BasicDBObject searchQuery = new BasicDBObject();
        queryArgs.forEach((k,v) -> searchQuery.put(k,v));
        collection.remove(searchQuery);
    }


    public void clearDb(){
        for(String collectionName: db.getCollectionNames()){
            db.getCollection(collectionName).drop();
        }
    }
}
