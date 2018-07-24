package cachingSystem;

import cachingSystem.classes.ObservableCache;
import cachingSystem.classes.ObservableFIFOCache;
import cachingSystem.classes.LRUCache;
import cachingSystem.classes.TimeAwareCache;
import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Pair;
import observerPattern.classes.BroadcastListener;
import observerPattern.interfaces.CacheListener;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileCache {

    public enum Strategy {
        FIFO,
        LRU,
    }

    public static FileCache createCacheWithCapacity(
            Strategy strategy, int capacity) {
        ObservableCache<String, String> dataCache;

        switch (strategy) {

            case FIFO:
                dataCache = new ObservableFIFOCache<>();
                break;
            case LRU:
                dataCache = new LRUCache<>();
                break;
            default:
                throw new IllegalArgumentException("Unsupported cache strategy: " + strategy);
        }

        dataCache.setStalePolicy(new CacheStalePolicy<String, String>() {
            @Override
            public boolean shouldRemoveEldestEntry(Pair<String, String> entry) {
                return dataCache.size() > capacity;
            }
        });

        return new FileCache(dataCache);
    }

    public static FileCache createCacheWithExpiration(long millisToExpire) {
        TimeAwareCache<String, String> dataCache = new TimeAwareCache<>();
        dataCache.setExpirePolicy(millisToExpire);

        return new FileCache(dataCache);
    }

    private FileCache(ObservableCache<String, String> dataCache) {
        this.dataCache = dataCache;
        this.broadcastListener = new BroadcastListener<>();

        this.dataCache.setCacheListener(broadcastListener);

        broadcastListener.addListener(createCacheListener());
    }

    private static String readFileContent(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));


        return new String(encoded, encoding);
    }

    private CacheListener<String, String> createCacheListener() {
        return new CacheListener<String, String>() {
            @Override
            public void onHit(String key) {

            }

            @Override
            public void onMiss(String key)  {
                String path = key;
                try {
                    String contents = readFileContent(key, StandardCharsets.UTF_8);
                    putFileContents(path, contents);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPut(String key, String value) {

            }

        };
    }

    public String getFileContents(String path) {
        String fileContents;

        do {
            fileContents = dataCache.get(path);
        } while (fileContents == null);
        return fileContents;
    }

    public void putFileContents(String path, String contents) {
        dataCache.put(path, contents);
    }

    public void addListener(CacheListener<String, String> listener) {
        broadcastListener.addListener(listener);
    }

    private ObservableCache<String, String> dataCache;
    private BroadcastListener<String, String> broadcastListener;
}
