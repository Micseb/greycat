package greycat;

import greycat.chunk.ChunkSpace;
import greycat.internal.CoreGraph;
import greycat.plugin.Plugin;
import greycat.plugin.Resolver;
import greycat.plugin.Scheduler;
import greycat.plugin.Storage;

public class PaxosGraph extends CoreGraph {

    @Override
    protected Resolver createResolver(Storage p_storage, ChunkSpace p_space, Graph selfGraph) {
        return new PaxosResolver(p_storage, p_space, selfGraph);
    }

    public PaxosGraph(Storage p_storage, long memorySize, long batchSize, Scheduler p_scheduler, Plugin[] p_plugins, boolean deepPriority) {
        super(p_storage, memorySize, batchSize, p_scheduler, p_plugins, deepPriority);
    }
}
