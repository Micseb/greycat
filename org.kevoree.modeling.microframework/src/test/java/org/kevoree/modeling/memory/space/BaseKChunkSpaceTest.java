package org.kevoree.modeling.memory.space;

import org.junit.Assert;
import org.junit.Test;
import org.kevoree.modeling.KConfig;
import org.kevoree.modeling.memory.KChunkFlags;
import org.kevoree.modeling.memory.chunk.KObjectChunk;
import org.kevoree.modeling.memory.chunk.KLongLongMap;
import org.kevoree.modeling.memory.chunk.KLongLongTree;
import org.kevoree.modeling.memory.chunk.KLongTree;

public abstract class BaseKChunkSpaceTest {

    public abstract KChunkSpace createKChunkSpace();

    @Test
    public void test() {
        KChunkSpace storage = createKChunkSpace();

        // KUniverseOrderMap
        KLongLongMap map = (KLongLongMap) storage.create(KConfig.NULL_LONG, KConfig.NULL_LONG, KConfig.NULL_LONG, KChunkTypes.LONG_LONG_MAP);
        map.put(0, 0);
        map.put(1, 1);
        KLongLongMap retrievedMap = (KLongLongMap) storage.get(KConfig.NULL_LONG, KConfig.NULL_LONG, KConfig.NULL_LONG);
        Assert.assertEquals(0, retrievedMap.get(0));
        Assert.assertEquals(1, retrievedMap.get(1));
        Assert.assertEquals(map.size(), retrievedMap.size());
        Assert.assertEquals((map.getFlags() & KChunkFlags.DIRTY_BIT) == KChunkFlags.DIRTY_BIT, (retrievedMap.getFlags() & KChunkFlags.DIRTY_BIT) == KChunkFlags.DIRTY_BIT);
        Assert.assertEquals(map.counter(), retrievedMap.counter());

        // KLongLongTree
        KLongLongTree longLongTree = (KLongLongTree) storage.create(0, KConfig.NULL_LONG, KConfig.END_OF_TIME, KChunkTypes.LONG_LONG_TREE);
        longLongTree.init(null, null, -1);
        longLongTree.insert(0, 0);
        longLongTree.insert(1, 1);
        KLongLongTree retrievedLongLongTree = (KLongLongTree) storage.get(0, KConfig.NULL_LONG, KConfig.END_OF_TIME);
        Assert.assertEquals(0, retrievedLongLongTree.lookupValue(0));
        Assert.assertEquals(1, retrievedLongLongTree.lookupValue(1));
        Assert.assertEquals(longLongTree.size(), retrievedLongLongTree.size());

        Assert.assertEquals((longLongTree.getFlags() & KChunkFlags.DIRTY_BIT) == KChunkFlags.DIRTY_BIT, (retrievedLongLongTree.getFlags() & KChunkFlags.DIRTY_BIT) == KChunkFlags.DIRTY_BIT);

        Assert.assertEquals(longLongTree.counter(), retrievedLongLongTree.counter());

        // KLongTree
        KLongTree longTree = (KLongTree) storage.create(0, KConfig.NULL_LONG, 0, KChunkTypes.LONG_TREE);
        longTree.init(null, null, -1);
        longTree.insert(0);
        longTree.insert(1);
        KLongTree retrievedLongTree = (KLongTree) storage.get(0, KConfig.NULL_LONG, 0);
        Assert.assertEquals(0, retrievedLongTree.lookup(0));
        Assert.assertEquals(1, retrievedLongTree.lookup(1));
        Assert.assertEquals(longTree.size(), retrievedLongTree.size());

        Assert.assertEquals((longTree.getFlags() & KChunkFlags.DIRTY_BIT) == KChunkFlags.DIRTY_BIT, (retrievedLongTree.getFlags() & KChunkFlags.DIRTY_BIT) == KChunkFlags.DIRTY_BIT);

        Assert.assertEquals(longTree.counter(), retrievedLongTree.counter());

        // KObjectChunk
        KObjectChunk chunk = (KObjectChunk) storage.create(0, 0, 0, KChunkTypes.CHUNK);
        KObjectChunk retrievedChunk = (KObjectChunk) storage.get(0, 0, 0);
        Assert.assertNotNull(retrievedChunk);
    }
}

