package control;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a cell in the board that may be occupied by an entity.
 * Thread-safe for concurrent access by game elements (zombies, plants, projectiles).
 */
public class Cell {

    private final ReentrantLock lock = new ReentrantLock();
    private EntityType entity;

    public Cell() {
        this.entity = EntityType.EMPTY;
    }

    /**
     * Locks the cell for exclusive access.
     */
    public void lock() {
        lock.lock();
    }

    /**
     * Unlocks the cell after exclusive access.
     */
    public void unlock() {
        lock.unlock();
    }

    /**
     * Sets the entity in this cell.
     * Should be called inside a lock.
     * @param entity the entity to place
     */
    public void setEntity(EntityType entity) {
        this.entity = entity;
    }

    /**
     * Gets the current entity in this cell.
     * Should be called inside a lock.
     * @return the entity
     */
    public EntityType getEntity() {
        return entity;
    }

    /**
     * Checks if the cell is empty.
     */
    public boolean isEmpty() {
        return entity == EntityType.EMPTY;
    }

    public boolean isZombie() {
        return entity == EntityType.ZOMBIE;
    }

    public boolean isPlant() {
        return entity == EntityType.PLANT;
    }

    public boolean isProjectile() {
        return entity == EntityType.PROJECTILE;
    }

    @Override
    public String toString() {
        switch (entity) {
    case PLANT: return "[P]";
    case ZOMBIE: return "[Z]";
    case PROJECTILE: return "[*]";
    case EMPTY: return "[ ]";
    default: return "[?]";
    }
  }
}
