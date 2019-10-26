package remoteResourceFramework.util;

import remoteResourceFramework.exceptions.InMemoryStorageException;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public abstract class InMemoryStorageService<T> {

    ConcurrentMap<Integer, T> objectsById;

    protected InMemoryStorageService() {
        this.objectsById = new ConcurrentHashMap<>();
    }

    public void put(int id, T object) throws InMemoryStorageException {
        Objects.requireNonNull(object);
        T result = objectsById.putIfAbsent(id, object);
        if (result != null) {
            throw new InMemoryStorageException("Object with given id already exists");
        }
    }

    public void remove(int id) {
        objectsById.remove(id);
    }

    public T getOrNull(int id) {
        return objectsById.getOrDefault(id, null);
    }
}